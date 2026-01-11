package com.gympulse.api.controllers;

import com.gympulse.api.dtos.CheckInSummaryDTO;
import com.gympulse.api.dtos.DashboardChartsDTO;
import com.gympulse.api.dtos.DashboardStatsDTO;
import com.gympulse.api.dtos.MemberSummaryDTO;
import com.gympulse.api.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/members-summary")
    public ResponseEntity<List<MemberSummaryDTO>> getMembersSummary() {
        return ResponseEntity.ok(dashboardService.getMembersSummary());
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        return ResponseEntity.ok(dashboardService.getStats());
    }

    @GetMapping("/charts")
    public ResponseEntity<DashboardChartsDTO> getDashboardCharts() {
        return ResponseEntity.ok(dashboardService.getChartsData());
    }

    @GetMapping("/alerts")
    public ResponseEntity<List<MemberSummaryDTO>> getMembershipAlerts() {
        List<MemberSummaryDTO> alerts = dashboardService.getMembershipAlerts();
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/recent-check-ins")
    public ResponseEntity<List<CheckInSummaryDTO>> getRecentCheckIns() {
        List<CheckInSummaryDTO> recentCheckIns = dashboardService.getRecentCheckIns();
        return ResponseEntity.ok(recentCheckIns);
    }
}
