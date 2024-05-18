package com.complex.finAdvisor.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Tag(name = "Сущность сигнала", description = "Структура данных из базы о сигнале")
@Table(name="stock_signal")
public class StockSignalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id сигнала", example = "1")
    private long id;
    @Column(name = "tradedate")
    @Schema(description = "Дата покупки по сигналу", example = "2024-02-29")
    private LocalDate date;
    @Column(name = "sec_id")
    @Schema(description = "Id инструмента сигнала по МосБиржи", example = "AFLT")
    private String secid;
    @Column(name = "shortname")
    @Schema(description = "Название инструмента сигнала по МосБиржи", example = "Аэрофлот")
    private String shortname;
    @Column(name = "open")
    @Schema(description = "Цена открытия(предполагаемая) к дате покупки по сигналу", example = "33.3")
    private double open;
//    @ManyToOne
//    @JoinColumn(referencedColumnName = "stock_id", nullable = false)
//    private Instrument stock;
}
