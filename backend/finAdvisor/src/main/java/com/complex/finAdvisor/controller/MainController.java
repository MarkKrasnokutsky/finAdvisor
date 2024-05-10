package com.complex.finAdvisor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/secured")
public class MainController {
    @RequestMapping("/user")
    public String userAccess(Principal principal) {
        if (principal == null){
            return null;
        }
        return principal.getName();
    }
}
