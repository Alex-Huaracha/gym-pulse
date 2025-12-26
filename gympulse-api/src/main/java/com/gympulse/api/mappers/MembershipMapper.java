package com.gympulse.api.mappers;

import com.gympulse.api.dtos.MembershipResponseDTO;
import com.gympulse.api.models.Membership;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MembershipMapper {

    @Mapping(target = "memberName", expression = "java(m.getMember().getFirstName() + \" \" + m.getMember().getLastName())")
    @Mapping(target = "planName", source = "plan.name")
    @Mapping(target = "status", expression = "java(java.time.LocalDate.now().isAfter(m.getEndDate()) ? \"Expired\" : \"Active\")")
    MembershipResponseDTO toResponseDTO(Membership m);
}
