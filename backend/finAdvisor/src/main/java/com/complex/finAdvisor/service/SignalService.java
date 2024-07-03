package com.complex.finAdvisor.service;

import com.complex.finAdvisor.dto.SignalDto;
import com.complex.finAdvisor.entity.InstrumentEntity;
import com.complex.finAdvisor.entity.InstrumentTariffEntity;
import com.complex.finAdvisor.entity.StockSignalEntity;
import com.complex.finAdvisor.entity.UserEntity;
import com.complex.finAdvisor.repository.SignalRepository;
import com.complex.finAdvisor.repository.StockRepository;
import com.complex.finAdvisor.repository.StockTariffRepository;
import com.complex.finAdvisor.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignalService {
    private final StockRepository stockRepository;
    private final SignalRepository signalRepository;
    private final UserRepository userRepository;
    private final StockTariffRepository stockTariffRepository;

    public List<StockSignalEntity> getSignalsByUser(String username) {
        try {
            List<StockSignalEntity> stockSignalEntities = new ArrayList<>();
            Optional<UserEntity> currentUser = userRepository.findByUsername(username);

            currentUser.ifPresent(userEntity -> {
                Long currentTariff = userEntity.getTariff().getId();
                List<InstrumentTariffEntity> listRelationship = stockTariffRepository.findByTariffId(currentTariff);

                for (InstrumentTariffEntity relationship : listRelationship) {
                    StockSignalEntity stockSignalEntity = signalRepository.findBySecid(relationship.getInstrument().getSecid());
                    if (stockSignalEntity == null) {
                        continue;
                    }
                    stockSignalEntities.add(stockSignalEntity);
                }
            });
            return stockSignalEntities;
        } catch (Exception e) {
            return null;
        }
    }

    @Scheduled(cron = "0 0 0 * * ?", zone = "Europe/Moscow")
    public void updateAllSignals() {
        List<InstrumentEntity> allStocks = stockRepository.findAll();
        for (InstrumentEntity instrumentEntity : allStocks) {
            int last = getLastHistoryInstrument(instrumentEntity);
            String apiUrl = "https://iss.moex.com/iss/history/engines/stock/markets/shares/boards/TQBR/securities/" + instrumentEntity.getSecid() + ".xml?limit=" + 100 + "&start=" + last;
            RestTemplate restTemplate = new RestTemplate();
            String xmlResponse = restTemplate.getForObject(apiUrl, String.class);
            // Парсинг и сохранение всех элементов <row> в базу данных
            ArrayList<SignalDto> signalDtoData = parseXmlResponse(xmlResponse);
            ArrayList<SignalDto> signalData = findLastSignal(signalDtoData);
            System.out.println(instrumentEntity.getSecid());
            System.out.println(last);
            System.out.println(signalDtoData.size());
            System.out.println(signalData.size());
            if (signalDtoData.isEmpty() || signalData.isEmpty()) {
                continue;
            }
            StockSignalEntity lastSignal = this.signalRepository.findBySecid(instrumentEntity.getSecid());
            if (lastSignal != null) {
                if (lastSignal.getDate().isBefore(signalData.get(7).getTradedate())) {
                    lastSignal.setDate(signalData.get(7).getTradedate()); // предположим, что у вас есть метод setDate
                    lastSignal.setOpen(signalData.get(7).getOpen()); // обновите все необходимые поля
                    this.signalRepository.save(lastSignal); // сохраните обновленную сущность
                    continue;
                } else if (lastSignal.getDate().isEqual(signalData.get(7).getTradedate())) {
                    lastSignal.setDate(signalData.get(7).getTradedate()); // предположим, что у вас есть метод setDate
                    lastSignal.setOpen(signalData.get(7).getOpen()); // обновите все необходимые поля
                    this.signalRepository.save(lastSignal); // сохраните обновленную сущность
                    continue;
                }
            } else {
                saveStockSignal(signalData);
                continue;
            }
            saveStockSignal(signalData);
        }
    }

    private int getLastHistoryInstrument(InstrumentEntity instrumentEntity) {
        int start = 0;
        while(true) {
            String apiUrl = "https://iss.moex.com/iss/history/engines/stock/markets/shares/boards/TQBR/securities/" + instrumentEntity.getSecid() + ".xml?limit=" + 100 + "&start=" + start;
            RestTemplate restTemplate = new RestTemplate();
            String xmlResponse = restTemplate.getForObject(apiUrl, String.class);
            ArrayList<SignalDto> signalDtoData = parseXmlResponse(xmlResponse);
            if (signalDtoData.size() < 100) {
                return start;
            }
            if (signalDtoData.isEmpty()) {
                return Math.max(0, start - 100);
            }
            start += 100;
        }
    }

    private void saveStockSignal(ArrayList<SignalDto> signalData) {
        StockSignalEntity stockSignalEntity = new StockSignalEntity();
        stockSignalEntity.setDate(signalData.get(7).getTradedate());
        stockSignalEntity.setShortname(signalData.get(7).getShortname());
        stockSignalEntity.setSecid(signalData.get(7).getSecid());
        stockSignalEntity.setOpen(signalData.get(7).getOpen());
        this.signalRepository.save(stockSignalEntity);
    }

    private ArrayList<SignalDto> parseXmlResponse(String xmlResponse) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode rootNode = xmlMapper.readTree(xmlResponse);

            // Iterate through "data" nodes and find the one with id "history"
            Iterator<JsonNode> dataNodes = rootNode.path("data").elements();
            JsonNode historyDataNode = null;
            while (dataNodes.hasNext()) {
                JsonNode dataNode = dataNodes.next();
                if (dataNode.has("id") && dataNode.path("id").asText().equals("history")) {
                    historyDataNode = dataNode;
                    break;
                }
            }

            if (historyDataNode == null) {
                // If no "data" node with id "history" is found, return an empty list
                return new ArrayList<>();
            }

            // Access the "rows" node from the found "data" node
            JsonNode rowsNode = historyDataNode.path("rows");
            JsonNode rowNodes = rowsNode.get("row"); // Assuming "row" is an array

            ArrayList<SignalDto> signalDtos = new ArrayList<>();
            if (rowNodes != null && rowNodes.isArray()) {
                for (JsonNode item : rowNodes) {
                    String shortname = item.get("SHORTNAME").asText();
                    String secid = item.get("SECID").asText();
                    String tradeDateStr = item.get("TRADEDATE").asText();
                    LocalDate tradeDate = LocalDate.parse(tradeDateStr);
                    double open = item.get("OPEN").asDouble();
                    double high = item.get("HIGH").asDouble();
                    double low = item.get("LOW").asDouble();
                    double close = item.get("CLOSE").asDouble();

                    SignalDto signalDto = new SignalDto();
                    signalDto.setSecid(secid);
                    signalDto.setTradedate(tradeDate);
                    signalDto.setOpen(open);
                    signalDto.setLow(low);
                    signalDto.setHigh(high);
                    signalDto.setClose(close);
                    signalDto.setShortname(shortname);
                    signalDtos.add(signalDto);
                }
            }
            return signalDtos;
        } catch (IOException e) {
            // Обработка ошибки парсинга XML
            return null;
        }
    }
    public ArrayList<SignalDto> findLastSignal(ArrayList<SignalDto> allStockDatumDtos) {
        for (int i = allStockDatumDtos.size() - 5; i >= 0; i--) {
            List<SignalDto> subListLow = allStockDatumDtos.subList(i, i + 5);
            if (isFractal(subListLow)) {
                for (int j = i + 5; j <= allStockDatumDtos.size() - 6; j++) { // Упрощенное условие для цикла
                    if (isFalseBreakdown(subListLow.get(2), allStockDatumDtos.get(j))) {
                        for (int k = j + 1; k <= j + 5; k++) { // Упрощенное условие для цикла
                            if (isSignal(allStockDatumDtos.get(j), allStockDatumDtos.get(k))) {
                                ArrayList<SignalDto> lastMatchingSignal = new ArrayList<>(subListLow);
                                lastMatchingSignal.add(allStockDatumDtos.get(j));
                                lastMatchingSignal.add(allStockDatumDtos.get(k));
                                lastMatchingSignal.add(createLongPoint(allStockDatumDtos.get(k)));

                                return lastMatchingSignal; // Возвращаем сразу, как только нашли подходящий сигнал
                            }
                        }
                    }
                }
            }
        }
        return new ArrayList<>(); // Возвращаем пустой список, если ничего не найдено
    }
    private boolean isFractal(List<SignalDto> subListSignalDto) {
        return subListSignalDto.get(0).getLow() > subListSignalDto.get(1).getLow() &&
                subListSignalDto.get(1).getLow() > subListSignalDto.get(2).getLow() &&
                subListSignalDto.get(2).getLow() < subListSignalDto.get(3).getLow() &&
                subListSignalDto.get(3).getLow() < subListSignalDto.get(4).getLow();
    }
    private boolean isFalseBreakdown(SignalDto minFractal, SignalDto nextBeforeFractal) {
        return minFractal.getLow() > nextBeforeFractal.getLow();
    }
    private boolean isSignal(SignalDto breakdown, SignalDto nextBeforeBreakdown) {
        return breakdown.getOpen() < nextBeforeBreakdown.getClose() && !isTrueBreakdown(breakdown, nextBeforeBreakdown);
    }
    private boolean isTrueBreakdown(SignalDto breakdown, SignalDto nextBeforeBreakdown) {
        return breakdown.getLow() > nextBeforeBreakdown.getClose();
    }
    private SignalDto createLongPoint(SignalDto signal) {
        return new SignalDto(signal.getSecid(), 0, 0, signal.getClose(), 0, signal.getShortname(), signal.getTradedate());
    }
}
