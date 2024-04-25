package com.finadvisor.statictest.repository;

import com.finadvisor.statictest.model.StockSignal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignalRepository extends JpaRepository<StockSignal, Long> {
}
