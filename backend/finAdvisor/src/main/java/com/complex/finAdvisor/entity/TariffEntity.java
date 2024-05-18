package com.complex.finAdvisor.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
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

    @Column(name = "name")
    @Schema(description = "Название тарифа", example = "Pro")
    private String name;

    @Column(name = "cost")
    @Schema(description = "Стоимость тарифа за месяц", example = "5999")
    private Double cost;

    @OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Список пользователей тарифа", example = "[2,3,5]")
    private Set<UserEntity> users = new HashSet<>();
}
