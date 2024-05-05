package com.finadvisor.statictest.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import com.finadvisor.statictest.domain.entity.InstrumentEntity;
import com.finadvisor.statictest.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceStock {
    private final StockRepository stockRepository;
//    public void resetIds() {
//        this.stockRepository.deleteAllAndResetIds();
//        this.stockRepository.resetAutoIncrement();
//    }
    public List<InstrumentEntity> fetchStocks() {
        String apiUrl = "https://iss.moex.com/iss/engines/stock/markets/shares/boards/TQBR/securities.xml";
        RestTemplate restTemplate = new RestTemplate();
        String xmlResponse = restTemplate.getForObject(apiUrl, String.class);
        // Парсинг и сохранение всех элементов <row> в базу данных
        List<InstrumentEntity> stocks = parseXmlResponse(xmlResponse);
        return this.stockRepository.saveAll(stocks);
    }
    private ArrayList<InstrumentEntity> parseXmlResponse(String xmlResponse) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode dataNodes = xmlMapper.readTree(xmlResponse).get("data");
            ArrayList<InstrumentEntity> stocks = new ArrayList<>();
            for (JsonNode dataNode : dataNodes) {
                if (dataNode.has("id") && dataNode.get("id").asText().equals("securities")) {
                    JsonNode rowNodes = dataNode.get("rows").get("row");
                    for (JsonNode rowNode : rowNodes) {
                        String shortname = rowNode.get("SHORTNAME").asText();
                        String secid = rowNode.get("SECID").asText();
                        String boardid = rowNode.get("BOARDID").asText();
                        InstrumentEntity stock = new InstrumentEntity();
                        stock.setSecid(secid);
                        stock.setShortname(shortname);
                        stock.setBoardid(boardid);
                        stocks.add(stock);
                    }
                }
            }
            return stocks;
        } catch (IOException e) {
            // Обработка ошибки парсинга XML
            return null;
        }
    }
}

