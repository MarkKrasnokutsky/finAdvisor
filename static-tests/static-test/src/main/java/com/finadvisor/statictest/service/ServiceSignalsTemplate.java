package com.finadvisor.statictest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.finadvisor.statictest.dto.Stock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceSignalsTemplate {
    public ArrayList<Stock> fetchStockEvents(String shortNameStock, int limit, int start) {
        String apiUrl = "https://iss.moex.com/iss/history/engines/stock/markets/shares/boards/TQBR/securities/" + shortNameStock + ".xml?limit=" + limit + "&start=" + start;
        RestTemplate restTemplate = new RestTemplate();
        String xmlResponse = restTemplate.getForObject(apiUrl, String.class);
        // Парсинг и сохранение всех элементов <row> в базу данных
        return parseXmlResponse(xmlResponse);
    }
    private ArrayList<Stock> parseXmlResponse(String xmlResponse) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode node = xmlMapper.readTree(xmlResponse).get("data").get("rows").get("row");
            ArrayList<Stock> stocks = new ArrayList<>();
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
