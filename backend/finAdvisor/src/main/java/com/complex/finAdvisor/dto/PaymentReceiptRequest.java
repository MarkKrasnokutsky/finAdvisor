package com.complex.finAdvisor.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaymentReceiptRequest {
    private PaymentCustomerRequest customer;
    private List<PaymentItemRequest> items;
}
