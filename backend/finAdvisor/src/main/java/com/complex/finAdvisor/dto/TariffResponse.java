package com.complex.finAdvisor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Тело ответа на обновление тарифа", description = "Описание тарифа в теле ответа")
public class TariffResponse {
    @Schema(description = "Название тарифа присваемого пользователю", example = "simple")
    private String name;

    @Schema(description = "Длительность тарифного плана (в днях)", example = "40")
    private Integer duration;

    @Schema(description = "Дата начала текущего тарифного плана пользователя", example = "2007-12-03T10:15:30")
    private LocalDateTime tariffInception;

    @Schema(description = "Дата истечения текущего тарифа пользователя", example = "2007-12-03T10:15:30")
    private LocalDateTime tariffExpiration;
}
