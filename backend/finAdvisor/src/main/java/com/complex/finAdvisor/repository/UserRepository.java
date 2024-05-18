package com.complex.finAdvisor.repository;

import com.complex.finAdvisor.entity.TariffEntity;
import com.complex.finAdvisor.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String login);
    Boolean existsByUsername(String login);
    Boolean existsByEmail(String email);

    UserEntity getUserById(Long id);
    @Query("SELECT u.tariff FROM UserEntity u WHERE u.id = ?1")
    TariffEntity findTariffByUserId(Long userId);
}
