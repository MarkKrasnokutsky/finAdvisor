package com.complex.finAdvisor.repository;

import com.complex.finAdvisor.entity.TariffEntity;
import com.complex.finAdvisor.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TariffRepository extends JpaRepository<TariffEntity, Long> {
    Optional<TariffEntity> findByName(String name);
}
