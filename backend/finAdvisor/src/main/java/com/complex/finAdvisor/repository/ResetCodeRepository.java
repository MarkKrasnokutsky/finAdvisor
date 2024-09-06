package com.complex.finAdvisor.repository;

import com.complex.finAdvisor.entity.ResetCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetCodeRepository extends JpaRepository<ResetCodeEntity, Long> {
    Optional<ResetCodeEntity> findByEmail(String email);
    ResetCodeEntity findByCode(String code);
}
