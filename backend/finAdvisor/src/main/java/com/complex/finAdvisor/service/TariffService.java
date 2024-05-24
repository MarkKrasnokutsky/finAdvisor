package com.complex.finAdvisor.service;

import com.complex.finAdvisor.dto.TariffRequest;
import com.complex.finAdvisor.entity.TariffEntity;
import com.complex.finAdvisor.entity.UserEntity;
import com.complex.finAdvisor.repository.TariffRepository;
import com.complex.finAdvisor.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TariffService {
    private final TariffRepository tariffRepository;
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

    public void updateTariffAuthUser(String username, TariffRequest tariffRequest) {
        Optional<UserEntity> currentUser = userRepository.findByUsername(username);
        currentUser.ifPresent(userEntity -> {
            Optional<TariffEntity> currentTariff = tariffRepository.findByName(tariffRequest.getName());
            currentTariff.ifPresent(userEntity::setTariff);
            userEntity.setTariffExpiration(LocalDateTime.parse(tariffRequest.getSaleDate()).plusDays(tariffRequest.getDuration()));
            userRepository.save(userEntity);
        });
    }

    private boolean isTariffExpired(LocalDateTime tariffExpiration) {
        return ZonedDateTime.now(ZoneId.of("Europe/Moscow")).toLocalDateTime().isAfter(tariffExpiration);
    }
}
