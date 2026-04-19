# ATM Transaction App (Spring Boot)

A learning-oriented **REST API** for basic banking-style operations: create accounts, deposit, withdraw, transfer between accounts, and check balances. Accounts are persisted in **MySQL** via **Spring Data JPA**, and API access is protected with **OAuth2 Resource Server** JWT validation against **Keycloak**.

## Features

- Create accounts with a unique account number, holder name, type (`SAVINGS` or `CHECKING`), and initial balance
- Deposit and withdraw with validation for positive amounts and sufficient funds on withdrawal
- Transfer funds between two accounts in one transaction
- Look up balance by account number
- Centralized error responses (`404`, `409`, `400`, `500`) via a global exception handler

## Tech stack


| Area                 | Technology                                                            |
| -------------------- | --------------------------------------------------------------------- |
| Runtime              | Java **21**                                                           |
| Framework            | Spring Boot (parent and dependency versions are defined in `pom.xml`) |
| API                  | Spring Web, REST                                                      |
| Persistence          | Spring Data JPA, Hibernate, **MySQL 8**                               |
| Security             | Spring Security, OAuth2 **Resource Server** (JWT)                     |
| Identity (local dev) | **Keycloak** (Docker)                                                 |
| Build                | Maven                                                                 |


Optional: **H2** is on the classpath (e.g. for tooling/tests); the default configuration targets MySQL.

## Repository layout

```
ATM-Project-with-SpringBoot/
└── atm-transaction-app/          # Spring Boot application (Maven module)
    ├── docker-compose.yml        # MySQL + Keycloak for local development
    ├── pom.xml
    └── src/
```

## Prerequisites

- **JDK 21**
- **Docker** and Docker Compose (for MySQL and Keycloak)
- **Maven** 3.9+ (or use the included Maven Wrapper if present)

## Local setup

### 1. Start MySQL and Keycloak

From the application directory:

```bash
cd atm-transaction-app
docker compose up -d
```

This starts:

- **MySQL** on host port `3306`, database `atm_db`, root password `mysql` (as defined in `docker-compose.yml`)
- **Keycloak** on **[http://localhost:8081](http://localhost:8081)** (admin user `admin` / `admin` in the compose file)

Wait until both services report healthy in `docker compose ps`.

### 2. Configure Keycloak for JWTs

The app expects this issuer (see `application.properties`):

`http://localhost:8081/realms/atm`

In the Keycloak admin console:

1. Create a realm named `**atm`**.
2. Create a **client** your token requests will use (e.g. a public client for SPA/Postman or a confidential client for machine clients), and ensure tokens are suitable for a **resource server** (Bearer JWT).
3. Create at least one **user** (and credentials) if you use the password grant or browser login, depending on your client setup.

Exact client settings depend on how you obtain tokens; the Spring app only validates JWTs issued by that realm.

### 3. Run the application

```bash
cd atm-transaction-app
./mvnw spring-boot:run
```

If you do not use the wrapper:

```bash
mvn -f atm-transaction-app/pom.xml spring-boot:run
```

By default the API listens on **[http://localhost:8080](http://localhost:8080)**.

### 4. Configuration (`application.properties`)


| Property                                               | Purpose                                                 |
| ------------------------------------------------------ | ------------------------------------------------------- |
| `spring.datasource.url`                                | JDBC URL (default `jdbc:mysql://localhost:3306/atm_db`) |
| `spring.datasource.username` / `password`              | DB credentials                                          |
| `spring.jpa.hibernate.ddl-auto`                        | `update` — schema evolves with entities                 |
| `spring.security.oauth2.resourceserver.jwt.issuer-uri` | Keycloak realm issuer URL                               |


Adjust these if you change ports, hostnames, or realm names.

## API overview

Base path: `**/api/accounts`**

All endpoints except those you explicitly permit in security require a **valid Bearer JWT** (`Authorization: Bearer <token>`).


| Method | Path                                    | Description                        |
| ------ | --------------------------------------- | ---------------------------------- |
| `GET`  | `/api/accounts/home`                    | Simple welcome string              |
| `POST` | `/api/accounts/create`                  | Create an account                  |
| `PUT`  | `/api/accounts/deposit`                 | Deposit to an account              |
| `PUT`  | `/api/accounts/withdraw`                | Withdraw from an account           |
| `PUT`  | `/api/accounts/transfer`                | Transfer between accounts          |
| `GET`  | `/api/accounts/balance/{accountNumber}` | Return balance text for an account |


### Example JSON bodies

**Create account** — `POST /api/accounts/create`

```json
{
  "accountNumber": "10000001",
  "accountHolderName": "Ada Lovelace",
  "accountType": "SAVINGS",
  "balance": "100.00"
}
```

`accountType` is case-insensitive in the service (`SAVINGS` or `CHECKING`).

**Deposit / withdraw** — `PUT /api/accounts/deposit` or `.../withdraw`

```json
{
  "accountNumber": "10000001",
  "accountType": "SAVINGS",
  "amount": "25.50"
}
```

**Transfer** — `PUT /api/accounts/transfer`

```json
{
  "senderAccountNumber": "10000001",
  "receiverAccountNumber": "10000002",
  "senderAccountType": "SAVINGS",
  "receiverAccountType": "CHECKING",
  "amount": "10.00"
}
```

## Error responses

Business and validation errors return JSON shaped like:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Account not found.",
  "timestamp": "2026-04-19T12:00:00Z"
}
```

Typical mappings include duplicate account numbers (`409`), missing accounts (`404`), insufficient funds (`400`), and invalid amounts (`400`).

## Tests

```bash
cd atm-transaction-app
./mvnw test
```

Service-layer tests use **JUnit 5** and **Mockito**.

## Security note

Default passwords and URLs in `docker-compose.yml` and `application.properties` are for **local development only**. Do not use them in production without hardening (secrets management, TLS, least-privilege DB users, and a properly configured identity provider).

## License

Specify your license here (for example MIT or Apache-2.0) once you choose one for the repository.