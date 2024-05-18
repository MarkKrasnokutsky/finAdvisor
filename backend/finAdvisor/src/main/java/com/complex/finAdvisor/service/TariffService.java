package com.complex.finAdvisor.service;

import com.complex.finAdvisor.entity.UserEntity;
import com.complex.finAdvisor.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TariffService {
    private final UserRepository userRepository;
    @Scheduled(cron = "0 0 0 * * ?") // Запуск задачи каждый день в полночь
    public void resetExpiredTariffs() {
        List<UserEntity> users = userRepository.findAll();
        for (UserEntity user : users) {
            if (user.getTariff() != null && isTariffExpired(user.getTariffExpiration())) {
                user.setTariff(null);
                userRepository.save(user);
            }
        }
    }

    private boolean isTariffExpired(LocalDateTime tariffExpiration) {
        return ZonedDateTime.now(ZoneId.of("Europe/Moscow")).toLocalDateTime().isAfter(tariffExpiration);
    }
}
