package com.gympulse.api.services;

import com.gympulse.api.dtos.MembershipRequestDTO;
import com.gympulse.api.dtos.MembershipResponseDTO;

import java.util.List;

public interface MembershipService {
    MembershipResponseDTO createMembership(MembershipRequestDTO requestDTO);
    List<MembershipResponseDTO> getMemberHistory(Integer memberId);
    MembershipResponseDTO getMembershipById(Integer id);
    void deleteMembership(Integer id);
}
