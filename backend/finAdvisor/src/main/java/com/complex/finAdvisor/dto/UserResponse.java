package com.complex.finAdvisor.dto;

import com.complex.finAdvisor.entity.TariffEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Тело ответа пользователя", description = "Описание сущности пользователя. Основные пользвательские данные")
public class UserResponse {
    @Schema(description = "Логин пользователя", example = "Mark_12")
    private String username;
    @Schema(description = "Тариф пользователя")
    private TariffEntity tariff;
    @Schema(description = "Почта пользователя", example = "mark@inbox.com")
    private String email;
    @Schema(description = "Длительность текущего тарифа пользователя (в сутках)", example = "30")
    private Integer tariffDuration;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss")
    @Schema(description = "Дата начала текущего тарифного плана пользователя", example = "03-12-2007T10:15:30")
    private LocalDateTime tariffInception;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss")
    @Schema(description = "Дата истечения текущего тарифа пользователя", example = "03-12-2007T10:15:30")
    private LocalDateTime tariffExpiration;
    @Schema(description = "Никнейм пользователя в телеграмме", example = "mark")
    private String tgNickname;
}
