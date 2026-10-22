# OrbitalNexus API
[![Kotlin](https://img.shields.io/badge/Kotlin-2.4.21-7F52FF)](https://kotlinlang.org/)
[![Ktor](https://img.shields.io/badge/Ktor-3.5-087CFA)](https://ktor.io/)
[![Koin](https://img.shields.io/badge/Koin-4.2.1-purple)](https://insert-koin.io/)
[![JUnit 6](https://img.shields.io/badge/JUnit-6-25A162?style=flat&logo=junit5&logoColor=white)](https://junit.org/)

OrbitalNexus API is a REST API built with Kotlin, Ktor, Exposed, Koin and PostgreSQL for managing planets, missions 
and astronauts in a fictional space exploration system.

* [Technologies Used](#technologies-used)
* [Features](#features)
* [Running the Project](#running-the-project)
* [Testing](#testing)
* [API Documentation](#api-documentation)
* [API Endpoints](#api-endpoints)
* [Example Responses](#example-responses)
* [Localization](#localization)
* [Project Structure](#project-structure)
* [Contact](#contact)

## Technologies Used

| Category | Technology | Why |
|----------|------------|-----|
| Language | [![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat\&logo=kotlin\&logoColor=white)](https://kotlinlang.org/) | Main development language (my favorite 💜)
| Framework | [![Ktor](https://img.shields.io/badge/Ktor-087CFA?style=flat\&logo=ktor\&logoColor=white)](https://ktor.io/) | Backend framework for building REST APIs
| SQL Framework | [![Exposed](https://img.shields.io/badge/Exposed-FF6B6B?style=flat)](https://www.jetbrains.com/exposed/) | SQL framework used for database access and queries
| Database | [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=flat&logo=postgresql&logoColor=white)](https://www.postgresql.org/) | Primary relational database used for persistent data storage
| Database Migrations | [![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=flat&logo=flyway&logoColor=white)](https://documentation.red-gate.com/flyway) | Versioned database schema and seed data
| Testing | [![JUnit 6](https://img.shields.io/badge/JUnit-6-25A162?style=flat&logo=junit5&logoColor=white)](https://junit.org/) [![MockK](https://img.shields.io/badge/MockK-7F52FF?style=flat)](https://mockk.io/) | Unit testing and mocking dependencies |
| API Documentation | [![OpenAPI](https://img.shields.io/badge/OpenAPI-3.0-6BA539?style=flat&logo=swagger&logoColor=white)](https://swagger.io/specification/) | API specification standard
| Documentation UI | [![Swagger UI](https://img.shields.io/badge/Swagger_UI-85EA2D?style=flat&logo=swagger&logoColor=black)](https://swagger.io/tools/swagger-ui/) | Interactive API documentation
| Dependency Injection | [![Koin](https://img.shields.io/badge/Koin-7F52FF?style=flat&logo=kotlin&logoColor=white)](https://insert-koin.io/) | Dependency injection framework
| Serialization | [![Kotlinx Serialization](https://img.shields.io/badge/Kotlinx_Serialization-7F52FF?style=flat)](https://kotlinlang.org/docs/serialization.html) | JSON serialization and deserialization

## Features

### Planets
* List all planets
* Find planet by ID
* List missions by planet
* Multilingual planet descriptions

### Missions
* List all missions
* Find mission by ID
* Mission objectives and descriptions
* Multilingual mission content
* Retrieve mission planet

### Astronauts
* List all astronauts
* Find astronaut by ID
* List astronauts by mission
* Provide astronaut profile image URLs

### Platform
* Locale-based API responses
* Automatic fallback to default locale
* Serve static image assets
* Versioned database schema and seed data with Flyway.
* Interactive API documentation with Swagger UI

## Running the Project

Clone the repository:

```bash
git clone https://github.com/gblrodrigues/OrbitalNexus-api.git
```

Navigate to the project folder:

```bash
cd OrbitalNexus-api
```

Run the application:

```bash
1. Start PostgreSQL
docker compose up -d

2. Run the application
./gradlew run
```

> Flyway automatically applies all database migrations during application startup.

The server will start at:

```text
http://localhost:8080
```

## Testing

Unit tests for `MissionServiceImpl`, using JUnit 6 and MockK.

The tests cover:
* Retrieving missions by ID and listing missions.
* Falling back to the default locale (`en-US`).
* Filtering missions by planet.
* Retrieving astronauts and planets associated with missions.
* Handling missing missions, missing planets and empty results.

Run the test suite:

```bash
./gradlew test
```

## API Documentation

After starting the application, the interactive API documentation is available at:

```text
http://localhost:8080/swagger
```
The API specification follows the OpenAPI 3.0 standard and is served through Swagger UI, allowing every endpoint to be explored and tested directly from the browser.

## API Endpoints

### Planets
| Method | Endpoint | Description |
|----------|------------|-----|
| GET | /v1/planets | List all planets
| GET | /v1/planets/{id} | Find planet by ID |
| GET | /v1/planets/{id}/missions | List missions by planet |

### Missions
| Method | Endpoint | Description |
|----------|------------|-----|
| GET | /v1/missions | List all missions
| GET | /v1/missions/{id} | Find mission by ID
| GET | /v1/missions/{id}/astronauts | List astronauts by mission
| GET | /v1/missions/{id}/planet | Retrieve mission planet

### Astronauts
| Method | Endpoint | Description |
|----------|------------|-----|
| GET | /v1/astronauts | List all astronauts
| GET | /v1/astronauts/{id} | Find astronaut by ID

### Localization Examples

| Endpoint | Description |
|----------|------------|
| GET /v1/planets?lang=pt-BR | Returns planets in Portuguese
| GET /v1/planets/1?lang=es-ES | Returns a planet in Spanish
| GET /v1/missions?lang=pt-BR | Returns missions in Portuguese
| GET /v1/missions/1?lang=es-ES | Returns a mission in Spanish

## Example Responses

GET /v1/missions/1

```json
{
  "id": 1,
  "name": "Aurora VII",
  "missionObjective": "Survey Kepler Prime and identify suitable landing zones for future expeditions.",
  "description": "The first manned mission to Kepler Prime, focused on planetary mapping and environmental analysis.",
  "planetId": 1,
  "launchYear": 2042
}
```

**GET /v1/missions/1/astronauts**

```json
[
  {
    "id": 1,
    "name": "Luna Carter",
    "nationality": "Terran",
    "missionId": 1,
    "image": "/assets/astronauts/Luna_Carter_image_profile.png"
  }
]
```

**GET /v1/missions/1/planet**

```json
{
  "id": 1,
  "name": "Kepler Prime",
  "description": "Rocky planet located in the Vega sector."
}
```

## Localization
All planet and mission endpoints support localization through the `lang` query parameter.
> If a translation is not available for the requested locale, the API automatically falls back to the default locale (`en-US`).

Supported locales:
* en-US (default)
* pt-BR
* es-ES

**GET /v1/missions/1?lang=es-ES**
```json
{
  "id": 1,
  "name": "Aurora VII",
  "missionObjective": "Explorar Kepler Prime e identificar zonas de aterrizaje adecuadas para futuras expediciones.",
  "description": "La primera misión tripulada a Kepler Prime, centrada en la cartografía planetaria y el análisis ambiental.",
  "planetId": 1,
  "launchYear": 2042
}
```

## Project Structure

```text
src/
├── main/
│   ├── kotlin/com/gblrod/orbitalnexus/
│   │   ├── application/
│   │   │   └── Application.kt
│   │   ├── database/
│   │   │   ├── mapper/
│   │   │   ├── table/
│   │   │   ├── DatabaseConfig.kt
│   │   │   ├── DatabaseFactory.kt
│   │   │   └── FlywayFactory.kt
│   │   ├── di/
│   │   │   └── AppModule.kt
│   │   ├── model/
│   │   │   ├── astronaut/
│   │   │   ├── error/
│   │   │   ├── mission/
│   │   │   └── planet/
│   │   ├── plugins/
│   │   │   ├── Database.kt
│   │   │   ├── Koin.kt
│   │   │   ├── Routing.kt
│   │   │   ├── Serialization.kt
│   │   │   ├── StatusPages.kt
│   │   │   └── Swagger.kt
│   │   ├── repository/
│   │   │   ├── impl/
│   │   │   └── interfaces/
│   │   ├── routes/
│   │   │   ├── AstronautRoutes.kt
│   │   │   ├── MissionRoutes.kt
│   │   │   └── PlanetRoutes.kt
│   │   ├── service/
│   │   │   ├── impl/
│   │   │   └── interfaces/
│   │   └── util/
│   │       └── Locales.kt
│   └── resources/
│       ├── api/
│       │   └── openapi.yaml
│       ├── assets/
│       └── db/
│           └── migration/
└── test/
    └── kotlin/com/gblrod/orbitalnexus/
        └── service/
            └── impl/
                └── MissionServiceImplTest.kt

```

## Architecture

```text
Client Request
      ↓
Routes
      ↓
Services
      ↓
Repositories
      ↓
Exposed
      ↓
PostgreSQL

Database schema managed by Flyway
Dependencies managed by Koin
```

## Contact
<a href="https://www.linkedin.com/in/gblrodrigues">
  <img src="https://img.shields.io/badge/LinkedIn-100%2B-0077B5?style=flat&logo=linkedin&logoColor=white" alt="My LinkedIn Profile"/>
</a>
