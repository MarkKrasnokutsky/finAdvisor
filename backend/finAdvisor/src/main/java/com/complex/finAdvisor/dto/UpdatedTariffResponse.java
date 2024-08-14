package com.complex.finAdvisor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedTariffResponse {
    private String username;
    private String tariffName;
    private Long dayDifference;
}
