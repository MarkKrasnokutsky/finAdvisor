package com.finadvisor.statictest.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
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
@Table(name="stock_signal")
public class StockSignal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "tradedate")
    private LocalDate date;
    @Column(name = "sec_id")
    private String secid;
    @Column(name = "shortname")
    private String shortname;
    @Column(name = "open")
    private double open;
//    @ManyToOne
//    @JoinColumn(name = "stock_id", nullable = false)
//    private Instrument stock;
}
