package com.complex.finAdvisor.dto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;


@Data
@Tag(name = "Тело запроса тарифа", description = "Описание сущности тарифа в теле запроса")
public class TariffRequest {
    private String name;
    private String saleDate;
    private int duration;
}
