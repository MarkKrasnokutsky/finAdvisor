package com.finadvisor.statictest.controller;

import com.finadvisor.statictest.service.ServiceStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class InstrumentController {
    @Autowired
    private ServiceStock serviceStock;
    @PostMapping("/fetchInstruments")
    ResponseEntity newStocks() {
        try {
            return ResponseEntity.ok(serviceStock.fetchStocks());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }


}
