import { useEffect, useState } from 'react';
import { History, UserCheck } from 'lucide-react';
import { dashboardService } from '../../services/dashboard-service';
import type { CheckInSummary } from '../../types';

export function LiveFeedWidget() {
  const [feed, setFeed] = useState<CheckInSummary[]>([]);

  useEffect(() => {
    const fetchFeed = async () => {
      try {
        const data = await dashboardService.getRecentCheckIns();
        setFeed(data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchFeed();

    const interval = setInterval(fetchFeed, 30000);
    return () => clearInterval(interval);
  }, []);

  const formatTime = (isoString: string) => {
    const date = new Date(isoString);
    return date.toLocaleTimeString('es-ES', {
      hour: '2-digit',
      minute: '2-digit',
    });
  };

  return (
    <div className="h-full flex flex-col">
      <div className="flex items-center gap-2 mb-3">
        <div className="p-1.5 bg-red-500/10 rounded-md">
          <History size={14} className="text-emerald-500" />
        </div>
        <h3 className="text-[#E3E3E3] text-xs font-medium uppercase tracking-wide">
          Últimos Accesos
        </h3>
      </div>

      <div className="flex-1 overflow-y-auto space-y-3 pr-2 min-h-0 scrollbar-thin scrollbar-thumb-[#444746] scrollbar-track-transparent hover:scrollbar-thumb-[#5A5D5B]">
        {feed.length === 0 ? (
          <p className="text-[#8E918F] text-xs text-center">
            Sin actividad reciente
          </p>
        ) : (
          feed.map((item) => (
            <div
              key={item.id}
              className="flex items-center justify-between group"
            >
              <div className="flex items-center gap-3">
                {/* Indicador visual de "Log" */}
                <div className="w-8 h-8 rounded-full bg-[#2D2E2F] flex items-center justify-center border border-[#444746] group-hover:border-emerald-500/50 transition-colors">
                  <UserCheck
                    size={14}
                    className="text-[#8E918F] group-hover:text-emerald-400"
                  />
                </div>
                <div>
                  <p className="text-[#E3E3E3] text-sm font-medium">
                    {item.memberName}
                  </p>
                  {/* <p className="text-[#8E918F] text-[10px]">
                    Entrada registrada
                  </p> */}
                </div>
              </div>
              <span className="text-xs text-[#E3E3E3] font-mono bg-[#2D2E2F] px-2 py-1 rounded">
                {formatTime(item.checkInTime)}
              </span>
            </div>
          ))
        )}
      </div>
    </div>
  );
}
