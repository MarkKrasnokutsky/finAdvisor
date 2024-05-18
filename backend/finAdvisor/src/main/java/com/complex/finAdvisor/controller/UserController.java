package com.complex.finAdvisor.controller;

import com.complex.finAdvisor.entity.TariffEntity;
import com.complex.finAdvisor.entity.UserEntity;
import com.complex.finAdvisor.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
