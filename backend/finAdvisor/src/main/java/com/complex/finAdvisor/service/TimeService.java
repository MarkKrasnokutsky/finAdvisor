package com.complex.finAdvisor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class TimeService {
    public LocalDate getServerTime() {
        return ZonedDateTime.now(ZoneId.of("Europe/Moscow")).toLocalDate();
    }
    public LocalDateTime getExtendedServerTime() {
        return ZonedDateTime.now(ZoneId.of("Europe/Moscow")).toLocalDateTime();
    }
}
