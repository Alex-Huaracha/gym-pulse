import { Card } from '../components/dashboard/card';
import {
  Users,
  Activity,
  CreditCard,
  Plus,
  List,
  CheckCircle,
} from 'lucide-react';

export default function DashboardPage() {
  return (
    <div className="min-h-screen p-6 max-w-400 mx-auto">
      {/* HEADER */}
      <header className="mb-8">
        <h1 className="text-2xl font-medium text-[#E3E3E3]">
          Panel de Control
        </h1>
        <p className="text-[#8E918F] text-sm mt-1">Gimnasio "GymPulse"</p>
      </header>

      {/* GRID */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 auto-rows-[160px]">
        <Card title="Socios Activos" className="lg:col-span-1">
          <div className="flex flex-col justify-end h-full pb-2">
            <div className="text-5xl font-medium text-white">30</div>
            <div className="flex items-center gap-2 mt-2 text-[#C4C7C5]">
              <Users size={18} />
              <span className="text-sm">Total registrados</span>
            </div>
          </div>
        </Card>

        <Card title="Check-ins Hoy" className="lg:col-span-1">
          <div className="flex flex-col justify-end h-full pb-2">
            <div className="text-5xl font-medium text-white">12</div>
            <div className="flex items-center gap-2 mt-2 text-[#C4C7C5]">
              <Activity size={18} /> <span className="text-sm">Afluencia</span>
            </div>
          </div>
        </Card>

        <Card title="Ingresos Mes" className="lg:col-span-1">
          <div className="flex flex-col justify-end h-full pb-2">
            <div className="text-5xl font-medium text-white">$4.5k</div>
            <div className="flex items-center gap-2 mt-2 text-[#C4C7C5]">
              <CreditCard size={18} />{' '}
              <span className="text-sm">Facturado</span>
            </div>
          </div>
        </Card>

        {/* Quick Actions */}
        <Card title="Acciones" className="lg:col-span-1 lg:row-span-2">
          <div className="flex flex-col gap-3 h-full justify-center">
            <button className="flex items-center justify-center gap-2 p-4 bg-[#E3E3E3] text-black rounded-xl hover:bg-white transition-colors font-medium">
              <Plus size={20} />
              Nuevo Socio
            </button>
            <button className="flex items-center justify-center gap-2 p-4 bg-[#2D2E2F] text-[#E3E3E3] border border-[#444746] rounded-xl hover:bg-[#393b3d] transition-colors font-medium">
              <CheckCircle size={20} />
              Registrar Visita
            </button>
            <button className="flex items-center justify-center gap-2 p-4 bg-[#2D2E2F] text-[#E3E3E3] border border-[#444746] rounded-xl hover:bg-[#393b3d] transition-colors font-medium">
              <List size={20} />
              Ver Socios
            </button>
          </div>
        </Card>

        {/* GRAPH */}
        <Card title="Tendencias" className="lg:col-span-3 lg:row-span-2">
          <div className="flex items-center justify-center h-full border border-dashed border-[#444746] rounded-lg">
            <span className="text-[#8E918F]">
              Gráfico de Afluencia (Próximamente)
            </span>
          </div>
        </Card>
      </div>
    </div>
  );
}
