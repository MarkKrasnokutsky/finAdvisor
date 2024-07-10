package com.complex.finAdvisor.dto;

import com.complex.finAdvisor.entity.TariffEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Тело ответа при обновлении токенов", description = "Данные авторизированного пользователя")
public class TokenRefreshResponse {
    @Schema(description = "Логин пользователя", example = "Mark_12")
    private String username;
    @Schema(description = "Тариф пользователя")
    private TariffEntity tariff;
    @Schema(description = "Почта пользователя", example = "mark@inbox.com")
    private String email;
    @Schema(description = "Длительность текущего тарифа пользователя (в сутках)", example = "30")
    private Integer tariffDuration;
    @Schema(description = "Дата начала текущего тарифного плана пользователя", example = "2007-12-03T10:15:30")
    private LocalDateTime tariffInception;
    @Schema(description = "Дата истечения текущего тарифа пользователя", example = "2007-12-03T10:15:30")
    private LocalDateTime tariffExpiration;
    @Schema(description = "Никнейм пользователя в телеграмме", example = "mark")
    private String tgNickname;
}
