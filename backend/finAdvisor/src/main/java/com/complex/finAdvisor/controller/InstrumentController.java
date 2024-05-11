package com.complex.finAdvisor.controller;

import com.complex.finAdvisor.repository.StockRepository;
import com.complex.finAdvisor.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/instruments")
public class InstrumentController {
    @Autowired
    private StockService stockService;
    @Autowired
    private StockRepository stockRepository;
    @PutMapping("/fetch")
    ResponseEntity newStocks() {
        try {
            return ResponseEntity.ok(stockService.fetchStocks());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
    @GetMapping("/getAll")
    ResponseEntity getStocks() {
        try {
            return ResponseEntity.ok(stockRepository.findAll());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

}
