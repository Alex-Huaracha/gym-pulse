package com.gympulse.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSummaryDTO {
    private Integer id;
    private String fullName;
    private String dni;
    private String status;
    private String currentPlan;
    private LocalDate endDate;
    private String daysRemaining;
}
