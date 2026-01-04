package com.gympulse.api.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class DashboardChartsDTO {
    private Map<Integer, Long> hourlyInflow;
    private Map<String, Long> weeklyInflow;
}
