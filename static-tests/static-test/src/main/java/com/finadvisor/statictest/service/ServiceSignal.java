package com.finadvisor.statictest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.finadvisor.statictest.domain.dto.Stock;
import com.finadvisor.statictest.domain.entity.InstrumentEntity;
import com.finadvisor.statictest.domain.entity.StockSignalEntity;
import com.finadvisor.statictest.repository.SignalRepository;
import com.finadvisor.statictest.repository.StockRepository;
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
public class ServiceSignal {
    private final StockRepository stockRepository;
    private final SignalRepository signalRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void updateAllSignals() {
        List<InstrumentEntity> allStocks = this.stockRepository.findAll();
        for (InstrumentEntity instrumentEntity : allStocks) {
            int last = getLastHistoryInstrument(instrumentEntity);
            String apiUrl = "https://iss.moex.com/iss/history/engines/stock/markets/shares/boards/TQBR/securities/" + instrumentEntity.getSecid() + ".xml?limit=" + 100 + "&start=" + last;
            RestTemplate restTemplate = new RestTemplate();
            String xmlResponse = restTemplate.getForObject(apiUrl, String.class);
            // Парсинг и сохранение всех элементов <row> в базу данных
            ArrayList<Stock> stockData = parseXmlResponse(xmlResponse);
            ArrayList<Stock> signalData = findLastSignal(stockData);
            System.out.println(last);
            System.out.println(stockData.size());
            System.out.println(signalData.size());
            if (stockData.isEmpty() || signalData.isEmpty()) {
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
            ArrayList<Stock> stockData = parseXmlResponse(xmlResponse);
            if (stockData.size() < 100) {
                return start;
            }
            if (stockData.isEmpty()) {
                return Math.max(0, start - 100);
            }
            start += 100;
        }
    }

    private void saveStockSignal(ArrayList<Stock> signalData) {
        StockSignalEntity stockSignalEntity = new StockSignalEntity();
        stockSignalEntity.setDate(signalData.get(7).getTradedate());
        stockSignalEntity.setShortname(signalData.get(7).getShortname());
        stockSignalEntity.setSecid(signalData.get(7).getSecid());
        stockSignalEntity.setOpen(signalData.get(7).getOpen());
        this.signalRepository.save(stockSignalEntity);
    }

    private ArrayList<Stock> parseXmlResponse(String xmlResponse) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode rootNode = xmlMapper.readTree(xmlResponse).get("data").get("rows");
            JsonNode node = rootNode != null ? rootNode.get("row") : null; // Check if rootNode is not null before getting "row"
            ArrayList<Stock> stocks = new ArrayList<>();
            if (node == null || !node.isArray()) { // Check if node is null or not an array
                return stocks; // Return empty list if "row" node does not exist or is not an array
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
                Stock stock = new Stock();
                stock.setSecid(secid);
                stock.setTradedate(tradeDate);
                stock.setOpen(open);
                stock.setLow(low);
                stock.setHigh(high);
                stock.setClose(close);
                stock.setShortname(shortname);
                stocks.add(stock);
            }
            return stocks;
        } catch (IOException e) {
            // Обработка ошибки парсинга XML
            return null;
        }
    }
    public ArrayList<Stock> findLastSignal(ArrayList<Stock> allStockData) {
        for (int i = allStockData.size() - 5; i >= 0; i--) {
            List<Stock> subListLow = allStockData.subList(i, i + 5);
            if (isFractal(subListLow)) {
                for (int j = i + 5; j <= allStockData.size() - 6; j++) { // Упрощенное условие для цикла
                    if (isFalseBreakdown(subListLow.get(2), allStockData.get(j))) {
                        for (int k = j + 1; k <= j + 5; k++) { // Упрощенное условие для цикла
                            if (isSignal(allStockData.get(j), allStockData.get(k))) {
                                ArrayList<Stock> lastMatchingSignal = new ArrayList<>(subListLow);
                                lastMatchingSignal.add(allStockData.get(j));
                                lastMatchingSignal.add(allStockData.get(k));
                                lastMatchingSignal.add(createLongPoint(allStockData.get(k)));

                                return lastMatchingSignal; // Возвращаем сразу, как только нашли подходящий сигнал
                            }
                        }
                    }
                }
            }
        }
        return new ArrayList<>(); // Возвращаем пустой список, если ничего не найдено
    }
    private boolean isFractal(List<Stock> subListStock) {
        return subListStock.get(0).getLow() > subListStock.get(1).getLow() &&
                subListStock.get(1).getLow() > subListStock.get(2).getLow() &&
                subListStock.get(2).getLow() < subListStock.get(3).getLow() &&
                subListStock.get(3).getLow() < subListStock.get(4).getLow();
    }
    private boolean isFalseBreakdown(Stock minFractal, Stock nextBeforeFractal) {
        return minFractal.getLow() > nextBeforeFractal.getLow();
    }
    private boolean isSignal(Stock breakdown, Stock nextBeforeBreakdown) {
        return breakdown.getOpen() < nextBeforeBreakdown.getClose() && !isTrueBreakdown(breakdown, nextBeforeBreakdown);
    }
    private boolean isTrueBreakdown(Stock breakdown, Stock nextBeforeBreakdown) {
        return breakdown.getLow() > nextBeforeBreakdown.getClose();
    }
    private Stock createLongPoint(Stock signal) {
        return new Stock(signal.getSecid(), 0, 0, signal.getClose(), 0, signal.getShortname(), signal.getTradedate());
    }
}
