package com.complex.finAdvisor.controller;

import com.complex.finAdvisor.service.TimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/server")
@RequiredArgsConstructor
@Tag(name = "Сервер", description = "Контроллер для получения информации о сервере")
public class ServerController {
    private final TimeService timeService;

    @GetMapping("/time")
    @Operation(summary = "Возвращает серверное время")
    public LocalDateTime getServerTime() {
        return timeService.getExtendedServerTime();
    }

}
