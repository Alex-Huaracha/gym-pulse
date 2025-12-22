package com.gympulse.api.services;

import com.gympulse.api.dtos.MembershipPlanRequestDTO;
import com.gympulse.api.dtos.MembershipPlanResponseDTO;

import java.util.List;

public interface MembershipPlanService {
    MembershipPlanResponseDTO createPlan(MembershipPlanRequestDTO requestDTO);
    List<MembershipPlanResponseDTO> getAllPlans();
    MembershipPlanResponseDTO getPlanById(Integer id);
    MembershipPlanResponseDTO updatePlan(Integer id, MembershipPlanRequestDTO requestDTO);
    void deletePlan(Integer id);
}
