import { useEffect, useState } from 'react';
import { Card } from '../components/dashboard/card';
import { Users, Activity, CreditCard } from 'lucide-react';
import { dashboardService } from '../services/dashboard-service';
import type { DashboardStats } from '../types';
import { AnalyticsWidget } from '../components/dashboard/analytics-widget';
import { AlertsWidget } from '../components/dashboard/alerts-widget';
import { LiveFeedWidget } from '../components/dashboard/live-feed-widget';

export default function DashboardPage() {
  const [stats, setStats] = useState<DashboardStats | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await dashboardService.getStats();
        setStats(data);
      } catch (error) {
        console.error('Error cargando dashboard:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  if (loading) {
    return (
      <div className="min-h-screen p-6 flex items-center justify-center text-[#E3E3E3]">
        Cargando sistema...
      </div>
    );
  }

  return (
    <div className="min-h-screen">
      <div className="h-[calc(100vh-2rem)] p-6 flex flex-col gap-4">
        <header className="mb-8">
          <h1 className="text-4xl font-medium text-[#E3E3E3]">
            Panel de Control
          </h1>
          <p className="text-[#8E918F] text-sm mt-1">Gimnasio "GymPulse"</p>
        </header>

        <div className="flex-1 grid grid-cols-1 md:grid-cols-4 grid-rows-3 gap-4 min-h-0">
          {/* Widgets */}
          <Card className="md:col-start-1 col-span-1 row-span-2 bg-[#1E1F20]">
            <AlertsWidget />
          </Card>

          <Card className="md:col-start-1 col-span-1 row-span-1 bg-[#1E1F20]">
            <LiveFeedWidget />
          </Card>

          {/* KPIs */}
          <Card
            title="Socios Activos"
            className="md:col-start-2 col-span-1 row-start-1 row-span-1"
          >
            <div className="flex flex-col justify-end h-full pb-2">
              <div className="text-5xl font-medium text-white">
                {stats?.totalActiveMembers || 0}
              </div>
              <div className="flex items-center gap-2 mt-2 text-[#C4C7C5]">
                <Users size={18} />
                <span className="text-sm">Total registrados</span>
              </div>
            </div>
          </Card>

          <Card
            title="Check-ins Hoy"
            className="md:col-start-3 col-span-1 row-start-1 row-span-1"
          >
            <div className="flex flex-col justify-end h-full pb-2">
              <div className="text-5xl font-medium text-white">
                {stats?.checkInsToday || 0}
              </div>
              <div className="flex items-center gap-2 mt-2 text-[#C4C7C5]">
                <Activity size={18} />{' '}
                <span className="text-sm">Afluencia</span>
              </div>
            </div>
          </Card>

          <Card
            title="Ingresos Mes"
            className="md:col-start-4 col-span-1 row-start-1 row-span-1"
          >
            <div className="flex flex-col justify-end h-full pb-2">
              <div className="text-4xl xl:text-5xl font-medium text-white">
                S/. {stats?.monthlyRevenue?.toLocaleString() || '0.00'}
              </div>
              <div className="flex items-center gap-2 mt-2 text-[#C4C7C5]">
                <CreditCard size={18} />{' '}
                <span className="text-sm">Facturado</span>
              </div>
            </div>
          </Card>

          {/* Charts */}
          <Card
            title="Tendencias"
            className="md:col-start-2 col-span-1 md:col-span-3 row-start-2 row-span-2"
          >
            <div className="h-full w-full pt-2">
              <AnalyticsWidget />
            </div>
          </Card>
        </div>
      </div>
    </div>
  );
}
