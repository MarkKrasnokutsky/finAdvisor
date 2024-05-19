package com.complex.finAdvisor.controller;

import com.complex.finAdvisor.dto.UserResponse;
import com.complex.finAdvisor.entity.TariffEntity;
import com.complex.finAdvisor.entity.UserEntity;
import com.complex.finAdvisor.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Tag(name = "Тестовая защищенная область роутов", description = "Контроллер для теста защищенной области rest-контроллера только для авторизированного пользователя")
@RequestMapping("/secured")
public class MainController {
    private final UserRepository userRepository;

    @RequestMapping("/user")
    @Operation(summary = "Возвращает логин объекта Principal - пользователя, который успешно прошёл аутентификацию")
    public ResponseEntity<UserResponse> userAccess(@Parameter(description = "The authenticated user") Principal principal) {
        if (principal == null) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            Optional<UserEntity> currentUser = userRepository.findByUsername(principal.getName());
            if (currentUser.isPresent()) {
                UserEntity userEntity = currentUser.get();
                TariffEntity currentTariff = userRepository.findTariffByUserId(userEntity.getId());
                String currentEmail = userEntity.getEmail();
                UserResponse userResponse = new UserResponse();
                userResponse.setEmail(currentEmail);
                userResponse.setUsername(principal.getName());
                userResponse.setTariff(currentTariff);
                return ResponseEntity.ok(userResponse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
