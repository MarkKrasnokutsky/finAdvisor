package com.finadvisor.statictest.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JacksonXmlRootElement(localName = "row")
public class Instrument {
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
