package com.complex.finAdvisor.dto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

@Data
@Tag(name = "Тело запроса авторизации", description = "Содержание post-запроса")
public class SigninRequest {
    private String username;
    private String password;
}
