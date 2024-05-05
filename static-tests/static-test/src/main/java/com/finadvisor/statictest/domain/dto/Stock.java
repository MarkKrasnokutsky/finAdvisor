package com.finadvisor.statictest.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private LocalDate tradedate;

    @Override
    public String toString() {
        return "TRADEDATE:" +  this.tradedate + ", SECID:" + this.secid + ", SHORTNAME:" + this.shortname + ", OPEN:" + this.open + ",LOW:"
                + this.low + ", HIGH:" + this.high + ", CLOSE:" + this.close;
    }
}
