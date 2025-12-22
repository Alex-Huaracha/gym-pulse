package com.gympulse.api.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MembershipPlanRequestDTO {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 50, message = "Name can have at most 50 characters")
    private String name;

    @NotNull(message = "Duration in days is mandatory")
    @Min(value = 1, message = "Duration must be at least 1 day")
    private Integer durationDays;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;
}
