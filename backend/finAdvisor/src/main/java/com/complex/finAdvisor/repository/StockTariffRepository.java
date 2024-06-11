package com.complex.finAdvisor.repository;

import com.complex.finAdvisor.entity.InstrumentTariffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockTariffRepository extends JpaRepository<InstrumentTariffEntity, Long> {
    List<InstrumentTariffEntity> findByTariffId(Long tariffId);
}
