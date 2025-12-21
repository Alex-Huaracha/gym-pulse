package com.gympulse.api.mappers;

import com.gympulse.api.dtos.MemberRequestDTO;
import com.gympulse.api.dtos.MemberResponseDTO;
import com.gympulse.api.models.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    // Client -> Entity
    Member toEntity(MemberRequestDTO requestDTO);

    // Entity -> Client
    @Mapping(target = "fullName", expression = "java(member.getFirstName() + \" \" + member.getLastName())")
    MemberResponseDTO toResponseDTO(Member member);

    void updateEntityFromDto(MemberRequestDTO requestDTO, @MappingTarget Member member);
}
