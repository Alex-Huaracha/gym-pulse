import api from './api';
import type { DashboardStats, DashboardCharts } from '../types';

export const dashboardService = {
  getStats: async (): Promise<DashboardStats> => {
    const { data } = await api.get<DashboardStats>('/dashboard/stats');
    return data;
  },

  getCharts: async (): Promise<DashboardCharts> => {
    const { data } = await api.get<DashboardCharts>('/dashboard/charts');
    return data;
  },
};
