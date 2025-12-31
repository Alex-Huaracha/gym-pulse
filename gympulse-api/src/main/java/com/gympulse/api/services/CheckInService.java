package com.gympulse.api.services;

import com.gympulse.api.dtos.CheckInRequestDTO;
import com.gympulse.api.dtos.CheckInResponseDTO;

public interface CheckInService {
    CheckInResponseDTO registerCheckIn(CheckInRequestDTO requestDTO);
}
