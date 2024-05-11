package com.complex.finAdvisor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
