package com.complex.finAdvisor.dto;

import com.complex.finAdvisor.entity.TariffEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String username;
    private TariffEntity tariff;
    private String email;
}
