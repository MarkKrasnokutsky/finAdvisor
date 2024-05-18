package com.complex.finAdvisor.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@NoArgsConstructor
@Tag(name = "Сущность роли", description = "Структура данных из базы о роли")
@AllArgsConstructor
@Data
@Builder
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id роли", example = "1")
    private Long id;
    @Schema(description = "Название роли", example = "admin")
    private String title;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Список пользователей с данной ролью", example = "[3,2,1,6]")
    private Set<UserEntity> users = new HashSet<>();
}
