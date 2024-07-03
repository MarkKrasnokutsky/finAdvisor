package com.complex.finAdvisor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Tag(name = "Сущность инструмента", description = "Структура данных из базы об инструменте")
@Table(name = "stock")
@JacksonXmlRootElement(localName = "row")
public class InstrumentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id инструмента", example = "1")
    private long id;

    @JacksonXmlProperty(localName = "SECID")
    @Schema(description = "Id инструмента из МосБиржи", example = "SBER")
    private String secid;

    @JacksonXmlProperty(localName = "BOARDID")
    @Schema(description = "Режим торгов. Единственное значение - TQBR", example = "TQBR")
    private String boardid;

    @JacksonXmlProperty(localName = "SHORTNAME")
    @Schema(description = "Название инструмента", example = "Сбербанк ао")
    private String shortname;

    @JsonIgnore
    @OneToMany(mappedBy = "instrument", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Schema(description = "Список отношений инструмент-тариф", example = "[2,3,5]")
    private Set<InstrumentTariffEntity> relationship = new HashSet<>();

    @Override
    public String toString() {
        return "Id:" + id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, secid, boardid, shortname);
    }

    //    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<StockSignal> stockSignals;
}
