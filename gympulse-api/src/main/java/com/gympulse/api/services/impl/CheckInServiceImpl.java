package com.gympulse.api.services.impl;

import com.gympulse.api.dtos.CheckInRequestDTO;
import com.gympulse.api.dtos.CheckInResponseDTO;
import com.gympulse.api.models.CheckIn;
import com.gympulse.api.models.Member;
import com.gympulse.api.models.Membership;
import com.gympulse.api.repositories.CheckInRepository;
import com.gympulse.api.repositories.MemberRepository;
import com.gympulse.api.repositories.MembershipRepository;
import com.gympulse.api.services.CheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInServiceImpl implements CheckInService {
    private final CheckInRepository checkInRepository;
    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;

    @Override
    @Transactional
    public CheckInResponseDTO registerCheckIn(CheckInRequestDTO requestDTO) {
        Member member = memberRepository.findByDni(requestDTO.getDni())
                .orElseThrow(() -> new RuntimeException("Member not found with DNI: " + requestDTO.getDni()));

        Optional<Membership> activeMembership = membershipRepository.findByMemberIdAndIsPaidTrue(member.getId());

        boolean accessGranted;
        String message;

        if (activeMembership.isPresent()) {
            Membership membership = activeMembership.get();
            if (membership.getEndDate().isBefore(LocalDate.now())) {
               message = "Membership expired on: " + membership.getEndDate();
                    accessGranted = false;
                } else {
                    message = "Welcome, " + member.getFirstName();
                    accessGranted = true;
                }
            } else {
                message = "No active membership";
                accessGranted = false;
        }

        CheckIn checkIn = new CheckIn();
        checkIn.setMember(member);
        checkIn.setIsValid(accessGranted);

        CheckIn savedCheckIn = checkInRepository.save(checkIn);

        CheckInResponseDTO response = new CheckInResponseDTO();
        response.setId(savedCheckIn.getId());
        response.setMemberName(member.getFirstName() + " " + member.getLastName());

        response.setTime(savedCheckIn.getCheckInTime());

        response.setAccessGranted(savedCheckIn.getIsValid());
        response.setMessage(message);

        return response;
    }
}