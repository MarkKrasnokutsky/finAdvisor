package com.complex.finAdvisor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;


@Data
@Tag(name = "Тело запроса тарифа", description = "Описание сущности тарифа в теле запроса")
public class TariffRequest {
    @Schema(description = "Название тарифа присваемого пользователю", example = "simple")
    private String name;
    @Schema(description = "Длительность тарифного плана (в днях)", example = "40")
    private int duration;
}
