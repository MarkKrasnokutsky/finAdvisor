package com.complex.finAdvisor.controller;

import com.complex.finAdvisor.dto.TariffRequest;
import com.complex.finAdvisor.entity.TariffEntity;
import com.complex.finAdvisor.entity.UserEntity;
import com.complex.finAdvisor.repository.TariffRepository;
import com.complex.finAdvisor.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/tariff")
public class TariffController {
    private final TariffRepository tariffRepository;
    private final UserRepository userRepository;
    @GetMapping("/getAll")
    public List<TariffEntity> getAllTariffs() {
        return tariffRepository.findAll();
    }

    @PutMapping("/updateByUser")
    public ResponseEntity<?> updateByUser(@RequestBody TariffRequest tariffRequest, Principal principal) {
        try {
            Optional<UserEntity> currentUser = userRepository.findByUsername(principal.getName());
            currentUser.ifPresent(userEntity -> {
                Optional<TariffEntity> currentTariff = tariffRepository.findByName(tariffRequest.getName());
                currentTariff.ifPresent(userEntity::setTariff);
                userEntity.setTariffExpiration(LocalDateTime.parse(tariffRequest.getSaleDate()).plusDays(tariffRequest.getDuration()));
                userRepository.save(userEntity);
            });
            return ResponseEntity.ok("Updated tariff to " + tariffRequest.getName() + "for user - " + principal.getName());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
