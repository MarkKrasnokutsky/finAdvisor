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

            return stocks;
        } catch (IOException e) {
            // Обработка ошибки парсинга XML
            return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
