export interface DashboardStats {
  totalActiveMembers: number;
  checkInsToday: number;
  monthlyRevenue: number;
}

export interface DashboardCharts {
  hourlyInflow: Record<string, number>;
  weeklyInflow: Record<string, number>;
}
