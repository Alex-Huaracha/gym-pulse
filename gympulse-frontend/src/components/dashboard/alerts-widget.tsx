import { Bell, AlertTriangle } from 'lucide-react';

export function AlertsWidget() {
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
        <div className="bg-red-500/5 border border-red-500/20 p-3 rounded-lg flex justify-between items-center">
          <div>
            <p className="text-[#E3E3E3] text-sm font-medium">Carlos Vega</p>
            <p className="text-red-400 text-xs">Vence HOY</p>
          </div>
          <AlertTriangle size={14} className="text-red-500" />
        </div>
        <div className="bg-[#2D2E2F] border border-[#444746] p-3 rounded-lg flex justify-between items-center">
          <div>
            <p className="text-[#E3E3E3] text-sm font-medium">Ana Maria</p>
            <p className="text-amber-400 text-xs">Vence en 2 días</p>
          </div>
        </div>
      </div>
    </div>
  );
}
