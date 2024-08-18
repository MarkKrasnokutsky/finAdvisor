package com.complex.finAdvisor.controller;

import com.complex.finAdvisor.dto.TariffRequest;
import com.complex.finAdvisor.dto.TariffResponse;
import com.complex.finAdvisor.dto.UpdatedTariffResponse;
import com.complex.finAdvisor.entity.TariffEntity;
import com.complex.finAdvisor.repository.TariffRepository;
import com.complex.finAdvisor.service.TariffService;
import com.complex.finAdvisor.util.TariffParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Tag(name = "Тарифы", description = "Контроллер для работы с тарифами")
@AllArgsConstructor
@RequestMapping("/api/tariff")
public class TariffController {
    private final TariffRepository tariffRepository;
    private final TariffService tariffService;
    private final TariffParser tariffParser;
    @GetMapping("/getAll")
    @Operation(summary = "Возвращает все сущестующие тарифы")
    public List<TariffEntity> getAllTariffs() {
        return tariffRepository.findAll();
    }

    @PutMapping("/updateByUser")
    @Operation(summary = "Обновляет тариф авторизированного пользователя. Указание тарифа в теле запроса")
    public ResponseEntity<TariffResponse> updateByUser(@RequestBody @Parameter(description = "Тело запроса для обновления тарифа. Описание тарифа") TariffRequest tariffRequest,
                                                       @Parameter(description = "Пользователь, прошедший успешно аутентификацию") Principal principal) {
        return ResponseEntity.ok(tariffService.updateTariffAuthUser(principal.getName(), tariffRequest));
    }

    @PostMapping("/getDifferenceDays")
    @Operation(summary = "Обновляет тариф авторизированного пользователя. Указание тарифа в теле запроса")
    public UpdatedTariffResponse getDifferenceDaysTariff(@RequestBody TariffRequest tariffRequest, Principal principal) {
        return tariffService.getDifferenceDaysTariff(tariffRequest, principal.getName());
    }
    @GetMapping("/fetchTariffs")
    @Operation(summary = "Запускает парсинг тарифов из json файла, создавая в БД схемы тариф " +
            "и отношение тариф-инструмент")
    public void fetchTariffs() {
        tariffParser.parseJson("src/main/resources/tariffs_instruments.json");
    }
}
