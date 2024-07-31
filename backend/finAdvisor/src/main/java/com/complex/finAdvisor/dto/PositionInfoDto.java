package com.complex.finAdvisor.dto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Модель информации о списке данных об инструменте с МосБиржи", description = "Структуктура данных информации списка")
public class PositionInfoDto {
    @Getter
    @Setter
    private int index;
    @Getter
    @Setter
    private int total;
    @Getter
    @Setter
    private int pageSize;
}
