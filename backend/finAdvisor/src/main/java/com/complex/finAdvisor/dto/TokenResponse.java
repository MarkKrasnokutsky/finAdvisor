package com.complex.finAdvisor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Тело ответа при авторизация", description = "Пара токенов. Первый - токен, второй - токен для обновления")
public class TokenResponse {
    @Schema(description = "Access token")
    private String token;
    @Schema(description = "Refresh token")
    private String refreshToken;
}
