package com.complex.finAdvisor.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Tag(name = "Сущность отношений тарифов к инструментам", description = "Структура данных из базы об отношении тариф-инструмент")
@Table(name = "stock_tariff")
public class InstrumentTariffEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id отношения", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private TariffEntity tariff;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private InstrumentEntity instrument;

    @Override
    public String toString() {
        return "Id:" + id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
