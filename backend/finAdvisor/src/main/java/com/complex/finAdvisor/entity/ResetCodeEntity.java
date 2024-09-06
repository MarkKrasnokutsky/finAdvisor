package com.complex.finAdvisor.entity;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reset_code")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Сущность кода сброса пароля", description = "Структура данных из базы кода сброса пароля")
public class ResetCodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String email; // Или другой идентификатор пользователя

    private LocalDateTime createdAt;

    private static final int EXPIRATION_TIME = 5; // Время жизни токена в минутах

    public ResetCodeEntity(String code, String email) {
        this.code = code;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(createdAt.plusMinutes(EXPIRATION_TIME));
    }
}
