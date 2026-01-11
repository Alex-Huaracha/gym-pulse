import api from './api';
import type { DashboardStats, DashboardCharts, MemberSummary } from '../types';

export const dashboardService = {
  getStats: async (): Promise<DashboardStats> => {
    const { data } = await api.get<DashboardStats>('/dashboard/stats');
    return data;
  },

  getCharts: async (): Promise<DashboardCharts> => {
    const { data } = await api.get<DashboardCharts>('/dashboard/charts');
    return data;
  },

  getMembersSummary: async (): Promise<MemberSummary[]> => {
    const { data } = await api.get<MemberSummary[]>(
      '/dashboard/members-summary'
    );
    return data;
  },

  getAlerts: async (): Promise<MemberSummary[]> => {
    const { data } = await api.get<MemberSummary[]>('/dashboard/alerts');
    return data;
  },
};
