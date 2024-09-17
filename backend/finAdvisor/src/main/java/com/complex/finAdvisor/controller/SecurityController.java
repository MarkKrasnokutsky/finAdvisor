package com.complex.finAdvisor.controller;

import com.complex.finAdvisor.dto.*;
import com.complex.finAdvisor.service.MailSenderService;
import com.complex.finAdvisor.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Tag(name = "Аутентификация", description = "Контроллер для работы с регистрацией/авторизацией")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SecurityController {
    private final SecurityService securityService;
    private final MailSenderService senderService;

    @PostMapping("/signup")
    @Operation(summary = "Регистрация пользователя")
    ResponseEntity<?> signup(@Valid @RequestBody @Parameter(description = "Тело запроса на регистрацию") SignupRequest signupRequest) throws MessagingException, IOException {
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

    @PostMapping("/sendCode")
    @Operation(summary = "Отправляет код подтверждения сброса пароля на указанный email")
    public ResponseEntity<?> sendCode(@RequestBody MailRequest request) throws MessagingException, IOException {
        return securityService.sendResetCode(request);
    }
    @PostMapping("/resetPassword")
    @Operation(summary = "Изменяет пароль пользователя по введенному коду")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        return securityService.resetPassword(request);
    }
}
