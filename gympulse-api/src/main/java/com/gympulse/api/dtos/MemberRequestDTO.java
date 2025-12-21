package com.gympulse.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberRequestDTO {
    @NotBlank(message = "DNI is required")
    @Size(min = 8, max = 8, message = "DNI must be exactly 8 characters long")
    @Pattern(regexp = "\\d{8}", message = "DNI must contain only digits")
    private String dni;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;
    private String phone;
}
