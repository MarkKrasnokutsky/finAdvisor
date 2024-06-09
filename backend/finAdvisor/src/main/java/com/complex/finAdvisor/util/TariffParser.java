package com.complex.finAdvisor.util;

import com.complex.finAdvisor.repository.StockRepository;
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
//    public final TariffRepository tariffRepository;
    public final StockRepository stockRepository;

    public void parseJson(String url) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(new File(url));

            Iterator<Map.Entry<String, JsonNode>> fields = root.fields();

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String fieldName = field.getKey();
                System.out.println("-----------" + fieldName + "-----------");
                JsonNode fieldValue = field.getValue();

                List<String> values = new ArrayList<>();
                if (fieldValue.isArray()) {
                    for (JsonNode node : fieldValue) {
                        values.add(node.asText());
                        System.out.println(node.asText());
                        System.out.println("Find it: " + stockRepository.findBySecid(node.asText()).getShortname());
                    }
                }

                // Теперь у вас есть список значений для этого поля
                // Вы можете работать с ним как вам угодно
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
}
