package com.gympulse.api.dtos;

import lombok.Data;

@Data
public class MemberResponseDTO {
    private Integer id;
    private String dni;
    private String fullName;
    private String phone;
    private String status;
}
