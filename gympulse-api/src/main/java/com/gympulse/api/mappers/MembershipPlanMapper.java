package com.gympulse.api.mappers;

import com.gympulse.api.dtos.MembershipPlanRequestDTO;
import com.gympulse.api.dtos.MembershipPlanResponseDTO;
import com.gympulse.api.models.MembershipPlan;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MembershipPlanMapper {
    MembershipPlan toEntity(MembershipPlanRequestDTO membershipPlan);
    MembershipPlanResponseDTO toResponseDTO(MembershipPlan plan);
    void updateEntityFromDto(MembershipPlanRequestDTO dto, @MappingTarget MembershipPlan plan);
}
