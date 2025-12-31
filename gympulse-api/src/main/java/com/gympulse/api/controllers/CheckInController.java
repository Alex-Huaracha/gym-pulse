package com.gympulse.api.controllers;

import com.gympulse.api.dtos.CheckInRequestDTO;
import com.gympulse.api.dtos.CheckInResponseDTO;
import com.gympulse.api.services.CheckInService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/check-ins")
@RequiredArgsConstructor
public class CheckInController {
    private final CheckInService checkInService;

    @PostMapping
    public ResponseEntity<CheckInResponseDTO> registerCheckIn(@Valid @RequestBody CheckInRequestDTO requestDTO) {
        CheckInResponseDTO response = checkInService.registerCheckIn(requestDTO);
        // Devolvemos 200 OK siempre, porque el intento se procesó bien (aunque el acceso sea denegado)
        return ResponseEntity.ok(response);
    }
}