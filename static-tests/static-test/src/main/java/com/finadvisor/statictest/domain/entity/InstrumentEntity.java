package com.finadvisor.statictest.domain.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JacksonXmlRootElement(localName = "row")
public class InstrumentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JacksonXmlProperty(localName = "SECID")
    private String secid;

    @JacksonXmlProperty(localName = "BOARDID")
    private String boardid;

    @JacksonXmlProperty(localName = "SHORTNAME")
    private String shortname;

//    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<StockSignal> stockSignals;
}
