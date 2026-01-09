import { useEffect, useState } from 'react';
import { Search, Plus, MoreHorizontal, User } from 'lucide-react';
import { dashboardService } from '../../services/dashboard-service';
import type { MemberSummary } from '../../types';

export function MembersTableWidget() {
  const [members, setMembers] = useState<MemberSummary[]>([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    const fetchMembers = async () => {
      try {
        const data = await dashboardService.getMembersSummary();
        setMembers(data);
      } catch (error) {
        console.error('Error cargando socios:', error);
      } finally {
        setLoading(false);
      }
    };
    fetchMembers();
  }, []);

  const filteredMembers = members.filter(
    (m) =>
      m.fullName.toLowerCase().includes(searchTerm.toLowerCase()) ||
      m.dni.includes(searchTerm)
  );

  return (
    <div className="flex flex-col h-full p-4">
      <div className="flex justify-between items-center mb-4">
        <h3 className="text-[#E3E3E3] font-medium text-lg">
          Gestión de Socios
        </h3>

        <div className="flex gap-2">
          <div className="relative">
            <Search
              size={16}
              className="absolute left-3 top-1/2 -translate-y-1/2 text-[#8E918F]"
            />
            <input
              type="text"
              placeholder="Buscar DNI o Nombre..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="bg-[#2D2E2F] border border-[#444746] text-[#E3E3E3] text-sm rounded-lg pl-9 pr-4 py-2 focus:outline-none focus:border-[#E3E3E3] w-64 transition-colors placeholder:text-[#8E918F]"
            />
          </div>

          <button className="flex items-center gap-2 bg-[#E3E3E3] hover:bg-white text-black text-sm font-medium px-4 py-2 rounded-lg transition-colors cursor-pointer">
            <Plus size={16} />
            Nuevo Socio
          </button>
        </div>
      </div>

      <div className="flex-1 overflow-auto rounded-xl border border-[#444746] bg-[#1E1F20] scrollbar-thin">
        <table className="w-full text-left border-collapse">
          <thead className="bg-[#2D2E2F] text-[#8E918F] text-xs uppercase sticky top-0 z-10">
            <tr>
              <th className="p-4 font-medium">Socio</th>
              <th className="p-4 font-medium">DNI</th>
              <th className="p-4 font-medium">Plan Actual</th>
              <th className="p-4 font-medium">Vence</th>
              <th className="p-4 font-medium">Días Restantes</th>
              <th className="p-4 font-medium">Estado</th>
              <th className="p-4 font-medium text-right">Acciones</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-[#444746]">
            {loading ? (
              <tr>
                <td colSpan={5} className="p-8 text-center text-[#8E918F]">
                  Cargando lista de socios...
                </td>
              </tr>
            ) : filteredMembers.length === 0 ? (
              <tr>
                <td colSpan={5} className="p-8 text-center text-[#8E918F]">
                  No se encontraron socios.
                </td>
              </tr>
            ) : (
              filteredMembers.map((member) => (
                <tr
                  key={member.id}
                  className="hover:bg-[#2D2E2F]/50 transition-colors group"
                >
                  <td className="p-4">
                    <div className="flex items-center gap-3">
                      <div className="w-8 h-8 rounded-full bg-[#2D2E2F] flex items-center justify-center border border-[#444746]">
                        <User size={14} className="text-[#8E918F]" />
                      </div>
                      <span className="text-[#E3E3E3] font-medium">
                        {member.fullName}
                      </span>
                    </div>
                  </td>
                  <td className="p-4 text-[#C4C7C5] font-mono text-sm">
                    {member.dni}
                  </td>
                  <td className="p-4">
                    <div className="flex flex-col">
                      <span className="text-[#E3E3E3] text-sm">
                        {member.currentPlan}
                      </span>
                    </div>
                  </td>
                  <td className="p-4">
                    <div className="flex flex-col">
                      <span className="text-[#C4C7C5] font-mono text-sm">
                        {member.endDate ? member.endDate : '-'}{' '}
                      </span>
                    </div>
                  </td>
                  <td className="p-4">
                    <div className="flex flex-col">
                      <span className="text-[#C4C7C5] font-mono text-sm">
                        {member.daysRemaining ? member.daysRemaining : '-'}
                      </span>
                    </div>
                  </td>
                  <td className="p-4">
                    <span
                      className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium border ${
                        member.status === 'ACTIVE'
                          ? 'bg-emerald-500/10 text-emerald-400 border-emerald-500/20'
                          : 'bg-red-500/10 text-red-400 border-red-500/20'
                      }`}
                    >
                      {member.status === 'ACTIVE' ? 'Activo' : 'Inactivo'}
                    </span>
                  </td>
                  <td className="p-4 text-right">
                    <button className="text-[#8E918F] hover:text-white p-2 rounded-lg hover:bg-[#444746] transition-colors cursor-pointer">
                      <MoreHorizontal size={18} />
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
