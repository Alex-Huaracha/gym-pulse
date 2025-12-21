package com.gympulse.api.services;

import com.gympulse.api.dtos.MemberRequestDTO;
import com.gympulse.api.dtos.MemberResponseDTO;

import java.util.List;

public interface MemberService {
    MemberResponseDTO createMember(MemberRequestDTO requestDTO);
    List<MemberResponseDTO> getAllMembers();
    MemberResponseDTO getMemberById(Integer id);
    MemberResponseDTO updateMember(Integer id, MemberRequestDTO requestDTO);
    void deleteMember(Integer id);
}
