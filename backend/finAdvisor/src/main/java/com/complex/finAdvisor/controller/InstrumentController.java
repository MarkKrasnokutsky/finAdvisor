package com.complex.finAdvisor.controller;

import com.complex.finAdvisor.repository.StockRepository;
import com.complex.finAdvisor.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@Tag(name = "Инструменты", description = "Контроллер для работы с инструментами")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/instruments")
public class InstrumentController {
    @Autowired
    private StockService stockService;
    @Autowired
    private StockRepository stockRepository;
    @PutMapping("/fetch")
    @Operation(summary = "Обновляет все возможные инструменты с API МосБиржи")
    ResponseEntity<?> newStocks() {
        try {
            return ResponseEntity.ok(stockService.fetchStocks());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
    @GetMapping("/getAll")
    @Operation(summary = "Возвращает все инструменты с БД")
    ResponseEntity<?> getStocks() {
        try {
            return ResponseEntity.ok(stockRepository.findAll());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
    @GetMapping("/getByUserTariff")
    @Operation(summary = "Возвращает все инструменты по тарифу пользователя")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    ResponseEntity<?> getByUserTariff(Principal principal) {
        try {
            return ResponseEntity.ok(stockService.getInstrumentsByUser(principal.getName()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

}
