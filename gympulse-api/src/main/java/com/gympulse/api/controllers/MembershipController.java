package com.gympulse.api.controllers;

import com.gympulse.api.dtos.MembershipRequestDTO;
import com.gympulse.api.dtos.MembershipResponseDTO;
import com.gympulse.api.services.MembershipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/memberships")
@RequiredArgsConstructor
public class MembershipController {
    private final MembershipService membershipService;

    @PostMapping
    public ResponseEntity<MembershipResponseDTO> createMembership(@Valid @RequestBody MembershipRequestDTO requestDTO) {
        MembershipResponseDTO response = membershipService.createMembership(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<MembershipResponseDTO>> getMemberHistory(@PathVariable Integer memberId) {
        List<MembershipResponseDTO> history = membershipService.getMemberHistory(memberId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembershipResponseDTO> getMembershipById(@PathVariable Integer id) {
        return ResponseEntity.ok(membershipService.getMembershipById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMembership(@PathVariable Integer id) {
        membershipService.deleteMembership(id);
        return ResponseEntity.noContent().build();
    }
}
