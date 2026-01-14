import api from './api';
import type { MembershipPlan } from '../types';

export const membershipPlanService = {
  getAll: async (): Promise<MembershipPlan[]> => {
    const { data } = await api.get<MembershipPlan[]>('/plans');
    return data;
  },

  create: async (plan: MembershipPlan): Promise<MembershipPlan> => {
    const { data } = await api.post<MembershipPlan>('/plans', plan);
    return data;
  },

  update: async (id: number, plan: MembershipPlan): Promise<MembershipPlan> => {
    const { data } = await api.put<MembershipPlan>(`/plans/${id}`, plan);
    return data;
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/plans/${id}`);
  },
};
