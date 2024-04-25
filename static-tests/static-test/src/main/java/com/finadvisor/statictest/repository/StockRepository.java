package com.finadvisor.statictest.repository;

import com.finadvisor.statictest.model.Instrument;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface StockRepository extends JpaRepository<Instrument, Long> {
}

