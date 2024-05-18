package com.complex.finAdvisor.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TariffRequest {
    private String name;
    private int cost;
    private String saleDate;
    private int duration;
}
