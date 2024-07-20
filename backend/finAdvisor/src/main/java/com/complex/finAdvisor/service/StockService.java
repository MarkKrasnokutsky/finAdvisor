package com.complex.finAdvisor.service;

import com.complex.finAdvisor.entity.InstrumentEntity;
import com.complex.finAdvisor.entity.InstrumentTariffEntity;
import com.complex.finAdvisor.entity.StockSignalEntity;
import com.complex.finAdvisor.entity.UserEntity;
import com.complex.finAdvisor.repository.StockRepository;
import com.complex.finAdvisor.repository.StockTariffRepository;
import com.complex.finAdvisor.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final UserRepository userRepository;
    private final StockTariffRepository stockTariffRepository;
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
    public List<InstrumentEntity> getInstrumentsByUser(String username) {
        try {
            List<InstrumentEntity> instrumentEntities = new ArrayList<>();
            Optional<UserEntity> currentUser = userRepository.findByUsername(username);

            currentUser.ifPresent(userEntity -> {
                Long currentTariff = userEntity.getTariff().getId();
                List<InstrumentTariffEntity> listRelationship = stockTariffRepository.findByTariffId(currentTariff);

                for (InstrumentTariffEntity relationship : listRelationship) {
                    instrumentEntities.add(relationship.getInstrument());
                }
            });
            return instrumentEntities;
        } catch (Exception e) {
            return null;
        }
    }
}

