package com.complex.finAdvisor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TelegramRequest {
    /**
     *  Никнейм пользователя телеграм должен соблюдать следующие требования:
     *      Символы: A-z (регистр не важен), 0-9 и "подчеркивания"
     *      Длина: 5-32
     * */
    @NotBlank(message = "Никнейм не может быть пустой")
    @Pattern(regexp = "^[a-zA-Z0-9_]{5,32}$", message = "Некорректный никнейм")
    private String nickname;
}
