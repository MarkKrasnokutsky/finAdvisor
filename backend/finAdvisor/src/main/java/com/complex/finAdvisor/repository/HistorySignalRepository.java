package com.complex.finAdvisor.repository;

import com.complex.finAdvisor.entity.HistorySignalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistorySignalRepository extends JpaRepository<HistorySignalEntity, Long> {
    List<HistorySignalEntity> findBySecid(String secid);
    Optional<HistorySignalEntity> findById(Long signalId);
}
