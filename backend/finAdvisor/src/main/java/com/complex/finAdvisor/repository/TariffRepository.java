package com.complex.finAdvisor.repository;

import com.complex.finAdvisor.entity.TariffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepository extends JpaRepository<TariffEntity, Long> {
}
