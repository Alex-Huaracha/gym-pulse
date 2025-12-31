package com.gympulse.api.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CheckInRequestDTO {
    @NotBlank(message = "DNI must not be blank")
    private String dni;
}
