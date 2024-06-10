package com.complex.finAdvisor.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Tag(name = "Сущность пользователя", description = "Структура данных из базы о пользователе")
@AllArgsConstructor
@Data
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id пользователя", example = "1")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    @Schema(description = "Логин пользователя", example = "mark1_")
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    @Schema(description = "Почта пользователя", example = "m@inbox.com")
    private String email;

    @Column(name = "tg_nickname", unique = true)
    @Schema(description = "Никнейм пользователя в телеграмме", example = "mark")
    private String tgNickname;

    @Column(name = "password", nullable = false, unique = true)
    @Schema(description = "Зашифрованный пароль пользователя", example = "$2a$10$jv9WYa18CNPTk/ZvNEDybOdp9Ju5P7X/2r8Qkaf4UfYrua/uYi1k6")
    private String password;

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private TariffEntity tariff;

    @Column(name = "tariff_expiration")
    @Schema(description = "Дата истечения текущего тарифа пользователя", example = "2024-06-12 19:14:52.000000")
    private LocalDateTime tariffExpiration;

    @Column(name = "created_at", nullable = false)
    @Schema(description = "Дата регистрации пользователя", example = "2024-06-12 19:14:52.000000")
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "Id:" + id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, tgNickname, password);
    }
}
