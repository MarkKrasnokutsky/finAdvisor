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
    @Schema(description = "Access token",
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJOaWtvbGF5IiwiaWF0IjoxNzE4NzEyMzU1LCJleHAiOjE3MTkzMTcxNTV9.ac3m-4oRi4WQ-RQuuLrcCAcoDYpc1xby3TuUvHmAINs")
    private String token;
    @Schema(description = "Refresh token",
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJOaWtvbGF5IiwiaWF0IjoxNzE4NzEyMzIxLCJleHAiOjE3MjEzNDAzMjF9.K8-0HZ5ix4Q6O3BQ80hV6fzDRJ_Gzu_P4VnTRiQgN_o")
    private String refreshToken;
}
