# 🚀 Projectea API

---

## 📦 Requirements
[![Java](https://img.shields.io/badge/Java-17%2B-red?logo=java)](https://adoptium.net/)
[![Maven](https://img.shields.io/badge/Maven-3.8%2B-brightgreen?logo=apache-maven)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8%2B-blue?logo=mysql)](https://www.mysql.com/)
[![Docker](https://img.shields.io/badge/Docker-optional-2496ED?logo=docker)](https://www.docker.com/)

- JDK installed and on PATH
- MySQL running locally (or via Docker)
- Optional: Docker Desktop to run the DB from compose

---

## ⚙️ Configuration

- Profile: `local` (default during development)
- Config file: `src/main/resources/application-local.yml`
- JWT secret: set in your `.yml` (or env var `JWT_SIGNING_KEY`)

Optional DB via compose:

```bash
docker compose -f compose.local.yml up -d
```

---

## ▶️ Run the API
For bash run:
```bash
# from projectea-api/
mvn clean spring-boot:run -Dspring-boot.run.profiles=local
```

In powershell run:
```powershell
mvn spring-boot:run "-Dspring-boot.run.profiles=local"
```

The app seeds demo data (users, categories, items, item units, a sample reservation) via `DataLoader`.

---

## 🔑 Auth

- Login: `POST /api/v1/auth/login` → returns JWT
- Register: `POST /api/v1/auth/register`
- Use Bearer JWT in `Authorization` header
- Expired/invalid tokens return 401/403

---

## 🔌 Main Endpoints

- Items:
  - `GET /api/v1/items` (ItemDto: thumbnailUrl, images[], categoryId, unit summaries)
  - `GET /api/v1/items?category=Lighting`
- Categories:
  - `GET /api/v1/categories`
- Reservations:
  - `GET /api/v1/reservations`
  - `GET /api/v1/reservations/{id}`
  - `GET /api/v1/reservations/user/{userId}`
  - `GET /api/v1/reservations/user/me` (uses authenticated user)
  - `POST /api/v1/reservations` (ReservationCreateDto with unit UUIDs)
  - `PUT /api/v1/reservations/{id}`
  - `DELETE /api/v1/reservations/{id}`

All controllers return DTOs via ModelMapper-powered adapters.

---

## 📁 Useful commands

```bash
# run db via docker (optional)
docker compose -f compose.local.yml up -d

# run api (local profile)
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

---

## 🔐 401/403 Handling

The frontend auto-logs out and redirects to `/auth` if a request gets 401/403.

---

## 📎 Postman/Insomnia

Set `{{baseUrl}} = http://localhost:8080` and include the Bearer token after logging in.