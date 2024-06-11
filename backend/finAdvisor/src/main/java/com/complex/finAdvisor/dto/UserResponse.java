package com.complex.finAdvisor.dto;

import com.complex.finAdvisor.entity.TariffEntity;
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
@Tag(name = "Тело ответа пользователя", description = "Описание сущности пользователя. Основные пользвательские данные")
public class UserResponse {
    @Schema(description = "Логин пользователя", example = "Mark_12")
    private String username;
    @Schema(description = "Тариф пользователя")
    private TariffEntity tariff;
    @Schema(description = "Почта пользователя", example = "mark@inbox.com")
    private String email;
}
