package com.complex.finAdvisor.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping("/test")
    String test(@RequestBody String nameUser) {
        return "Welcome, " + nameUser;
    }
}
