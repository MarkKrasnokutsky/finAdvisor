package com.complex.finAdvisor.service;

import com.complex.finAdvisor.dto.TariffRequest;
import com.complex.finAdvisor.dto.UpdatedTariffResponse;
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
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
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
                user.setTariffDuration(null);
                user.setTariffInception(null);
                user.setTariffExpiration(null);
                userRepository.save(user);
            }
        }
    }

    public void updateTariffAuthUser(String username, TariffRequest tariffRequest) {
        Optional<UserEntity> currentUser = userRepository.findByUsername(username);
        currentUser.ifPresent(userEntity -> {
            Optional<TariffEntity> currentTariff = tariffRepository.findByName(tariffRequest.getName());
            currentTariff.ifPresent(userEntity::setTariff);
            userEntity.setTariffDuration(tariffRequest.getDuration());
            ZonedDateTime moscowTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
            userEntity.setTariffInception(moscowTime.toLocalDateTime());
            userEntity.setTariffExpiration(moscowTime.toLocalDateTime().plusDays(tariffRequest.getDuration()));
            userRepository.save(userEntity);
        });
    }

    public UpdatedTariffResponse getDifferenceDaysTariff(TariffRequest tariffRequest, String username) {
        UpdatedTariffResponse response = new UpdatedTariffResponse();
        Optional<UserEntity> currentUser = userRepository.findByUsername(username);
        currentUser.ifPresent(userEntity -> {
            ZonedDateTime moscowTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
            // Вычисление разницы в днях
            long daysDifference = ChronoUnit.DAYS.between(moscowTime.toLocalDate(),
                    userEntity.getTariffExpiration().toLocalDate());

            // Если текущая дата позже даты истечения тарифа, устанавливаем разницу в 0
            if (daysDifference < 0 || !Objects.equals(tariffRequest.getName(), userEntity.getTariff().getName())) {
                daysDifference = 0;
            }
            response.setUsername(userEntity.getUsername());
            response.setDayDifference(daysDifference);
            response.setTariffName(tariffRequest.getName());
        });
        return response;
    }

    private boolean isTariffExpired(LocalDateTime tariffExpiration) {
        return ZonedDateTime.now(ZoneId.of("Europe/Moscow")).toLocalDateTime().isAfter(tariffExpiration);
    }
}
