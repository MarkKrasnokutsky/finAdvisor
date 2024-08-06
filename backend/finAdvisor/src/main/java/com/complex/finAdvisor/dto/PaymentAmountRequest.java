package com.complex.finAdvisor.dto;

import lombok.Data;

@Data
public class PaymentAmountRequest {
    private Double value;
    private String currency;
}
