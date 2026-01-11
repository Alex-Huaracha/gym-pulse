import { Bell, AlertTriangle, CheckCircle } from 'lucide-react';
import { useEffect, useState } from 'react';
import type { MemberSummary } from '../../types';
import { dashboardService } from '../../services/dashboard-service';

export function AlertsWidget() {
  const [alerts, setAlerts] = useState<MemberSummary[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchAlerts = async () => {
      try {
        const data = await dashboardService.getAlerts();
        setAlerts(data);
      } catch (error) {
        console.error('Error cargando alertas:', error);
      } finally {
        setLoading(false);
      }
    };
    fetchAlerts();
  }, []);

  return (
    <div className="h-full flex flex-col">
      <div className="flex items-center gap-2 mb-3">
        <div className="p-1.5 bg-red-500/10 rounded-md">
          <Bell size={14} className="text-red-500" />
        </div>
        <h3 className="text-[#E3E3E3] text-xs font-medium uppercase tracking-wide">
          Por Vencer / Deudas
        </h3>
      </div>

      <div className="flex-1 overflow-y-auto space-y-2 pr-1 scrollbar-thin">
        {/* Mock Data - Luego lo conectamos */}
        {loading ? (
          <p className="text-[#8E918F] text-xs text-center py-4">
            Buscando deudas...
          </p>
        ) : alerts.length === 0 ? (
          <div className="flex flex-col items-center justify-center h-full text-[#8E918F] gap-2">
            <CheckCircle size={24} className="opacity-20" />
            <span className="text-xs">Todo al día</span>
          </div>
        ) : (
          <>
            {alerts.map((member) => {
              const isExpiredToday = member.daysRemaining === 0;
              const isUrgent =
                member.daysRemaining !== null && member.daysRemaining <= 1;

              return (
                <div
                  key={member.id}
                  className={`p-3 rounded-lg flex justify-between items-center ${
                    isExpiredToday || isUrgent
                      ? 'bg-red-500/5 border border-red-500/20'
                      : 'bg-[#2D2E2F] border border-[#444746]'
                  }`}
                >
                  <div>
                    <p className="text-[#E3E3E3] text-sm font-medium">
                      {member.fullName}
                    </p>
                    <p
                      className={`text-xs ${
                        isExpiredToday || isUrgent
                          ? 'text-red-400'
                          : 'text-amber-400'
                      }`}
                    >
                      {member.daysRemaining === 0
                        ? 'Vence HOY'
                        : member.daysRemaining === 1
                        ? 'Vence mañana'
                        : `Vence en ${member.daysRemaining} días`}
                    </p>
                  </div>
                  {(isExpiredToday || isUrgent) && (
                    <AlertTriangle size={14} className="text-red-500" />
                  )}
                </div>
              );
            })}
          </>
        )}
      </div>
    </div>
  );
}
