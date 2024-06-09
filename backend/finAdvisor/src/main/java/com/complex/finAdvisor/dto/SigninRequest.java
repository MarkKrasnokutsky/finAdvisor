package com.complex.finAdvisor.dto;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Tag(name = "Тело запроса авторизации", description = "Содержание post-запроса")
public class SigninRequest {
    /**
     *  Логин пользователя должен быть длиной не менее, чем 4 символа, состоять из латинских букв,
     *  цифр и нижних подчёркиваний
     * */
    @NotBlank(message = "Логин пользователя не может быть пустой")
    @Pattern(regexp = "^[a-zA-Z0-9_]{4,40}$", message = "Некорректный логин")
    private String username;
    /**
     *  Пароль пользователя должен быть от 8 до 40 символов,
     *  в нем можно использовать цифры, символы и буквы латинского алфавита.
     *  При этом обязательно в пароле должна быть хотя бы одна цифра,
     *  одна буква в нижнем регистре и одна буква в верхнем регистре.
     * */
    @NotBlank(message = "Пароль пользователя не может быть пустой")
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,40})", message = "Некорректный пароль")
    private String password;
}
