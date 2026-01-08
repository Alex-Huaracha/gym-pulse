import { useState, useEffect } from 'react';
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
  LineChart,
  Line,
  CartesianGrid,
} from 'recharts';
import * as Tabs from '@radix-ui/react-tabs';
import { dashboardService } from '../../services/dashboard-service';
import type { DashboardCharts } from '../../types';

export function AnalyticsWidget() {
  const [data, setData] = useState<DashboardCharts | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    dashboardService.getCharts().then((res) => {
      setData(res);
      setLoading(false);
    });
  }, []);

  if (loading)
    return <div className="text-sm text-[#8E918F]">Cargando gráficos...</div>;
  if (!data)
    return <div className="text-sm text-red-400">Error al cargar datos</div>;

  // Hourly Data (Fill 24h to ensure order)
  const hourlyData = Array.from({ length: 24 }, (_, i) => {
    const hourKey = i.toString();
    return {
      name: `${i}:00`,
      value: data.hourlyInflow[hourKey] || 0, // If no data, default to 0
    };
  });

  // Weekly Data (Ordered Monday to Sunday)
  const daysOrder = [
    'MONDAY',
    'TUESDAY',
    'WEDNESDAY',
    'THURSDAY',
    'FRIDAY',
    'SATURDAY',
    'SUNDAY',
  ];
  const dayLabels: Record<string, string> = {
    MONDAY: 'Lun',
    TUESDAY: 'Mar',
    WEDNESDAY: 'Mié',
    THURSDAY: 'Jue',
    FRIDAY: 'Vie',
    SATURDAY: 'Sáb',
    SUNDAY: 'Dom',
  };

  const weeklyData = daysOrder.map((day) => ({
    name: dayLabels[day], // Convert "MONDAY" to "Lun"
    value: data.weeklyInflow[day] || 0,
  }));

  return (
    <Tabs.Root defaultValue="hourly" className="h-full flex flex-col">
      {/* HEADER WITH TABS */}
      <div className="flex justify-between items-center mb-4">
        <Tabs.List className="flex bg-[#2D2E2F] p-1 rounded-lg border border-[#444746]">
          <Tabs.Trigger
            value="hourly"
            className="px-3 py-1 text-xs font-medium text-[#C4C7C5] rounded hover:text-white data-[state=active]:bg-[#444746] data-[state=active]:text-white transition-all"
          >
            Por Hora (Hoy)
          </Tabs.Trigger>
          <Tabs.Trigger
            value="weekly"
            className="px-3 py-1 text-xs font-medium text-[#C4C7C5] rounded hover:text-white data-[state=active]:bg-[#444746] data-[state=active]:text-white transition-all"
          >
            Semanal
          </Tabs.Trigger>
        </Tabs.List>
      </div>

      {/* CHART 1: BAR CHART (HOURLY) */}
      <Tabs.Content value="hourly" className="flex-1 w-full min-h-0">
        <ResponsiveContainer width="100%" height="100%">
          <BarChart data={hourlyData}>
            <XAxis
              dataKey="name"
              tick={{ fill: '#8E918F', fontSize: 10 }}
              axisLine={false}
              tickLine={false}
            />
            <Tooltip
              contentStyle={{
                backgroundColor: '#1E1F20',
                borderColor: '#444746',
                color: '#E3E3E3',
              }}
              cursor={{ fill: '#2D2E2F' }}
            />
            <Bar
              dataKey="value"
              fill="#E3E3E3"
              radius={[4, 4, 0, 0]}
              barSize={20}
            />
          </BarChart>
        </ResponsiveContainer>
      </Tabs.Content>

      {/* CHART 2: LINE CHART (WEEKLY) */}
      <Tabs.Content value="weekly" className="flex-1 w-full min-h-0">
        <ResponsiveContainer width="100%" height="100%">
          <LineChart data={weeklyData}>
            <CartesianGrid
              strokeDasharray="3 3"
              stroke="#444746"
              vertical={false}
            />
            <XAxis
              dataKey="name"
              tick={{ fill: '#8E918F', fontSize: 12 }}
              axisLine={false}
              tickLine={false}
              dy={10}
            />
            <YAxis
              tick={{ fill: '#8E918F', fontSize: 12 }}
              axisLine={false}
              tickLine={false}
            />
            <Tooltip
              contentStyle={{
                backgroundColor: '#1E1F20',
                borderColor: '#444746',
                color: '#E3E3E3',
              }}
            />
            <Line
              type="monotone"
              dataKey="value"
              stroke="#E3E3E3"
              strokeWidth={3}
              dot={{ fill: '#1E1F20', stroke: '#E3E3E3', strokeWidth: 2, r: 4 }}
            />
          </LineChart>
        </ResponsiveContainer>
      </Tabs.Content>
    </Tabs.Root>
  );
}
