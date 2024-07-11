package com.complex.finAdvisor.controller;

import com.complex.finAdvisor.dto.SigninRequest;
import com.complex.finAdvisor.dto.SignupRequest;
import com.complex.finAdvisor.dto.TokenRefreshResponse;
import com.complex.finAdvisor.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Tag(name = "Аутентификация", description = "Контроллер для работы с регистрацией/авторизацией")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SecurityController {
    private final SecurityService securityService;

    @PostMapping("/signup")
    @Operation(summary = "Регистрация пользователя")
    ResponseEntity<?> signup(@Valid @RequestBody @Parameter(description = "Тело запроса на регистрацию") SignupRequest signupRequest) {
        return securityService.register(signupRequest);
    }

    @PostMapping("/signin")
    @Operation(summary = "Авторизация пользователя. Запись токенов в куки-файлы")
    ResponseEntity<?> signin(@Valid @RequestBody @Parameter(description = "Тело запроса на авторизацию") SigninRequest signinRequest,
                             HttpServletResponse response) {
        return securityService.login(signinRequest, response);
    }
    @PostMapping("/refresh_token")
    @Operation(summary = "Обновление access-токена по refresh-токену с записью в куки-файлы")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
        return securityService.refresh(request, response);
    }
}
