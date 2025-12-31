package com.gympulse.api.services.impl;

import com.gympulse.api.dtos.MemberRequestDTO;
import com.gympulse.api.dtos.MemberResponseDTO;
import com.gympulse.api.mappers.MemberMapper;
import com.gympulse.api.models.Member;
import com.gympulse.api.repositories.MemberRepository;
import com.gympulse.api.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    @Transactional
    public MemberResponseDTO createMember(MemberRequestDTO requestDTO) {
        if (memberRepository.findByDni(requestDTO.getDni()).isPresent()) {
            throw new RuntimeException("Member with DNI " + requestDTO.getDni() + " already exists.");
        }

        Member member = memberMapper.toEntity(requestDTO);

        Member savedMember = memberRepository.save(member);

        return memberMapper.toResponseDTO(savedMember);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberResponseDTO> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(memberMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponseDTO getMemberById(Integer id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member with ID " + id + " not found."));
        return memberMapper.toResponseDTO(member);
    }

    @Override
    @Transactional
    public MemberResponseDTO updateMember(Integer id, MemberRequestDTO requestDTO) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member with ID " + id + " not found."));

        memberMapper.updateEntityFromDto(requestDTO, member);

        memberRepository.save(member);

        return memberMapper.toResponseDTO(member);
    }

    @Override
    @Transactional
    public void deleteMember(Integer id) {
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("Member with ID " + id + " not found.");
        }
        memberRepository.deleteById(id);
    }

}
