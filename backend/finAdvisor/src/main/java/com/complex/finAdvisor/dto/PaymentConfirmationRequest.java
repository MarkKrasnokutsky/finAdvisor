package com.complex.finAdvisor.dto;

import lombok.Data;

@Data
public class PaymentConfirmationRequest {
    private String type;
    private String return_url;
}
