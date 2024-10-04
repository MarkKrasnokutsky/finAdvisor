package com.complex.finAdvisor.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Объект сигнала в истории", description = "Структура данных из при запросе сигналов")
public class SignalHistoryDto {
    @Schema(description = "Id сигнала", example = "1")
    private long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Schema(description = "Дата покупки по сигналу", example = "29-02-2024")
    private LocalDate date;
    @Schema(description = "Id инструмента сигнала по МосБиржи", example = "AFLT")
    private String secid;
    @Schema(description = "Название инструмента сигнала по МосБиржи", example = "Аэрофлот")
    private String shortname;
    @Schema(description = "Цена открытия(предполагаемая) к дате покупки по сигналу", example = "33.3")
    private double open;
    @Schema(description = "Фиксация прибыли", example = "100.0")
    private Double profitFix;
    @Schema(description = "СТОП на продажу, если акция упала", example = "33.3")
    private Double stop;
}
