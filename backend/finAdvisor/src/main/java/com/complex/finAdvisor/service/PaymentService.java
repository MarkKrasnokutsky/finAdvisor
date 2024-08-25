package com.complex.finAdvisor.service;

import com.complex.finAdvisor.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {
    @Value("${ukassa.payment.url}")
    private String url;
    @Value("${ukassa.payment.username}")
    private String username;
    @Value("${ukassa.payment.password}")
    private String password;
    @Value("${ukassa.payment.return-url}")
    private String returnUrl;

    public String sendPayment(PaymentCustomRequest customRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username, password);
        headers.set("Idempotence-Key", String.valueOf(Math.random()));

        PaymentRequest request = new PaymentRequest();
        PaymentAmountRequest amountRequest = new PaymentAmountRequest();
        amountRequest.setValue(customRequest.getValue());
        amountRequest.setCurrency(customRequest.getCurrency());
        request.setAmount(amountRequest);
        PaymentConfirmationRequest confirmationRequest = new PaymentConfirmationRequest();
        confirmationRequest.setType("redirect");
        confirmationRequest.setReturn_url(returnUrl);
        request.setConfirmation(confirmationRequest);
        PaymentReceiptRequest paymentReceipt = new PaymentReceiptRequest();
        PaymentCustomerRequest paymentCustomer = new PaymentCustomerRequest();
        paymentCustomer.setFull_name(customRequest.getFullName());
        paymentCustomer.setEmail(customRequest.getEmail());
        paymentReceipt.setCustomer(paymentCustomer);
        List<PaymentItemRequest> paymentItems = new ArrayList<PaymentItemRequest>();
        PaymentItemRequest paymentItem = new PaymentItemRequest();
        paymentItem.setDescription(customRequest.getDescription());
        paymentItem.setQuantity(1.0);
        paymentItem.setAmount(amountRequest);
        paymentItem.setVat_code(2);
        paymentItem.setPayment_mode("full_prepayment");
        paymentItem.setPayment_subject("commodity");
        paymentItems.add(paymentItem);
        paymentReceipt.setItems(paymentItems);
        request.setReceipt(paymentReceipt);
        HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(request, headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url, requestEntity, String.class);
    }

    public String checkPayment(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username, password);
        headers.set("Idempotence-Key", String.valueOf(Math.random()));
        String checkUrl = url + "/" + id;
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        // Выполнение GET-запроса
        ResponseEntity<String> response = restTemplate.exchange(checkUrl, HttpMethod.GET, requestEntity, String.class);
        return response.getBody();
    }
}
