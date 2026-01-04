package com.gympulse.api.services;

import com.gympulse.api.dtos.DashboardChartsDTO;
import com.gympulse.api.dtos.DashboardStatsDTO;
import com.gympulse.api.dtos.MemberSummaryDTO;

import java.util.List;

public interface DashboardService {
    List<MemberSummaryDTO> getMembersSummary();
    DashboardStatsDTO getStats();
    DashboardChartsDTO getChartsData();
}
