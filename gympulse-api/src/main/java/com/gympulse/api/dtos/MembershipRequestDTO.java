package com.gympulse.api.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MembershipRequestDTO {
    @NotNull(message = "Member ID is required")
    private Integer memberId;

    @NotNull(message = "Plan ID is required")
    private Integer planId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;
}
