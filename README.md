# NextJOB

> Job board application connecting job seekers with employers. Still under active development.

NextJOB allows job seekers to browse, filter, and apply for job announcements, while employers can post listings and manage applications. The project uses a decoupled architecture: a Nuxt 4 SPA frontend communicates with a Spring Boot REST API backend secured with JWT.

## Table of Contents

- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Architecture](#architecture)
- [Database Schema](#database-schema)
- [API Reference](#api-reference)
- [Environment Variables](#environment-variables)
- [Available Scripts](#available-scripts)
- [Deployment](#deployment)
- [Troubleshooting](#troubleshooting)

---

## Tech Stack

| Layer      | Technology                                          |
| ---------- | --------------------------------------------------- |
| Frontend   | Nuxt 4.1.3, Vue 3, TypeScript 5.9                   |
| UI         | Nuxt UI 4.3, Tailwind CSS 4                         |
| Auth (FE)  | nuxt-auth-utils 0.5, Zod 4                          |
| Backend    | Spring Boot 3.5.6, Java 21                          |
| Security   | Spring Security, JWT (jjwt 0.11.5)                  |
| Database   | PostgreSQL (JPA / Hibernate, ddl-auto: create-drop) |
| Build (FE) | pnpm, Vite 7                                        |
| Build (BE) | Maven (mvnw wrapper)                                |

---

## Prerequisites

Make sure the following are installed before you begin:

- **Node.js** 20 or higher
- **pnpm** 9 or higher — `npm install -g pnpm`
- **Java** 21 — [Adoptium](https://adoptium.net/)
- **PostgreSQL** 15 or higher — or Docker (recommended)

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/marek-kramarczyk/NextJOB.git
cd NextJOB
```

### 2. Start the database (Docker)

```bash
cd backend
cp .env.example .env
# Fill in POSTGRES_URL, POSTGRES_USER, POSTGRES_PASSWORD, POSTGRES_PORT in .env
docker compose up -d
cd ..
```

The `docker-compose.yml` spins up a `postgres:latest` container. The Spring Boot app recreates the schema automatically on each startup (`ddl-auto=create-drop`).

### 3. Start the backend

```bash
cd backend
./mvnw spring-boot:run    # Linux/macOS
mvnw.cmd spring-boot:run  # Windows
```

The API starts on `http://localhost:8080`.

> **Note:** The JWT secret is currently hardcoded in `application.properties`. Replace it with a strong random value for production.

### 4. Start the frontend

```bash
cd frontend
cp .env.example .env
# Set API_URL=http://localhost:8080
pnpm install
pnpm dev
```

Open [http://localhost:3000](http://localhost:3000) in your browser.

---

## Project Structure

```
NextJOB/
├── backend/                        # Spring Boot REST API
│   ├── src/main/java/com/nextjob/
│   │   ├── NextjobApplication.java
│   │   ├── configs/                # Security, JWT filter, CORS, data seeding
│   │   ├── controllers/            # REST controllers (one per resource)
│   │   ├── services/               # Business logic
│   │   ├── repositories/           # Spring Data JPA repositories
│   │   ├── entities/               # JPA entities
│   │   ├── dtos/                   # Request/response DTOs
│   │   ├── mappers/                # Entity ↔ DTO mappers
│   │   ├── specifications/         # JPA Specifications for filtering
│   │   └── exceptions/             # Exception handling
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── docker-compose.yml
│   ├── .env.example
│   └── pom.xml
│
├── frontend/                       # Nuxt 4 SPA
│   ├── app/
│   │   ├── app.vue
│   │   ├── components/             # UI components (auth, announcements, nav, …)
│   │   ├── composables/            # useAPI, useAuth, useApplications, useJobSeeker
│   │   ├── layouts/                # default, admin, settings
│   │   ├── middleware/             # auth.global.ts (route guard)
│   │   ├── pages/                  # File-based routing
│   │   ├── schemas/                # Zod validation schemas
│   │   └── types/                  # TypeScript type definitions
│   ├── server/api/                 # Nuxt server-side API proxy
│   ├── .env.example
│   ├── nuxt.config.ts
│   └── package.json
│
├── base.sql                        # Reference SQL schema
└── README.md
```

---

## Architecture

### Request Lifecycle

```
Browser
  │
  ▼
Nuxt Page / Component
  │  Calls composable (useAPI / useApplications / …)
  ▼
useAPI.ts  ──── injects JWT from session (useAuth) ────► Authorization: Bearer <token>
  │
  ▼
Nuxt server/api/[...].ts  (optional server-side proxy)
  │
  ▼
Spring Boot REST API  (port 8080)
  │
  ├── JwtAuthenticationFilter  → validates token, sets SecurityContext
  ├── Controller                → maps HTTP method + path
  ├── Service                   → business logic, transactions
  ├── Repository (JPA)          → SQL queries via Hibernate
  └── PostgreSQL
```

### Authentication Flow

1. User submits credentials to `POST /api/auth/login`.
2. `AuthenticationService` validates via Spring Security's `AuthenticationManager`.
3. `JwtService` generates a signed JWT (HS256, 1-hour expiry).
4. Frontend stores the token in the session via `nuxt-auth-utils`.
5. Every subsequent request passes the token in the `Authorization` header via `useAPI`.
6. `JwtAuthenticationFilter` on the backend validates the token on each request.

### Filtering (Job Announcements)

Job listings support server-side filtering using JPA `Specification` objects (`JobAnnouncementSpecification`). The frontend sends filter parameters as query strings; the backend dynamically composes the `WHERE` clause and returns paginated results.

### Frontend Composables

| Composable           | Responsibility                                      |
| -------------------- | --------------------------------------------------- |
| `useAuth`            | Session token/user state, fetchUser, clearSession   |
| `useAPI`             | Fetch wrapper that injects the JWT Bearer token     |
| `useApplications`    | CRUD for job applications                           |
| `useJobSeeker`       | Fetch/update job seeker profile                     |
| `useAuthModal`       | Global open/close state for the login modal         |

---

## Database Schema

The full reference schema is in [`base.sql`](base.sql). Key tables:

| Table                | Description                                                    |
| -------------------- | -------------------------------------------------------------- |
| `users`              | Registered users (email, password hash, role, address)         |
| `roles`              | `ADMIN` / `USER`                                               |
| `companies`          | Employer profiles linked to a user (owner)                     |
| `industries`         | Industry categories (IT, Finance, Education, …)               |
| `job_announcements`  | Job postings with salary range, location, CV requirement, etc. |
| `contract_types`     | B2B, employment contract, mandate contract, internship, …     |
| `workload_types`     | Full-time, part-time, temporary, internship                    |
| `work_modes`         | Remote, on-site, hybrid                                        |
| `payment_types`      | Hourly, weekly, monthly                                        |
| `job_seekers`        | Applicant profiles (full_name, email, message)                 |
| `applications`       | Joins job_seekers → job_announcements (unique per pair)        |

> **Warning:** `spring.jpa.hibernate.ddl-auto=create-drop` drops and recreates all tables on every application restart. Change to `validate` or `update` before storing persistent data.

---

## API Reference

All endpoints are prefixed with `/api`. Protected endpoints require `Authorization: Bearer <token>`.

### Auth

| Method | Path              | Auth     | Description              |
| ------ | ----------------- | -------- | ------------------------ |
| POST   | `/auth/signup`    | Public   | Register a new user      |
| POST   | `/auth/login`     | Public   | Login, returns JWT token |

### Job Announcements

| Method | Path                     | Auth     | Description                          |
| ------ | ------------------------ | -------- | ------------------------------------ |
| GET    | `/job-announcements`     | Required | Paginated & filtered list            |
| GET    | `/job-announcements/{id}`| Required | Single announcement details          |
| POST   | `/job-announcements`     | Required | Create a new job announcement        |

### Reference Data (read-only)

`/industries`, `/contract-types`, `/work-modes`, `/workload-types`, `/payment-types` — all return lists used to populate filter dropdowns.

### Users / Profile

`/users`, `/companies`, `/job-seekers` — user and company profile management.

---

## Environment Variables

### Backend (`backend/.env`)

| Variable        | Description                          | Example                                   |
| --------------- | ------------------------------------ | ----------------------------------------- |
| `POSTGRES_URL`  | Full JDBC URL                        | `jdbc:postgresql://localhost:5432/nextjob`|
| `POSTGRES_USER` | Database username                    | `postgres`                                |
| `POSTGRES_PASSWORD` | Database password                | `secret`                                  |
| `POSTGRES_PORT` | Port exposed by Docker Compose       | `5432`                                    |

### Frontend (`frontend/.env`)

| Variable  | Description                  | Example                      |
| --------- | ---------------------------- | ---------------------------- |
| `API_URL`  | Base URL of the Spring Boot API | `http://localhost:8080`   |

---

## Available Scripts

### Backend

```bash
# Run in development
./mvnw spring-boot:run

# Build JAR
./mvnw package -DskipTests

# Run tests
./mvnw test
```

### Frontend

```bash
# Install dependencies
pnpm install

# Development server (http://localhost:3000)
pnpm dev

# Type-check & build for production
pnpm build

# Preview production build
pnpm preview

# Lint
pnpm lint
```

---

## Deployment

The project has no production deployment config yet (no `Dockerfile` for the frontend, no CI/CD pipeline). Below is a recommended approach.

### Backend (Docker)

```bash
cd backend
./mvnw package -DskipTests
docker build -t nextjob-api .
docker run -p 8080:8080 \
  -e POSTGRES_URL=jdbc:postgresql://<host>:5432/nextjob \
  -e POSTGRES_USER=<user> \
  -e POSTGRES_PASSWORD=<password> \
  nextjob-api
```

Before going to production:

1. Change `ddl-auto` from `create-drop` to `validate` in `application.properties`.
2. Move the JWT secret out of `application.properties` into an environment variable.
3. Update the CORS origin in `SecurityConfiguration` from `localhost:8080` to your production domain.

### Frontend

```bash
cd frontend
pnpm build
# Serve the .output/ directory with any static host (Vercel, Netlify, Nginx, etc.)
```

Set `API_URL` to the production backend URL at build time.

---

## Troubleshooting

### Port conflict on 8080 or 3000

Check and kill the process using the port:
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <pid> /F
```

### Database connection refused

1. Verify the Docker container is running: `docker ps`
2. Check `POSTGRES_URL`, `POSTGRES_USER`, `POSTGRES_PASSWORD` in `backend/.env`
3. Ensure the port in `POSTGRES_PORT` matches the mapped port: `docker compose ps`

### JWT errors (`401 Unauthorized`)

- Token expires after 1 hour. Log out and log in again to refresh it.
- Make sure `API_URL` in `frontend/.env` points to the correct backend host/port.

### Hibernate drops all data on restart

This is expected with `ddl-auto=create-drop`. Use `DataInitializer.java` to re-seed reference data, or change the strategy to `update` to persist data between restarts.

### Frontend `pnpm install` fails

Ensure you are using pnpm ≥ 9. The repo uses a `pnpm-workspace.yaml` in the frontend directory.
```bash
npm install -g pnpm@latest
pnpm install
```
