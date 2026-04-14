# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run

```bash
# Compile
mvn compile

# Run (entry point is src/main/java/Main.java)
mvn exec:java -Dexec.mainClass="Main"

# Package
mvn package
```

Requires Java 25. There are no external dependencies and no tests at this time.

## Architecture

This is a CLI-only gym management system with **no database** — all data lives in a `HashMap<UUID, User>` for the duration of a single session.

### Request/Response flow

`Main` → `UserService` → `UserRepository` (interface) → `InMemoryUserRepository`

1. `Main.java` handles the top-level loop (Register / Login / Exit), collects input into a `RegistrationRequest`, and delegates to `UserService`.
2. After login, `Main` routes to `AdminMenu`, `CoachMenu`, or `ClientMenu`, passing the shared `UserService` (and `UserRepository` where needed).
3. `UserService` contains all business logic: `login`, `register`, `deleteUser`, `getAllUsersByRole`, `updateProfile`.
4. `InMemoryUserRepository` is the only implementation of `UserRepository`. It stores users by `UUID` key; `findByEmail` and `findByPhoneNumber` iterate over values.

### Domain model

- `User` (abstract) ← `Admin`, `Coach`, `Client`
- `Client` holds a `WorkoutWeek` (list of `WorkoutDay`s, each with a list of exercise strings).
- `Client.assignedCoach` is stored as the **coach's UUID** (set by admins via `AdminMenu`).
- `RegistrationRequest` is a flat DTO used for both registration and profile updates — fields are role-conditional (e.g., `adminLevel` only matters for `ROLE.ADMIN`).

### Role-specific menus

| Role  | Menu class   | Key capabilities |
|-------|-------------|-----------------|
| Admin | `AdminMenu` | View/add/delete users, assign coach to client, change status |
| Coach | `CoachMenu` | View assigned clients, add exercises, create new workout days |
| Client | `ClientMenu` | View profile, view workout plan, update password/weight |

`CoachMenu.viewClients()` filters by `coach.getUserId().equals(client.getAssignedCoach())`.

### Source layout

All Java sources are flat under `src/main/java/` (no package, except the unused `org/example/Main.java` template which can be ignored).
