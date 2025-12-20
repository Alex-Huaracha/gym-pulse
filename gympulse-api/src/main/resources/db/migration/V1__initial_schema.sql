-- 1. Tabla de Socios (Members)
CREATE TABLE members
(
    id         SERIAL PRIMARY KEY,
    dni        VARCHAR(20) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    email      VARCHAR(100),
    phone      VARCHAR(20),
    status     VARCHAR(20)              DEFAULT 'INACTIVE', -- ACTIVE, INACTIVE, DEBTOR
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Indexamos el DNI ya que será el campo de búsqueda principal para el Check-in
CREATE INDEX idx_members_dni ON members (dni);

-- 2. Tabla de Planes (Membership Types)
-- Ej: Mensual, Trimestral, Anual
CREATE TABLE membership_plans
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(50)    NOT NULL,
    duration_days INTEGER        NOT NULL,
    price         DECIMAL(10, 2) NOT NULL
);

-- 3. Tabla de Membresías (Mapeo de qué socio tiene qué plan)
CREATE TABLE memberships
(
    id         SERIAL PRIMARY KEY,
    member_id  INTEGER REFERENCES members (id) ON DELETE CASCADE,
    plan_id    INTEGER REFERENCES membership_plans (id),
    start_date DATE NOT NULL,
    end_date   DATE NOT NULL,
    is_paid    BOOLEAN                  DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 4. Tabla de Asistencia (Check-ins)
CREATE TABLE check_ins
(
    id            SERIAL PRIMARY KEY,
    member_id     INTEGER REFERENCES members (id) ON DELETE CASCADE,
    check_in_time TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);