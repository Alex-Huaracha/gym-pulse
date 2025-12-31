package com.gympulse.api.services.impl;

import com.gympulse.api.dtos.MembershipRequestDTO;
import com.gympulse.api.dtos.MembershipResponseDTO;
import com.gympulse.api.mappers.MembershipMapper;
import com.gympulse.api.models.Member;
import com.gympulse.api.models.Membership;
import com.gympulse.api.models.MembershipPlan;
import com.gympulse.api.repositories.MemberRepository;
import com.gympulse.api.repositories.MembershipPlanRepository;
import com.gympulse.api.repositories.MembershipRepository;
import com.gympulse.api.services.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;
    private final MemberRepository memberRepository;
    private final MembershipPlanRepository planRepository;
    private final MembershipMapper membershipMapper;

    @Override
    @Transactional
    public MembershipResponseDTO createMembership(MembershipRequestDTO dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        MembershipPlan plan = planRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        Membership membership = new Membership();
        membership.setMember(member);
        membership.setPlan(plan);
        membership.setStartDate(dto.getStartDate());

        LocalDate endDate = dto.getStartDate().plusDays(plan.getDurationDays());
        membership.setEndDate(endDate);
        membership.setIsPaid(true);

        member.setStatus("ACTIVE");
        memberRepository.save(member);

        return membershipMapper.toResponseDTO(membershipRepository.save(membership));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MembershipResponseDTO> getMemberHistory(Integer memberId) {
        // Validamos que el socio exista antes de buscar
        if (!memberRepository.existsById(memberId)) {
            throw new RuntimeException("Member not found");
        }

        return membershipRepository.findByMemberId(memberId).stream()
                .map(membershipMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MembershipResponseDTO getMembershipById(Integer id) {
        return membershipRepository.findById(id)
                .map(membershipMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Membership not found with ID: " + id));
    }

    @Override
    @Transactional
    public void deleteMembership(Integer id) {
        if (!membershipRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete. Membership not found.");
        }
        membershipRepository.deleteById(id);
    }
}