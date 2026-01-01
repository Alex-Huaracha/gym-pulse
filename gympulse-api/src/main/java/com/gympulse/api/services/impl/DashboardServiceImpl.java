package com.gympulse.api.services.impl;

import com.gympulse.api.dtos.MemberSummaryDTO;
import com.gympulse.api.models.Member;
import com.gympulse.api.models.Membership;
import com.gympulse.api.repositories.MemberRepository;
import com.gympulse.api.repositories.MembershipRepository;
import com.gympulse.api.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MemberSummaryDTO> getMembersSummary() {
        List<Member> allMembers = memberRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<Membership> activeMemberships = membershipRepository.findAllActiveMemberships();

        Map<Integer, Membership> membershipMap = activeMemberships.stream()
                .collect(Collectors.toMap(m -> m.getMember().getId(), m -> m));

        List<MemberSummaryDTO> summaryList = new ArrayList<>();

        for (Member member : allMembers) {
            Membership activeMembership = membershipMap.get(member.getId());

            MemberSummaryDTO dto = new MemberSummaryDTO();
            dto.setId(member.getId());
            dto.setFullName(member.getFirstName() + " " + member.getLastName());
            dto.setDni(member.getDni());
            dto.setStatus(member.getStatus());

            if (activeMembership != null) {
                dto.setCurrentPlan(activeMembership.getPlan().getName());
                dto.setEndDate(activeMembership.getEndDate());

                long days = ChronoUnit.DAYS.between(LocalDate.now(), activeMembership.getEndDate());
                dto.setDaysRemaining(days + " days");
            } else {
                dto.setCurrentPlan("No Active Plan");
                dto.setEndDate(null);
                dto.setDaysRemaining("-");
            }

            summaryList.add(dto);
        }

        return summaryList;
    }
}