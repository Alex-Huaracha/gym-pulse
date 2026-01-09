import { History } from 'lucide-react';

export function LiveFeedWidget() {
  return (
    <div className="h-full flex flex-col">
      <div className="flex items-center gap-2 mb-3">
        <div className="p-1.5 bg-blue-500/10 rounded-md">
          <History size={14} className="text-blue-500" />
        </div>
        <h3 className="text-[#E3E3E3] text-xs font-medium uppercase tracking-wide">
          Últimos Accesos
        </h3>
      </div>

      <div className="flex-1 overflow-y-auto space-y-2 pr-1 scrollbar-thin">
        {[1, 2, 3, 4].map((i) => (
          <div
            key={i}
            className="flex items-center justify-between p-2 hover:bg-[#2D2E2F] rounded-lg transition-colors border-b border-[#444746]/50 last:border-0"
          >
            <div className="flex items-center gap-3">
              <div className="w-2 h-2 rounded-full bg-emerald-500 shadow-[0_0_8px_rgba(16,185,129,0.4)]"></div>
              <span className="text-sm text-[#E3E3E3]">Socio Ejemplo {i}</span>
            </div>
            <span className="text-xs text-[#8E918F] font-mono">
              10:0{5 - i}
            </span>
          </div>
        ))}
      </div>
    </div>
  );
}
