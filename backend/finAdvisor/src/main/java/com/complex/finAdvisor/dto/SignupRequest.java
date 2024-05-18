package com.complex.finAdvisor.dto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

@Data
@Tag(name = "Тело запроса регистрации", description = "Содержание post-запроса")
public class SignupRequest {
    private String username;
    private String password;
    private String email;
}
