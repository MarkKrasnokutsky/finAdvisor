package com.complex.finAdvisor.util;

import com.complex.finAdvisor.entity.InstrumentTariffEntity;
import com.complex.finAdvisor.entity.TariffEntity;
import com.complex.finAdvisor.repository.StockRepository;
import com.complex.finAdvisor.repository.StockTariffRepository;
import com.complex.finAdvisor.repository.TariffRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class TariffParser {
    public final StockRepository stockRepository;
    public final StockTariffRepository stockTariffRepository;
    private final TariffRepository tariffRepository;

    public void parseJson(String url) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(new File(url));

            Iterator<Map.Entry<String, JsonNode>> fields = root.fields();

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String fieldName = field.getKey();
                System.out.println("-----------" + fieldName + "-----------");
                TariffEntity currentTariff = new TariffEntity();
                currentTariff.setName(fieldName);
                tariffRepository.save(currentTariff);
                JsonNode fieldValue = field.getValue();

                List<String> values = new ArrayList<>();
                if (fieldValue.isArray()) {
                    for (JsonNode node : fieldValue) {
                        values.add(node.asText());
                        InstrumentTariffEntity relationship = new InstrumentTariffEntity();
                        relationship.setTariff(currentTariff);
                        relationship.setInstrument(stockRepository.findBySecid(node.asText()));
                        stockTariffRepository.save(relationship);
                        System.out.println(node.asText());
                        System.out.println("Find it: " + stockRepository.findBySecid(node.asText()).getShortname());
                    }
                }
                currentTariff.setInstrumentCount(values.size());
                tariffRepository.save(currentTariff);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
}
