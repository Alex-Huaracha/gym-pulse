package com.gympulse.api.services.impl;

import com.gympulse.api.dtos.DashboardChartsDTO;
import com.gympulse.api.dtos.DashboardStatsDTO;
import com.gympulse.api.dtos.MemberSummaryDTO;
import com.gympulse.api.models.Member;
import com.gympulse.api.models.Membership;
import com.gympulse.api.repositories.CheckInRepository;
import com.gympulse.api.repositories.MemberRepository;
import com.gympulse.api.repositories.MembershipRepository;
import com.gympulse.api.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;
    private final CheckInRepository checkInRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MemberSummaryDTO> getMembersSummary() {
        List<Member> allMembers = memberRepository.findAll();
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
                dto.setDaysRemaining(days);
            } else {
                dto.setCurrentPlan("Sin Plan Activo");
                dto.setEndDate(null);
                dto.setDaysRemaining(null);
            }
            summaryList.add(dto);
        }

        summaryList.sort((m1, m2) -> {
            Long d1 = m1.getDaysRemaining();
            Long d2 = m2.getDaysRemaining();

            if (d1 == null && d2 == null) return m1.getFullName().compareTo(m2.getFullName());
            if (d1 == null) return 1;
            if (d2 == null) return -1;

            int comparison = Long.compare(d1, d2);
            return comparison != 0 ? comparison : m1.getFullName().compareTo(m2.getFullName());
        });

        return summaryList;
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardStatsDTO getStats() {
        Long activeMembers = memberRepository.countActiveMembers();
        Long checkInsToday = checkInRepository.countCheckInsToday();
        BigDecimal revenue = membershipRepository.sumMonthlyRevenue();

        return DashboardStatsDTO.builder()
                .totalActiveMembers(activeMembers)
                .checkInsToday(checkInsToday)
                .monthlyRevenue(revenue)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardChartsDTO getChartsData() {
        return DashboardChartsDTO.builder()
                .hourlyInflow(getHourlyStats())
                .weeklyInflow(getWeeklyStats())
                .build();
    }

    @Override
    public List<MemberSummaryDTO> getMembershipAlerts() {
        List<MemberSummaryDTO> allMembers = this.getMembersSummary();
        return allMembers.stream()
                .filter(m -> m.getDaysRemaining() != null && m.getDaysRemaining() <= 5)
                .limit(5)
                .collect(Collectors.toList());
    }

    private Map<Integer, Long> getHourlyStats() {
        // 1. Inicializar mapa con 0s para las 24 horas (0 a 23)
        Map<Integer, Long> stats = new LinkedHashMap<>();
        for (int i = 0; i < 24; i++) {
            stats.put(i, 0L);
        }

        // 2. Obtener datos reales de la BD
        List<Object[]> results = checkInRepository.countCheckInsByHourToday();

        // 3. Llenar el mapa (sobrescribir los 0s donde haya datos)
        for (Object[] row : results) {
            // Postgres devuelve Double o Integer dependiendo de la versión, casteamos con cuidado
            int hour = ((Number) row[0]).intValue();
            long count = ((Number) row[1]).longValue();
            stats.put(hour, count);
        }
        return stats;
    }

    private Map<String, Long> getWeeklyStats() {
        // 1. Inicializar mapa ordenado (Lunes a Domingo)
        Map<String, Long> stats = new LinkedHashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            stats.put(day.name(), 0L);
        }

        // 2. Obtener datos de la BD
        List<Object[]> results = checkInRepository.countCheckInsByDayOfWeek();

        // 3. Llenar
        for (Object[] row : results) {
            int dayIndex = ((Number) row[0]).intValue(); // 1=Lunes, 7=Domingo (ISODOW)
            long count = ((Number) row[1]).longValue();

            // Convertimos el índice 1..7 a DayOfWeek enum
            DayOfWeek day = DayOfWeek.of(dayIndex);
            stats.put(day.name(), count);
        }
        return stats;
    }
}