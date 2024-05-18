package com.complex.finAdvisor.controller;

import com.complex.finAdvisor.dto.TelegramRequest;
import com.complex.finAdvisor.entity.TariffEntity;
import com.complex.finAdvisor.entity.UserEntity;
import com.complex.finAdvisor.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Пользователи", description = "Контроллер для работы с пользователями")
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
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
    public ResponseEntity<?> updateTelegram(@RequestBody TelegramRequest telegramRequest, Principal principal) {
        Optional<UserEntity> currentUser = userRepository.findByUsername(principal.getName());
        currentUser.ifPresent(userEntity -> {
            userEntity.setTgNickname(telegramRequest.getNickname());
            userRepository.save(userEntity);
        });
        return ResponseEntity.ok("Updated telegram nickname to " + telegramRequest.getNickname() + "for user - " + principal.getName());
    }
    @GetMapping("/getTelegram")
    @Operation(summary = "Возвращает телеграм ник текущего авторизированного пользователя")
    public String updateTelegram(Principal principal) {
        Optional<UserEntity> currentUser = userRepository.findByUsername(principal.getName());
        var ref = new Object() {
            String telegramNickname;
        };
        currentUser.ifPresent(userEntity -> {
            ref.telegramNickname = userEntity.getTgNickname();
        });
        return ref.telegramNickname;
    }
}
