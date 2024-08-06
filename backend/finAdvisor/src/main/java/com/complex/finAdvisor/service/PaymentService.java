package com.complex.finAdvisor.service;

import com.complex.finAdvisor.dto.PaymentAmountRequest;
import com.complex.finAdvisor.dto.PaymentConfirmationRequest;
import com.complex.finAdvisor.dto.PaymentCustomRequest;
import com.complex.finAdvisor.dto.PaymentRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

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
        request.setDescription(customRequest.getDescription());
        request.setCapture(true);
        PaymentConfirmationRequest confirmationRequest = new PaymentConfirmationRequest();
        confirmationRequest.setType("redirect");
        confirmationRequest.setReturn_url(returnUrl);
        request.setConfirmation(confirmationRequest);
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
