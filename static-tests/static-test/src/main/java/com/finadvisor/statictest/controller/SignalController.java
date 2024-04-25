package com.finadvisor.statictest.controller;

import com.finadvisor.statictest.repository.SignalRepository;
import com.finadvisor.statictest.service.ServiceSignal;
import com.finadvisor.statictest.service.ServiceStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class SignalController {
    @Autowired
    private ServiceSignal serviceSignal;
    @Autowired
    private SignalRepository signalRepository;
    @GetMapping("/fetchSignals")
    ResponseEntity getSignals() {
        try {
            return ResponseEntity.ok(serviceSignal.fetchAllStockEvents());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
    @GetMapping("/signals_get")
    ResponseEntity findLastSignal() {
        try {
            return ResponseEntity.ok(signalRepository.findAll());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
