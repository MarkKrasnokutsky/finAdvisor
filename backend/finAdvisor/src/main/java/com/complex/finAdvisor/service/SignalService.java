package com.complex.finAdvisor.service;

import com.complex.finAdvisor.dto.PositionInfoDto;
import com.complex.finAdvisor.dto.SignalDto;
import com.complex.finAdvisor.dto.SignalResponse;
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

    public List<SignalResponse> getSignalsByUser(String username) {
        try {
            List<SignalResponse> stockSignals = new ArrayList<>();
            Optional<UserEntity> currentUser = userRepository.findByUsername(username);

            currentUser.ifPresent(userEntity -> {
                Long currentTariff = userEntity.getTariff().getId();
                List<InstrumentTariffEntity> listRelationship = stockTariffRepository.findByTariffId(currentTariff);

                for (InstrumentTariffEntity relationship : listRelationship) {
                    StockSignalEntity stockSignalEntity = signalRepository.findBySecid(relationship.getInstrument().getSecid());
                    if (stockSignalEntity == null) {
                        continue;
                    }
                    SignalResponse signalResponse = new SignalResponse();
                    signalResponse.setId(stockSignalEntity.getId());
                    signalResponse.setDate(stockSignalEntity.getDate());
                    signalResponse.setSecid(stockSignalEntity.getSecid());
                    signalResponse.setShortname(stockSignalEntity.getShortname());
                    signalResponse.setOpen(stockSignalEntity.getOpen());
                    signalResponse.setStop(stockSignalEntity.getStop());
                    signalResponse.setProfitFix(stockSignalEntity.getProfitFix());
                    stockSignals.add(signalResponse);
                }
            });
            return stockSignals;
        } catch (Exception e) {
            return null;
        }
    }

    @Scheduled(cron = "0 0 2 * * ?", zone = "Europe/Moscow")
    public void updateAllSignals() {
        List<InstrumentEntity> allStocks = stockRepository.findAll();
        signalRepository.deleteAll();
        for (InstrumentEntity instrumentEntity : allStocks) {
            int start;
            PositionInfoDto info = getLastHistoryInstrument(instrumentEntity);
            if(info.getTotal() < info.getPageSize()) {
                start = 7;
            }
            else {
                start = info.getTotal() - info.getPageSize() + 7;
            }
            String apiUrl = "https://iss.moex.com/iss/history/engines/stock/markets/shares/boards/TQBR/securities/" + instrumentEntity.getSecid() + ".xml?limit=" + info.getPageSize() + "&start=" + start;
            RestTemplate restTemplate = new RestTemplate();
            String xmlResponse = restTemplate.getForObject(apiUrl, String.class);
            // Парсинг и сохранение всех элементов <row> в базу данных
            ArrayList<SignalDto> signalDtoData = parseXmlResponse(xmlResponse);
            ArrayList<SignalDto> signalData = findLastSignal(signalDtoData);
            System.out.println("------------------------------------------------------");
            System.out.println(instrumentEntity.getSecid());
            System.out.println(start);
            System.out.println(signalDtoData.size());
            System.out.println(signalData.size());
            if (signalDtoData.isEmpty() || signalData.isEmpty()) {
                continue;
            }
//            StockSignalEntity lastSignal = this.signalRepository.findBySecid(instrumentEntity.getSecid());
//            if (lastSignal != null) {
//                if (lastSignal.getDate().isBefore(signalData.get(7).getTradedate()) || lastSignal.getDate().isEqual(signalData.get(7).getTradedate())) {
//                    lastSignal.setDate(signalData.get(7).getTradedate());
//                    lastSignal.setOpen(signalData.get(7).getOpen());
//                    lastSignal.setStop(signalData.get(5).getLow() - 0.0005 * signalData.get(5).getLow());
//                    this.signalRepository.save(lastSignal);
//                    continue;
//                }
//            } else {
//                saveStockSignal(signalData);
//                continue;
//            }
            saveStockSignal(signalData);
        }
    }

    private PositionInfoDto getLastHistoryInstrument(InstrumentEntity instrumentEntity) {
        try {
            String apiUrl = "https://iss.moex.com/iss/history/engines/stock/markets/shares/boards/TQBR/securities/" + instrumentEntity.getSecid() + ".xml?limit=" + 100 + "&start=" + 0;
            RestTemplate restTemplate = new RestTemplate();
            String xmlResponse = restTemplate.getForObject(apiUrl, String.class);
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode rootNode = xmlMapper.readTree(xmlResponse);

            // Итерация по узлам "data" и поиск узла с id "history.cursor"
            Iterator<JsonNode> dataNodes = rootNode.path("data").elements();
            JsonNode historyCursorDataNode = null;
            while (dataNodes.hasNext()) {
                JsonNode dataNode = dataNodes.next();
                if (dataNode.has("id") && dataNode.path("id").asText().equals("history.cursor")) {
                    historyCursorDataNode = dataNode;
                    break;
                }
            }

            if (historyCursorDataNode == null) {
                // Если узел "data" с id "history.cursor" не найден, вернуть пустой список
                return null;
            }

            // Доступ к узлу "rows" из найденного узла "data"
            JsonNode rowsNode = historyCursorDataNode.path("rows");
            JsonNode rowNode = rowsNode.get("row"); // Предполагается, что "row" - это массив

            PositionInfoDto instrumentInfo = new PositionInfoDto();
            if (rowNode != null) {
                SignalDto signalDto = new SignalDto();
                // Извлечение значений из атрибутов
                instrumentInfo.setIndex(rowNode.get("INDEX").asInt());
                instrumentInfo.setTotal(rowNode.get("TOTAL").asInt());
                instrumentInfo.setPageSize(rowNode.get("PAGESIZE").asInt());
            }
            return instrumentInfo;
        } catch (IOException e) {
            // Обработка ошибки парсинга XML
            e.printStackTrace(); // Рекомендуется логировать ошибку
            return null;
        }
    }

    private void saveStockSignal(ArrayList<SignalDto> signalData) {
        StockSignalEntity stockSignalEntity = new StockSignalEntity();
        stockSignalEntity.setDate(signalData.get(7).getTradedate());
        stockSignalEntity.setShortname(signalData.get(7).getShortname());
        stockSignalEntity.setSecid(signalData.get(7).getSecid());
        stockSignalEntity.setOpen(signalData.get(7).getOpen());
        stockSignalEntity.setProfitFix(signalData.get(7).getOpen() + signalData.get(7).getOpen() * 0.06);
        stockSignalEntity.setStop(signalData.get(5).getLow() - 0.0005 * signalData.get(5).getLow());
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
    // Поиск последнего сигнала по инструменту. На вход принимается список дневных свечей, которые по api МосБиржы
    // лежат на последней странице
    public ArrayList<SignalDto> findLastSignal(ArrayList<SignalDto> allStockDatumDtos) {
        outerLoop:
        for (int i = allStockDatumDtos.size() - 5; i >= 0; i--) {
            // На всём промежутке для начала ищется фрактал с конца списка свечей (то есть самый свежий)
            List<SignalDto> subListLow = allStockDatumDtos.subList(i, i + 5);
            // Если фрактал найден
            if (isFractal(subListLow)) {
                // Находим конечный индекс, учитывая максимум 6 элементов после 5-го
                // далее уже от фрактала идем вперед и ищем ложный пробой
                for (int j = i + 5; j <= Math.min(allStockDatumDtos.size() - 1, i + 5 + 6); j++) { // Упрощенное условие для цикла
                    // Если ложный пробой найден
                    System.out.println(allStockDatumDtos.get(j));
                    if (isFalseBreakdown(subListLow.get(2), allStockDatumDtos.get(j))) {
                        // Идем опять далее, но уже от пробойной свечи и ищем сигнальную
                        for (int k = j + 1; k < Math.min(j + 6, allStockDatumDtos.size()); k++) { // Упрощенное условие для цикла
                            if (isTrueBreakdown(allStockDatumDtos.get(j), allStockDatumDtos.get(k))) {
                                continue outerLoop;
                            }
                            boolean flagTypeBreakdown = typeBreakdown(allStockDatumDtos.get(j));
                            // Если сигнальная найдена
                            if (isSignal(allStockDatumDtos.get(j), allStockDatumDtos.get(k), flagTypeBreakdown)) {
                                // Записываем данные в структуру для сигнала
                                ArrayList<SignalDto> lastMatchingSignal = new ArrayList<>(subListLow);
                                lastMatchingSignal.add(allStockDatumDtos.get(j));
                                lastMatchingSignal.add(allStockDatumDtos.get(k));
                                lastMatchingSignal.add(createLongPoint(allStockDatumDtos.get(k)));
                                // Для справки:
                                // subListLow - это фрактальная выборка = 5 свечей
                                // lastMatchingSignal.add(allStockDatumDtos.get(j)); - ложный пробой
                                // lastMatchingSignal.add(allStockDatumDtos.get(k)); - сигнальная свеча
                                // lastMatchingSignal.add(createLongPoint(allStockDatumDtos.get(k))); - сам сигнал
                                return lastMatchingSignal; // Возвращаем сразу, как только нашли подходящий сигнал
                            }
                        }
                    }
                }
            }
        }
        return new ArrayList<>(); // Возвращаем пустой список, если ничего не найдено
    }
    // Проверка наличия фрактала в 5 свечах. На вход принимается список дневных свечей, которые по api МосБиржы
    private boolean isFractal(List<SignalDto> subListSignalDto) {
        return
                subListSignalDto.get(2).getLow() < subListSignalDto.get(1).getLow() &&
                        subListSignalDto.get(2).getLow() < subListSignalDto.get(3).getLow() &&
                        subListSignalDto.get(2).getLow() < subListSignalDto.get(0).getLow() &&
                        subListSignalDto.get(2).getLow() < subListSignalDto.get(4).getLow();
    }
    // true - "зелёная" свеча
    // false - "красная" свеча
    private boolean typeBreakdown(SignalDto breakdown) {
        return breakdown.getClose() > breakdown.getOpen();
    }
    // Проверка на ложный пробой
    private boolean isFalseBreakdown(SignalDto minFractal, SignalDto nextBeforeFractal) {
        return minFractal.getLow() > nextBeforeFractal.getLow();
    }
    // Проверка на сигнальную свечу
    private boolean isSignal(SignalDto  breakdown, SignalDto nextBeforeBreakdown, boolean flagTypeBreakdown) {
        if (flagTypeBreakdown) return breakdown.getClose() < nextBeforeBreakdown.getClose() && !isTrueBreakdown(breakdown, nextBeforeBreakdown);
        else return breakdown.getOpen() < nextBeforeBreakdown.getClose() && !isTrueBreakdown(breakdown, nextBeforeBreakdown);
    }
    // Проверка на истинный пробой
    private boolean isTrueBreakdown(SignalDto breakdown, SignalDto nextBeforeBreakdown) {
        return breakdown.getLow() > nextBeforeBreakdown.getClose();
    }
    // Создание структуры для сигнала
    private SignalDto createLongPoint(SignalDto signal) {
        return new SignalDto(signal.getSecid(), 0, 0, signal.getClose(), 0, signal.getShortname(), signal.getTradedate().plusDays(1));
    }
}
