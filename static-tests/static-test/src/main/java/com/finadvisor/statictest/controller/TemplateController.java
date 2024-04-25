package com.finadvisor.statictest.controller;

import com.finadvisor.statictest.entity.Stock;
import com.finadvisor.statictest.service.ServiceSignalsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TemplateController {
    @Autowired
    private ServiceSignalsTemplate serviceSignalsTemplate;
    @GetMapping(value = {"/stockSignal/{secid}/{limit}/{start}"})
    public String stockData(Model model, @PathVariable String secid, @PathVariable int limit, @PathVariable int start) {
        ArrayList<Stock> stockData = serviceSignalsTemplate.fetchStockEvents(secid, limit, start);
        ArrayList<Stock> lastSignalData = serviceSignalsTemplate.findLastSignal(stockData);
        List<Stock> subListFractal = lastSignalData.subList(0, 5);
        Stock breakdown = lastSignalData.get(5);
        Stock signal = lastSignalData.get(6);
        Stock longPoint = lastSignalData.get(7);
        model.addAttribute("stockData", stockData);
        model.addAttribute("subListFractal", subListFractal);
        model.addAttribute("breakdown", breakdown);
        model.addAttribute("signal", signal);
        model.addAttribute("longPoint", longPoint);
        return "index";
    }
}
