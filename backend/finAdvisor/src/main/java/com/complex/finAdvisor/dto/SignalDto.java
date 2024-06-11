package com.complex.finAdvisor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Модель сигнала", description = "Структуктура данных сигнала")
public class SignalDto {
    @Getter
    @Setter
    @Schema(description = "secid инструмента с МосБиржи", example = "ABIO")
    private String secid;
    @Getter
    @Setter
    @Schema(description = "наименьшая цена инструмента за день", example = "ABIO")
    private double low;
    @Getter
    @Setter
    @Schema(description = "наибольшая цена инструмента за день", example = "ABIO")
    private double high;
    @Getter
    @Setter
    @Schema(description = "цена открытия инструмента", example = "ABIO")
    private double open;
    @Getter
    @Setter
    @Schema(description = "цена закрытия инструмента", example = "ABIO")
    private double close;
    @Getter
    @Setter
    @Schema(description = "короткое название инструмента", example = "ABIO")
    private String shortname;
    @Getter
    @Setter
    @Schema(description = "дата данной статистики инструмента", example = "ABIO")
    private LocalDate tradedate;

    @Override
    public String toString() {
        return "TRADEDATE:" +  this.tradedate + ", SECID:" + this.secid + ", SHORTNAME:" + this.shortname + ", OPEN:" + this.open + ",LOW:"
                + this.low + ", HIGH:" + this.high + ", CLOSE:" + this.close;
    }
}
