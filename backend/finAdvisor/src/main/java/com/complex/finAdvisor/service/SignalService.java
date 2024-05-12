package com.complex.finAdvisor.service;

import com.complex.finAdvisor.dto.StockDto;
import com.complex.finAdvisor.entity.InstrumentEntity;
import com.complex.finAdvisor.entity.StockSignalEntity;
import com.complex.finAdvisor.repository.SignalRepository;
import com.complex.finAdvisor.repository.StockRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SignalService {
    private final StockRepository stockRepository;
    private final SignalRepository signalRepository;

    @Scheduled(cron = "0 0 0 * * ?", zone = "Europe/Moscow")
    public void updateAllSignals() {
        List<InstrumentEntity> allStocks = this.stockRepository.findAll();
        for (InstrumentEntity instrumentEntity : allStocks) {
            int last = getLastHistoryInstrument(instrumentEntity);
            String apiUrl = "https://iss.moex.com/iss/history/engines/stock/markets/shares/boards/TQBR/securities/" + instrumentEntity.getSecid() + ".xml?limit=" + 100 + "&start=" + last;
            RestTemplate restTemplate = new RestTemplate();
            String xmlResponse = restTemplate.getForObject(apiUrl, String.class);
            // Парсинг и сохранение всех элементов <row> в базу данных
            ArrayList<StockDto> stockDtoData = parseXmlResponse(xmlResponse);
            ArrayList<StockDto> signalData = findLastSignal(stockDtoData);
            System.out.println(last);
            System.out.println(stockDtoData.size());
            System.out.println(signalData.size());
            if (stockDtoData.isEmpty() || signalData.isEmpty()) {
                continue;
            }
            StockSignalEntity lastSignal = this.signalRepository.findBySecid(instrumentEntity.getSecid());
            if (lastSignal != null && lastSignal.getDate().isBefore(signalData.get(7).getTradedate())) {
                this.signalRepository.deleteById(lastSignal.getId());
                saveStockSignal(signalData);
            }
            if (lastSignal != null && lastSignal.getDate().isEqual(signalData.get(7).getTradedate())) {
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
            ArrayList<StockDto> stockDtoData = parseXmlResponse(xmlResponse);
            if (stockDtoData.size() < 100) {
                return start;
            }
            if (stockDtoData.isEmpty()) {
                return Math.max(0, start - 100);
            }
            start += 100;
        }
    }

    private void saveStockSignal(ArrayList<StockDto> signalData) {
        StockSignalEntity stockSignalEntity = new StockSignalEntity();
        stockSignalEntity.setDate(signalData.get(7).getTradedate());
        stockSignalEntity.setShortname(signalData.get(7).getShortname());
        stockSignalEntity.setSecid(signalData.get(7).getSecid());
        stockSignalEntity.setOpen(signalData.get(7).getOpen());
        this.signalRepository.save(stockSignalEntity);
    }

    private ArrayList<StockDto> parseXmlResponse(String xmlResponse) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode rootNode = xmlMapper.readTree(xmlResponse).get("data").get("rows");
            JsonNode node = rootNode != null ? rootNode.get("row") : null; // Check if rootNode is not null before getting "row"
            ArrayList<StockDto> stockDtos = new ArrayList<>();
            if (node == null || !node.isArray()) { // Check if node is null or not an array
                return stockDtos; // Return empty list if "row" node does not exist or is not an array
            }
            for (JsonNode item : node) {
                String shortname = item.get("SHORTNAME").asText();
                String secid = item.get("SECID").asText();
                String tradeDateStr = item.get("TRADEDATE").asText();
                LocalDate tradeDate = LocalDate.parse(tradeDateStr);
                double open = item.get("OPEN").asDouble();
                double high = item.get("HIGH").asDouble();
                double low = item.get("LOW").asDouble();
                double close = item.get("CLOSE").asDouble();
                StockDto stockDto = new StockDto();
                stockDto.setSecid(secid);
                stockDto.setTradedate(tradeDate);
                stockDto.setOpen(open);
                stockDto.setLow(low);
                stockDto.setHigh(high);
                stockDto.setClose(close);
                stockDto.setShortname(shortname);
                stockDtos.add(stockDto);
            }
            return stockDtos;
        } catch (IOException e) {
            // Обработка ошибки парсинга XML
            return null;
        }
    }
    public ArrayList<StockDto> findLastSignal(ArrayList<StockDto> allStockDatumDtos) {
        for (int i = allStockDatumDtos.size() - 5; i >= 0; i--) {
            List<StockDto> subListLow = allStockDatumDtos.subList(i, i + 5);
            if (isFractal(subListLow)) {
                for (int j = i + 5; j <= allStockDatumDtos.size() - 6; j++) { // Упрощенное условие для цикла
                    if (isFalseBreakdown(subListLow.get(2), allStockDatumDtos.get(j))) {
                        for (int k = j + 1; k <= j + 5; k++) { // Упрощенное условие для цикла
                            if (isSignal(allStockDatumDtos.get(j), allStockDatumDtos.get(k))) {
                                ArrayList<StockDto> lastMatchingSignal = new ArrayList<>(subListLow);
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
    private boolean isFractal(List<StockDto> subListStockDto) {
        return subListStockDto.get(0).getLow() > subListStockDto.get(1).getLow() &&
                subListStockDto.get(1).getLow() > subListStockDto.get(2).getLow() &&
                subListStockDto.get(2).getLow() < subListStockDto.get(3).getLow() &&
                subListStockDto.get(3).getLow() < subListStockDto.get(4).getLow();
    }
    private boolean isFalseBreakdown(StockDto minFractal, StockDto nextBeforeFractal) {
        return minFractal.getLow() > nextBeforeFractal.getLow();
    }
    private boolean isSignal(StockDto breakdown, StockDto nextBeforeBreakdown) {
        return breakdown.getOpen() < nextBeforeBreakdown.getClose() && !isTrueBreakdown(breakdown, nextBeforeBreakdown);
    }
    private boolean isTrueBreakdown(StockDto breakdown, StockDto nextBeforeBreakdown) {
        return breakdown.getLow() > nextBeforeBreakdown.getClose();
    }
    private StockDto createLongPoint(StockDto signal) {
        return new StockDto(signal.getSecid(), 0, 0, signal.getClose(), 0, signal.getShortname(), signal.getTradedate());
    }
}
