package com.finadvisor.statictest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.finadvisor.statictest.entity.Stock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceStock {
    public ArrayList<Stock> fetchStocks(String shortNameStock, int limit, int start) {
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
            System.out.println(node);
            ArrayList<Stock> stocks = new ArrayList<>();
            int index = 0;
            for (JsonNode item : node) {

                String shortname = item.get("SHORTNAME").asText();
                String secid = item.get("SECID").asText();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String tradeDateStr = item.get("TRADEDATE").asText();
                Date tradeDate = formatter.parse(tradeDateStr);
                double open = item.get("OPEN").asDouble();
                double high = item.get("HIGH").asDouble();
                double low = item.get("LOW").asDouble();
                double close = item.get("CLOSE").asDouble();
                Stock stock = new Stock();
                index++;
                if(index >= 4) {
                    index = 0;
                }
                stock.setSecid(secid);
                stock.setTradedate(tradeDate);
                stock.setOpen(open);
                stock.setLow(low);
                stock.setHigh(high);
                stock.setClose(close);
                stock.setShortname(shortname);
                stocks.add(stock);
            }
            //Collections.reverse(stocks);
            return stocks;
        } catch (IOException e) {
            // Обработка ошибки парсинга XML
            return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Stock> findLastSignal(ArrayList<Stock> allStockData) {
        ArrayList<Stock> lastSignal = new ArrayList<>();
        for (int i = allStockData.size() - 5; i >= 0; i--) {
            // Выборка предположительного фрактала
            List<Stock> subListLow = allStockData.subList(i, i + 5);
            // Проверка на истинный фрактал и его минимум
            if (subListLow.get(0).getLow() > subListLow.get(1).getLow() && subListLow.get(1).getLow() > subListLow.get(2).getLow()
                    && subListLow.get(2).getLow() < subListLow.get(3).getLow() && subListLow.get(3).getLow() < subListLow.get(4).getLow()) {
                if (i + 5 + 10 <= allStockData.size()) {
                    // Двигаемся на 10 свечей вперед, не учитывая первые две после фаркатала
                    for (int j = i + 5, count_j = 0; j < allStockData.size() && count_j < 10; j++, count_j++) {
                        // Проврка на пробойную свечу
                        if (subListLow.get(2).getLow() > allStockData.get(j).getLow()) {
                            if (j + 6 <= allStockData.size()) {
                                // Двигаемся на 5 свечей вперед от пробойной свечи
                                for (int k = j + 1, count_k = 0; k < allStockData.size() && count_k < 5; k++, count_k++) {
                                    // Проверка на сигнальную свечу И на НЕ истинный пробой
                                    if(allStockData.get(j).getOpen() < allStockData.get(k).getClose() && !(allStockData.get(j).getLow() > allStockData.get(k).getClose())) {
                                        if (k + 1 < allStockData.size()) {
                                            Stock stockLongOpen = new Stock();
                                            stockLongOpen.setSecid(allStockData.get(k).getSecid());
                                            // Закрытие в n день. Сигнал в n + 1 день в 9:30
                                            stockLongOpen.setTradedate(allStockData.get(k).getTradedate());
                                            stockLongOpen.setOpen(allStockData.get(k).getClose());
                                            stockLongOpen.setLow(0);
                                            stockLongOpen.setHigh(0);
                                            stockLongOpen.setClose(0);
                                            stockLongOpen.setShortname(allStockData.get(k).getShortname());
                                            //Stock stockLongOpen = allStockData.get(k + 1);
                                            // Истинный фрактал
                                            lastSignal.addAll(subListLow);
                                            // Пробойная свеча
                                            lastSignal.add(allStockData.get(j));
                                            // Сигнальная свеча
                                            lastSignal.add(allStockData.get(k));
                                            // Точка входа
                                            lastSignal.add(stockLongOpen);
                                            return lastSignal;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return lastSignal;
    }
}
