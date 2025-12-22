package com.gympulse.api.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MembershipPlanResponseDTO {
    private Integer id;
    private String name;
    private Integer durationDays;
    private BigDecimal price;
}
