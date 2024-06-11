package com.complex.finAdvisor.controller;

import com.complex.finAdvisor.dto.SigninRequest;
import com.complex.finAdvisor.dto.SignupRequest;
import com.complex.finAdvisor.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Аутентификация", description = "Контроллер для работы с регистрацией/авторизацией")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SecurityController {
    private final SecurityService securityService;

    @PostMapping("/signup")
    @Operation(summary = "Регистрация пользователя")
    ResponseEntity<?> signup(@Valid @RequestBody @Parameter(description = "Тело запроса на регистрацию") SignupRequest signupRequest) {
        return securityService.register(signupRequest);
    }

    @PostMapping("/signin")
    @Operation(summary = "Авторизация пользователя")
    ResponseEntity<?> signin(@Valid @RequestBody @Parameter(description = "Тело запроса на авторизацию") SigninRequest signinRequest) {
        return securityService.login(signinRequest);
    }
}
