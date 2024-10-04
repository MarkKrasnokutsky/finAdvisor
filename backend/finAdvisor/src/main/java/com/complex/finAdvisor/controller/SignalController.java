package com.complex.finAdvisor.controller;

import com.complex.finAdvisor.entity.StockSignalEntity;
import com.complex.finAdvisor.repository.SignalRepository;
import com.complex.finAdvisor.service.SignalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Tag(name = "Сигналы", description = "Контроллер для работы с сигналами")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/signals")
@RequiredArgsConstructor
public class SignalController {
    private final SignalService signalService;
    private final SignalRepository signalRepository;
    @PutMapping("/updateAll")
    @Operation(summary = "Обновляет все возможные сигналы по всем инструментам")
    List<StockSignalEntity> getSignals() {
        signalService.updateAllSignals();
        return this.signalRepository.findAll();
    }
    @GetMapping("/getAll")
    @Operation(summary = "Возвращает все сигналы из БД")
    ResponseEntity<?> getAllSignals() {
        try {
            return ResponseEntity.ok(signalRepository.findAll());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
    @GetMapping("/getSignalsOnThisDayByUser")
    @Operation(summary = "Возвращает все сигналы по тарифу пользователя текущего дня")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    ResponseEntity<?> getSignalsOfThisDayByUser(Principal principal) {
        try {
            return ResponseEntity.ok(signalService.getSignalsOfThisDay(principal.getName()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
    @GetMapping("/getSignalsOnThisDay")
    @Operation(summary = "Возвращает все сигналы по тарифу пользователя текущего дня")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    ResponseEntity<?> getSignalsOfThisDay() {
        try {
            return ResponseEntity.ok(signalService.getSignalsOfThisDay());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
    @GetMapping("/getByUserTariff")
    @Operation(summary = "Возвращает все сигналы по тарифу пользователя")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    ResponseEntity<?> getByUserTariff(Principal principal) {
        try {
            return ResponseEntity.ok(signalService.getSignalsByUser(principal.getName()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PutMapping("/updateSignalsHistory")
    @Operation(summary = "Обновляет историю сигналов за всё время")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    ResponseEntity<?> updateAllSignalsInHistory() {
        try {
            signalService.saveSignalsOfThisDayInHistory();
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
