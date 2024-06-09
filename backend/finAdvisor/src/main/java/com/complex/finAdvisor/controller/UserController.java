package com.complex.finAdvisor.controller;

import com.complex.finAdvisor.dto.TelegramRequest;
import com.complex.finAdvisor.entity.TariffEntity;
import com.complex.finAdvisor.entity.UserEntity;
import com.complex.finAdvisor.repository.UserRepository;
import com.complex.finAdvisor.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Tag(name = "Пользователи", description = "Контроллер для работы с пользователями")
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    @GetMapping("/get/{id}")
    @Operation(summary = "Возвращает пользователя по id")
    public UserEntity getUserById(@PathVariable Long id) {
        return userRepository.getUserById(id);
    }
    @GetMapping("/getAll")
    @Operation(summary = "Возвращает всех пользователей")
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
    @GetMapping("/getTariff/{id}")
    @Operation(summary = "Возвращает текущий тариф пользователя по его id")
    public TariffEntity getTariffByUserId(@PathVariable Long id) {
        return userRepository.findTariffByUserId(id);
    }

    @PostMapping("/updateTelegram")
    @Operation(summary = "Обновляет телеграм-никнейм полььзователя в базе данных")
    public ResponseEntity<?> updateTelegram(@Valid @RequestBody TelegramRequest telegramRequest, Principal principal) {
        userService.setTgNickname(principal.getName(), telegramRequest.getNickname());
        return ResponseEntity.ok("Updated telegram nickname to " + telegramRequest.getNickname() + " for user - " + principal.getName());
    }
    @GetMapping("/getTelegram")
    @Operation(summary = "Возвращает телеграм ник текущего авторизированного пользователя")
    public String  getTelegram(Principal principal) {
        return userService.getTgNickname(principal.getName());
    }
}
