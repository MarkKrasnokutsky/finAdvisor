package com.complex.finAdvisor.controller;

import com.complex.finAdvisor.entity.StockSignalEntity;
import com.complex.finAdvisor.repository.SignalRepository;
import com.complex.finAdvisor.service.SignalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Сигналы", description = "Контроллер для работы с сигналами")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/signals")
public class SignalController {
    @Autowired
    private SignalService signalService;
    @Autowired
    private SignalRepository signalRepository;
    @PutMapping("/updateAll")
    @Operation(summary = "Обновляет все возможные сигналы по всем инструментам")
    List<StockSignalEntity> getSignals() {
        signalService.updateAllSignals();
        return this.signalRepository.findAll();
    }
    @GetMapping("/getAll")
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
