package com.complex.finAdvisor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Tag(name = "Сущность тарифа", description = "Структура данных из базы о тарифе")
@Table(name = "tariff")
public class TariffEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id тарифа", example = "1")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    @Schema(description = "Название тарифа", example = "Pro")
    private String name;

    @Column(name = "cost")
    @Schema(description = "Стоимость тарифа за месяц", example = "5999")
    private Double cost;

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Schema(description = "Список пользователей тарифа", example = "[2,3,5]")
    private Set<UserEntity> users = new HashSet<>();

    @Column(name = "instrument_count")
    @Schema(description = "Количество доступных инструментов", example = "100")
    private int instrumentCount;

    @JsonIgnore
    @OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Schema(description = "Список отношений инструмент-тариф", example = "[2,3,5]")
    private Set<InstrumentTariffEntity> relationship = new HashSet<>();

    @Override
    public String toString() {
        return "Id:" + id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, cost);
    }
}
