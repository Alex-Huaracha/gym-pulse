export interface DashboardStats {
  totalActiveMembers: number;
  checkInsToday: number;
  monthlyRevenue: number;
}

export interface DashboardCharts {
  hourlyInflow: Record<string, number>;
  weeklyInflow: Record<string, number>;
}

export interface MemberSummary {
  id: number;
  fullName: string;
  dni: string;
  status: 'ACTIVE' | 'INACTIVE';
  currentPlan: string | null;
  endDate: string | null;
  daysRemaining: number;
}
