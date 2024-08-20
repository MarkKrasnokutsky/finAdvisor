package com.complex.finAdvisor.repository;

import com.complex.finAdvisor.entity.StockSignalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SignalRepository extends JpaRepository<StockSignalEntity, Long> {
    List<StockSignalEntity> findBySecid(String secid);
    Optional<StockSignalEntity> findById(Long signalId);
}
