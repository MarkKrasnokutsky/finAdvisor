package com.complex.finAdvisor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Tag(name = "Тестовая защищенная область роутов", description = "Контроллер для теста защищенной области rest-контроллера только для авторизированного пользователя")
@RequestMapping("/secured")
public class MainController {
    @RequestMapping("/user")
    @Operation(summary = "Возвращает логин объекта Principal - пользователя, который успешно прошёл аутентификацию")
    public String userAccess(@Parameter(description = "The authenticated user") Principal principal) {
        if (principal == null){
            return null;
        }
        return principal.getName();
    }
}
