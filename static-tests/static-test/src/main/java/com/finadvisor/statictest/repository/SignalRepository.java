package com.finadvisor.statictest.repository;

import com.finadvisor.statictest.domain.entity.StockSignalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignalRepository extends JpaRepository<StockSignalEntity, Long> {
    StockSignalEntity findBySecid(String secid);
}
