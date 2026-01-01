package com.gympulse.api.services;

import com.gympulse.api.dtos.MemberSummaryDTO;

import java.util.List;

public interface DashboardService {
    List<MemberSummaryDTO> getMembersSummary();
}
