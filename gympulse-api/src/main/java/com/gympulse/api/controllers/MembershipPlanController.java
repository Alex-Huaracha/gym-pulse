package com.gympulse.api.controllers;

import com.gympulse.api.dtos.MembershipPlanRequestDTO;
import com.gympulse.api.dtos.MembershipPlanResponseDTO;
import com.gympulse.api.services.MembershipPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plans")
@RequiredArgsConstructor
public class MembershipPlanController {
    private final MembershipPlanService planService;

    @PostMapping
    public ResponseEntity<MembershipPlanResponseDTO> createPlan(@Valid @RequestBody MembershipPlanRequestDTO requestDTO) {
        return new ResponseEntity<>(planService.createPlan(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MembershipPlanResponseDTO>> getAllPlans() {
        return ResponseEntity.ok(planService.getAllPlans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembershipPlanResponseDTO> getPlanById(@PathVariable Integer id) {
        return ResponseEntity.ok(planService.getPlanById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MembershipPlanResponseDTO> updatePlan(@PathVariable Integer id, @Valid @RequestBody MembershipPlanRequestDTO requestDTO) {
        return ResponseEntity.ok(planService.updatePlan(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Integer id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }
}