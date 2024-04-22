package com.finadvisor.statictest.controller;

import com.finadvisor.statictest.entity.Stock;
import com.finadvisor.statictest.service.ServiceStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Controller
public class TemplateController {
    @Autowired
    private ServiceStock serviceStock;
    @GetMapping(value = {"/stock/{secid}/{limit}/{start}"})
    public String stockData(Model model, @PathVariable String secid, @PathVariable int limit, @PathVariable int start) {
        ArrayList<Stock> stockData = serviceStock.fetchStocks(secid, limit, start);
        ArrayList<Stock> lastFractalData = serviceStock.findLastSignal(stockData);
        model.addAttribute("stockData", stockData);
        model.addAttribute("lastSignalData", lastFractalData);
        return "index";
    }
}
