package com.complex.finAdvisor.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/user")
public class UserController {
    @PostMapping("/test")
    String test(@RequestBody String nameUser) {
        return "Welcome, " + nameUser;
    }
}
