package com.complex.finAdvisor.controller;

import com.complex.finAdvisor.dto.TariffRequest;
import com.complex.finAdvisor.entity.TariffEntity;
import com.complex.finAdvisor.entity.UserEntity;
import com.complex.finAdvisor.repository.TariffRepository;
import com.complex.finAdvisor.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Тарифы", description = "Контроллер для работы с тарифами")
@AllArgsConstructor
@RequestMapping("/tariff")
public class TariffController {
    private final TariffRepository tariffRepository;
    private final UserRepository userRepository;
    @GetMapping("/getAll")
    @Operation(summary = "Возвращает все сущестующие тарифы")
    public List<TariffEntity> getAllTariffs() {
        return tariffRepository.findAll();
    }

    @PutMapping("/updateByUser")
    @Operation(summary = "Обновляет тариф авторизированного пользователя. Указание тарифа в теле запроса")
    public ResponseEntity<?> updateByUser(@RequestBody @Parameter(description = "Тело запроса для обновления тарифа. Описание тарифа") TariffRequest tariffRequest,
                                          @Parameter(description = "Пользователь, прошедший успешно аутентификацию") Principal principal) {
        try {
            Optional<UserEntity> currentUser = userRepository.findByUsername(principal.getName());
            currentUser.ifPresent(userEntity -> {
                Optional<TariffEntity> currentTariff = tariffRepository.findByName(tariffRequest.getName());
                currentTariff.ifPresent(userEntity::setTariff);
                userEntity.setTariffExpiration(LocalDateTime.parse(tariffRequest.getSaleDate()).plusDays(tariffRequest.getDuration()));
                userRepository.save(userEntity);
            });
            return ResponseEntity.ok("Updated tariff to " + tariffRequest.getName() + "for user - " + principal.getName());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
