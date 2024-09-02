package com.complex.finAdvisor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    private String email;
    private String code;
    /**
     *  Пароль пользователя должен быть от 8 до 40 символов,
     *  в нем можно использовать цифры, символы и буквы латинского алфавита.
     *  При этом обязательно в пароле должна быть хотя бы одна цифра,
     *  одна буква в нижнем регистре и одна буква в верхнем регистре.
     * */
    @NotBlank(message = "Пароль пользователя не может быть пустой")
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,40})", message = "Некорректный пароль")
    @Schema(description = "Пароль пользователя." +
            "Правила валидации:" +
            "Пароль пользователя должен быть от 8 до 40 символов,\n" +
            "в нем можно использовать цифры, символы и буквы латинского алфавита.\n" +
            "При этом обязательно в пароле должна быть хотя бы одна цифра,\n" +
            "одна буква в нижнем регистре и одна буква в верхнем регистре.", example = "Mark12032004")
    private String newPassword;
}
