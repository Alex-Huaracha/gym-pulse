# Gym Pulse

Gym management system: members, membership plans, billing periods, and access check-ins. Monorepo with a Spring Boot REST API and a React dashboard sharing a single Postgres instance.

> **Status:** work in progress. No authentication yet — `/api/v1/**` is `permitAll`. Not production-ready.

## Stack

- **Backend:** Spring Boot 3.5, Java 21, Spring Data JPA, Flyway, MapStruct, Lombok
- **Frontend:** React 19, Vite 7, TypeScript, Tailwind v4, Axios, React Router
- **Database:** PostgreSQL 16 (via Docker Compose)

## Repository layout

```
gym-pulse/
├── gympulse-api/         Spring Boot REST API (Maven)
├── gympulse-frontend/    React + Vite dashboard (pnpm)
└── docker-compose.yml    Postgres 16
```

## Getting started

### Prerequisites

- Docker + Docker Compose
- JDK 21
- Node.js 20+ and pnpm

### 1. Start the database

```bash
docker compose up -d
```

Postgres runs on `localhost:5432` (db `gympulse_db`, user `alex`, password `password`). Credentials match `application.properties`; change both if you need to.

### 2. Run the API

```bash
cd gympulse-api
./mvnw spring-boot:run
```

API is served at `http://localhost:8080/api/v1`.

On first run, `DataSeeder` populates the DB with 3 plans and 50 members (30 active with check-in history, 10 expired, 10 prospects). The seeder only runs when the `members` table is empty — to re-seed, drop the volume:

```bash
docker compose down -v
```

### 3. Run the frontend

```bash
cd gympulse-frontend
pnpm install
echo "VITE_API_URL=http://localhost:8080/api/v1" > .env
pnpm dev
```

Dashboard is served at `http://localhost:5173`.

## Domain model

- **Member** — gym customer (`firstName`, `lastName`, `dni`, `status`).
- **MembershipPlan** — catalog entry: `name`, `durationDays`, `price`.
- **Membership** — instance bound to a member and a plan; `endDate = startDate + plan.durationDays`. Tracks `isPaid`.
- **CheckIn** — immutable access event; stores `isValid` at the moment of registration.

Splitting `MembershipPlan` (catalog) from `Membership` (instance) means changing a plan's price or duration does not retroactively affect memberships already issued.

## API endpoints

All under `/api/v1`. See `gympulse-api/requests.http` for ready-to-run examples.

| Group              | Endpoint                              |
| ------------------ | ------------------------------------- |
| Members            | `/members`                            |
| Plans              | `/plans`                              |
| Memberships        | `/memberships`                        |
| Check-ins          | `/check-ins`                          |
| Dashboard summary  | `/dashboard/members-summary`          |
| Dashboard stats    | `/dashboard/stats`                    |
| Dashboard charts   | `/dashboard/charts`                   |
| Recent check-ins   | `/dashboard/recent-check-ins`         |

## Design notes

- **Schema is the source of truth.** Flyway migrations live in `gympulse-api/src/main/resources/db/migration/`. Hibernate runs with `ddl-auto=validate`, so the app refuses to start if JPA entities drift from the migrated schema. Never edit an applied migration; add a new `V<n>__<desc>.sql`.
- **Check-ins are append-only events.** `CheckIn.isValid` is computed once (against the member's paid, non-expired membership) and stored. The historical record reflects whether access was granted at that moment, even if the membership state changes later.
- **DTO boundary via MapStruct.** Controllers expose DTOs, not JPA entities, to avoid leaking lazy relations into the HTTP layer. The annotation processor order in `pom.xml` matters: `lombok` → `lombok-mapstruct-binding` → `mapstruct-processor`.
- **CORS / Security.** `SecurityConfig` disables CSRF, allows origins `localhost:3000/4200/5173`, and applies `permitAll` to `/api/v1/**`. Auth is not implemented yet.

## Common commands

**Backend** (`gympulse-api/`):

```bash
./mvnw spring-boot:run                   # run app
./mvnw test                              # run all tests
./mvnw test -Dtest=ClassName#method      # run a single test
./mvnw package                           # build jar
```

**Frontend** (`gympulse-frontend/`):

```bash
pnpm dev          # vite dev server
pnpm build        # tsc -b && vite build
pnpm lint         # eslint
pnpm preview
```

**Database:**

```bash
docker compose up -d      # start postgres
docker compose down       # stop (keeps volume)
docker compose down -v    # stop and drop volume (forces re-seed)
```

## Roadmap

- Authentication and role-based access (admin / front-desk).
- Fix `MembershipRepository.findByMemberIdAndIsPaidTrue` to handle multiple paid memberships per member (renewals).
- Duplicate check-in protection (same member within a short window).
- Payments and invoicing.
- Trainer scheduling.
