package com.complex.finAdvisor.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private PaymentAmountRequest amount;
    private PaymentReceiptRequest receipt;
    private PaymentConfirmationRequest confirmation;
    private boolean capture;
}
