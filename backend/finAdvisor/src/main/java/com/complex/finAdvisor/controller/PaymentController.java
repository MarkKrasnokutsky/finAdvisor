package com.complex.finAdvisor.controller;

import com.complex.finAdvisor.dto.PaymentCustomRequest;
import com.complex.finAdvisor.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/payment")
@Tag(name = "Оплата", description = "Контроллер для оплаты")
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("/create")
    public String createPayment(@RequestBody PaymentCustomRequest paymentCustomRequest) {
        String description = paymentCustomRequest.getDescription();
        return paymentService.sendPayment(paymentCustomRequest);
    }

    @GetMapping("/check/{id}")
    public String checkPayment(@PathVariable String id) {
        return paymentService.checkPayment(id);
    }
}
