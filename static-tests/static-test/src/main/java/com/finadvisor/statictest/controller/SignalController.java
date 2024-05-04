package com.finadvisor.statictest.controller;

import com.finadvisor.statictest.repository.SignalRepository;
import com.finadvisor.statictest.service.ServiceSignal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Сигналы", description = "Контроллер для работы с сигналами")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/signals")
public class SignalController {
    @Autowired
    private ServiceSignal serviceSignal;
    @Autowired
    private SignalRepository signalRepository;
    @GetMapping("/updateAll")
    @Operation(summary = "Обновляет все возможные сигналы по всем инструментам")
    ResponseEntity getSignals() {
        try {
            return ResponseEntity.ok(serviceSignal.fetchAllStockEvents());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
    @GetMapping("/get")
    @Operation(summary = "Возвращает все сигналы из БД")
    ResponseEntity findLastSignal() {
        try {
            return ResponseEntity.ok(signalRepository.findAll());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
