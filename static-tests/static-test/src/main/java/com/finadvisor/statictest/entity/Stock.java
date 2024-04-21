package com.finadvisor.statictest.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Getter
    @Setter
    private String secid;
    @Getter
    @Setter
    private double low;
    @Getter
    @Setter
    private double high;
    @Getter
    @Setter
    private double open;
    @Getter
    @Setter
    private double close;
    @Getter
    @Setter
    private String shortname;
    @Getter
    @Setter
    private Date tradedate;
}
