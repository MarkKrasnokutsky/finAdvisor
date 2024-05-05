package com.finadvisor.statictest.repository;

import com.finadvisor.statictest.domain.entity.InstrumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<InstrumentEntity, Long> {
}

