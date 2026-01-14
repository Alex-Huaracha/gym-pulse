import { useEffect, useState } from 'react';
import {
  X,
  Plus,
  Trash2,
  Tag,
  Pencil,
  CheckCircle,
  RotateCcw,
} from 'lucide-react';
import type { MembershipPlan } from '../../types';
import { membershipPlanService } from '../../services/membership-plan-service';

interface Props {
  isOpen: boolean;
  onClose: () => void;
}

export function PlansManagerModal({ isOpen, onClose }: Props) {
  const [plans, setPlans] = useState<MembershipPlan[]>([]);
  const [loading, setLoading] = useState(false);

  const [editingId, setEditingId] = useState<number | null>(null);

  const [formData, setFormData] = useState<MembershipPlan>({
    name: '',
    price: 0,
    durationDays: 30,
  });

  useEffect(() => {
    if (isOpen) fetchPlans();
  }, [isOpen]);

  const fetchPlans = async () => {
    try {
      const data = await membershipPlanService.getAll();
      setPlans(data);
    } catch (error) {
      console.error('Error cargando planes', error);
    }
  };

  const handleEditClick = (plan: MembershipPlan) => {
    setEditingId(plan.id!);
    setFormData({
      name: plan.name,
      price: plan.price,
      durationDays: plan.durationDays,
    });
  };

  const handleCancelEdit = () => {
    setEditingId(null);
    setFormData({ name: '', price: 0, durationDays: 30 });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!formData.name || !formData.price) return;

    setLoading(true);
    try {
      if (editingId) {
        await membershipPlanService.update(editingId, formData);
      } else {
        await membershipPlanService.create(formData);
      }

      await fetchPlans();
      handleCancelEdit();
    } catch (error) {
      console.error('Error guardando plan', error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (
      !confirm(
        '¿Eliminar este plan? Si hay usuarios activos con este plan, podría causar inconsistencias.'
      )
    )
      return;
    try {
      await membershipPlanService.delete(id);
      await fetchPlans();
      if (editingId === id) handleCancelEdit();
    } catch (error) {
      console.error('Error eliminando plan', error);
    }
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black/60 backdrop-blur-sm flex items-center justify-center z-50 p-4 animate-in fade-in duration-200">
      <div className="bg-[#1E1F20] border border-[#444746] w-full max-w-3xl rounded-xl shadow-2xl overflow-hidden flex flex-col max-h-[90vh]">
        {/* Header */}
        <div className="flex justify-between items-center p-5 border-b border-[#444746] bg-[#2D2E2F]">
          <div className="flex items-center gap-2">
            <Tag size={20} className="text-[#E3E3E3]" />
            <h2 className="text-[#E3E3E3] font-medium text-lg">
              Gestionar Planes
            </h2>
          </div>
          <button
            onClick={onClose}
            className="text-[#8E918F] hover:text-white transition-colors"
          >
            <X size={24} />
          </button>
        </div>

        {/* Content */}
        <div className="p-5 overflow-y-auto space-y-6">
          {/* --- FORMULARIO (Dual: Crear / Editar) --- */}
          <form
            onSubmit={handleSubmit}
            className={`p-4 rounded-lg border ${
              editingId
                ? 'bg-amber-500/5 border-amber-500/20'
                : 'bg-[#2D2E2F]/30 border-[#444746] border-dashed'
            }`}
          >
            <div className="flex justify-between items-center mb-3">
              <h3
                className={`text-xs uppercase tracking-wide font-semibold ${
                  editingId ? 'text-amber-400' : 'text-[#8E918F]'
                }`}
              >
                {editingId ? 'Editando Plan' : 'Agregar Nuevo Plan'}
              </h3>
              {editingId && (
                <button
                  type="button"
                  onClick={handleCancelEdit}
                  className="text-xs text-[#8E918F] hover:text-white flex items-center gap-1"
                >
                  <RotateCcw size={12} /> Cancelar
                </button>
              )}
            </div>

            <div className="grid grid-cols-12 gap-3">
              <div className="col-span-6">
                <input
                  type="text"
                  placeholder="Nombre (ej. Mensual)"
                  className="w-full bg-[#1E1F20] border border-[#444746] text-[#E3E3E3] text-sm rounded-lg p-2.5 focus:outline-none focus:border-[#E3E3E3]"
                  value={formData.name}
                  onChange={(e) =>
                    setFormData({ ...formData, name: e.target.value })
                  }
                />
              </div>
              <div className="col-span-3">
                <input
                  type="number"
                  placeholder="S/."
                  className="w-full bg-[#1E1F20] border border-[#444746] text-[#E3E3E3] text-sm rounded-lg p-2.5 focus:outline-none focus:border-[#E3E3E3]"
                  value={formData.price || ''}
                  onChange={(e) =>
                    setFormData({ ...formData, price: Number(e.target.value) })
                  }
                />
              </div>
              <div className="col-span-3">
                {/* Select amigable que convierte a DÍAS por debajo */}
                <select
                  className="w-full bg-[#1E1F20] border border-[#444746] text-[#E3E3E3] text-sm rounded-lg p-2.5 focus:outline-none focus:border-[#E3E3E3]"
                  value={formData.durationDays}
                  onChange={(e) =>
                    setFormData({
                      ...formData,
                      durationDays: Number(e.target.value),
                    })
                  }
                >
                  <option value={1}>1 Día (Diario)</option>
                  <option value={7}>7 Días (Semanal)</option>
                  <option value={15}>15 Días (Quincenal)</option>
                  <option value={30}>30 Días (Mensual)</option>
                  <option value={90}>90 Días (Trimestral)</option>
                  <option value={180}>180 Días (Semestral)</option>
                  <option value={365}>365 Días (Anual)</option>
                </select>
              </div>
            </div>

            <button
              type="submit"
              disabled={loading}
              className={`w-full mt-3 font-medium text-sm py-2.5 rounded-lg transition-colors flex items-center justify-center gap-2 disabled:opacity-50 ${
                editingId
                  ? 'bg-amber-500 hover:bg-amber-400 text-black'
                  : 'bg-[#E3E3E3] hover:bg-white text-black'
              }`}
            >
              {editingId ? <CheckCircle size={16} /> : <Plus size={16} />}
              {loading
                ? 'Procesando...'
                : editingId
                ? 'Actualizar Plan'
                : 'Guardar Plan'}
            </button>
          </form>

          {/* --- LISTA --- */}
          <div>
            <h3 className="text-xs text-[#8E918F] uppercase tracking-wide mb-3 font-semibold">
              Planes Disponibles
            </h3>
            <div className="space-y-2 max-h-75 overflow-y-auto pr-2 scrollbar-thin">
              {plans.length === 0 && (
                <div className="text-center py-8 text-[#8E918F] text-sm bg-[#2D2E2F]/20 rounded-lg border border-[#444746]/50">
                  No hay planes registrados.
                </div>
              )}
              {plans.map((plan) => (
                <div
                  key={plan.id}
                  className={`flex justify-between items-center bg-[#2D2E2F] p-4 rounded-lg border transition-colors ${
                    editingId === plan.id
                      ? 'border-amber-500/50 ring-1 ring-amber-500/20'
                      : 'border-[#444746] hover:border-[#8E918F]'
                  }`}
                >
                  <div>
                    <p className="text-[#E3E3E3] font-medium">{plan.name}</p>
                    <p className="text-[#8E918F] text-xs mt-0.5 flex items-center gap-1">
                      <span>⏱ {plan.durationDays} días</span>
                    </p>
                  </div>
                  <div className="flex items-center gap-3">
                    <span className="text-[#E3E3E3] font-mono font-medium">
                      S/. {plan.price.toFixed(2)}
                    </span>

                    {/* Botón EDITAR */}
                    <button
                      onClick={() => handleEditClick(plan)}
                      className="text-[#8E918F] hover:text-amber-400 p-2 hover:bg-[#1E1F20] rounded-lg transition-all"
                      title="Editar"
                    >
                      <Pencil size={16} />
                    </button>

                    {/* Botón BORRAR */}
                    <button
                      onClick={() => plan.id && handleDelete(plan.id)}
                      className="text-[#8E918F] hover:text-red-400 p-2 hover:bg-[#1E1F20] rounded-lg transition-all"
                      title="Eliminar"
                    >
                      <Trash2 size={16} />
                    </button>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
