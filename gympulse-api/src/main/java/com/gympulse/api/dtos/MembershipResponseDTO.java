package com.gympulse.api.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MembershipResponseDTO {
    private Integer id;
    private String memberName;
    private String planName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isPaid;
    private String status;
}
