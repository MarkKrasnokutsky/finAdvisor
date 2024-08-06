package com.complex.finAdvisor.dto;

import lombok.Data;

@Data
public class PaymentCustomRequest {
    private Double value;
    private String currency;
    private String description;
}
