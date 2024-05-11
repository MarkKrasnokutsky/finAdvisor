package com.complex.finAdvisor.repository;

import com.complex.finAdvisor.entity.StockSignalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignalRepository extends JpaRepository<StockSignalEntity, Long> {
    StockSignalEntity findBySecid(String secid);
}
