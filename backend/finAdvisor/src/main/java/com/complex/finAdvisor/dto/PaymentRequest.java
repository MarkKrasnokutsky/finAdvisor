package com.complex.finAdvisor.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private PaymentAmountRequest amount;
    private boolean capture;
    private String description;
    private PaymentConfirmationRequest confirmation;
}
