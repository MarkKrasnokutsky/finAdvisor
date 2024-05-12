package com.complex.finAdvisor.controller;

import com.complex.finAdvisor.entity.TariffEntity;
import com.complex.finAdvisor.entity.UserEntity;
import com.complex.finAdvisor.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    @GetMapping("/get/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userRepository.getUserById(id);
    }
    @GetMapping("/getAll")
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
    @GetMapping("/getTariff/{id}")
    public TariffEntity getTariffByUserId(@PathVariable Long id) {
        return userRepository.findTariffByUserId(id);
    }
}
