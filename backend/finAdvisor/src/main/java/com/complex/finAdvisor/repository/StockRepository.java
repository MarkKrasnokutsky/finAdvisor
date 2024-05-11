package com.complex.finAdvisor.repository;

import com.complex.finAdvisor.entity.InstrumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<InstrumentEntity, Long> {
}

