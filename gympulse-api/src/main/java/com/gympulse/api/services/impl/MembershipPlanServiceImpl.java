package com.gympulse.api.services.impl;

import com.gympulse.api.dtos.MembershipPlanRequestDTO;
import com.gympulse.api.dtos.MembershipPlanResponseDTO;
import com.gympulse.api.mappers.MembershipPlanMapper;
import com.gympulse.api.models.MembershipPlan;
import com.gympulse.api.repositories.MembershipPlanRepository;
import com.gympulse.api.services.MembershipPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MembershipPlanServiceImpl implements MembershipPlanService {
    private final MembershipPlanRepository planRepository;
    private final MembershipPlanMapper planMapper;

    @Override
    @Transactional
    public MembershipPlanResponseDTO createPlan(MembershipPlanRequestDTO requestDTO) {
        if (planRepository.existsByName(requestDTO.getName())) {
            throw new IllegalArgumentException("Membership plan with the same name already exists.");
        }
        MembershipPlan planEntity = planMapper.toEntity(requestDTO);
        return planMapper.toResponseDTO(planRepository.save(planEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MembershipPlanResponseDTO> getAllPlans() {
        return planRepository.findAll().stream()
                .map(planMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MembershipPlanResponseDTO getPlanById(Integer id) {
        MembershipPlan planEntity = planRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Membership plan not found with id: " + id));
        return planMapper.toResponseDTO(planEntity);
    }

    @Override
    @Transactional
    public MembershipPlanResponseDTO updatePlan(Integer id, MembershipPlanRequestDTO requestDTO) {
        MembershipPlan planEntity = planRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Membership plan not found with id: " + id));
        planMapper.updateEntityFromDto(requestDTO, planEntity);
        return planMapper.toResponseDTO(planRepository.save(planEntity));
    }

    @Override
    @Transactional
    public void deletePlan(Integer id) {
        if (!planRepository.existsById(id)) {
            throw new RuntimeException("Membership plan not found with id: " + id);
        }
        planRepository.deleteById(id);
    }
}