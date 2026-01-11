package com.gympulse.api.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CheckInSummaryDTO {
    private Integer id;
    private String memberName;
    private LocalDateTime checkInTime;
}
