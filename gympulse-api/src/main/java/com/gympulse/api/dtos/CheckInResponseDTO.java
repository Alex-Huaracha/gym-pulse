package com.gympulse.api.dtos;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class CheckInResponseDTO {
    private Integer id;
    private String memberName;
    private OffsetDateTime time;
    private Boolean accessGranted;
    private String message;
}
