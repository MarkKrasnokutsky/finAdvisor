package com.complex.finAdvisor.dto;

import lombok.Data;

@Data
public class PaymentItemRequest {
    private String description;
    private Double quantity;
    private PaymentAmountRequest amount;
    private Integer vat_code;
    private String payment_mode;
    private String payment_subject;
}
