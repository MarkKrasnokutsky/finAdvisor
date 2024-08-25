package com.complex.finAdvisor.dto;

import lombok.Data;

@Data
public class PaymentCustomRequest {
    private String fullName;
    private String email;
    private Double value;
    private String currency;
    private String description;
}
