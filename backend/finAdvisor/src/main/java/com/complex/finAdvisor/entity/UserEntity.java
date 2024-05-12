package com.complex.finAdvisor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "tg_nickname", unique = true)
    private String tgNickname;

    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private TariffEntity tariff;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @Column(name = "tariff_expiration")
    private LocalDateTime tariffExpiration;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
