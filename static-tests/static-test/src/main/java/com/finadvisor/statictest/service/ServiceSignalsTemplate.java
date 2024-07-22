package com.finadvisor.statictest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.finadvisor.statictest.domain.dto.Stock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
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

            ArrayList<Stock> signalDtos = new ArrayList<>();
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

                    Stock signalDto = new Stock();
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
    public ArrayList<Stock> findLastSignal(ArrayList<Stock> allStockDatumDtos) {
        outerLoop:
        for (int i = allStockDatumDtos.size() - 5; i >= 0; i--) {
            // На всём промежутке для начала ищется фрактал с конца списка свечей (то есть самый свежий)
            List<Stock> subListLow = allStockDatumDtos.subList(i, i + 5);
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
                                ArrayList<Stock> lastMatchingSignal = new ArrayList<>(subListLow);
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
    private boolean isFractal(List<Stock> subListSignalDto) {
        return
         subListSignalDto.get(2).getLow() < subListSignalDto.get(1).getLow() &&
         subListSignalDto.get(2).getLow() < subListSignalDto.get(3).getLow() &&
         subListSignalDto.get(2).getLow() < subListSignalDto.get(0).getLow() &&
         subListSignalDto.get(2).getLow() < subListSignalDto.get(4).getLow();
    }
    // true - "зелёная" свеча
    // false - "красная" свеча
    private boolean typeBreakdown(Stock breakdown) {
        return breakdown.getClose() > breakdown.getOpen();
    }
    // Проверка на ложный пробой
    private boolean isFalseBreakdown(Stock minFractal, Stock nextBeforeFractal) {
        return minFractal.getLow() > nextBeforeFractal.getLow();
    }
    // Проверка на сигнальную свечу
    private boolean isSignal(Stock  breakdown, Stock nextBeforeBreakdown, boolean flagTypeBreakdown) {
        if (flagTypeBreakdown) return breakdown.getClose() < nextBeforeBreakdown.getClose() && !isTrueBreakdown(breakdown, nextBeforeBreakdown);
        else return breakdown.getOpen() < nextBeforeBreakdown.getClose() && !isTrueBreakdown(breakdown, nextBeforeBreakdown);
    }
    // Проверка на истинный пробой
    private boolean isTrueBreakdown(Stock breakdown, Stock nextBeforeBreakdown) {
        return breakdown.getLow() > nextBeforeBreakdown.getClose();
    }
    // Создание структуры для сигнала
    private Stock createLongPoint(Stock signal) {
        return new Stock(signal.getSecid(), 0, 0, signal.getClose(), 0, signal.getShortname(), signal.getTradedate().plusDays(1));
    }
}

