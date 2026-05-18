# Banking & Digital Wallet System

A production-style microservices backend inspired by modern fintech platforms such as PhonePe, PayPal, and Razorpay.

This project demonstrates how to build a secure, scalable, and auditable banking system using Java, Spring Boot, PostgreSQL, JWT authentication, and microservice architecture.

---

# 🚀 Features Implemented

## Authentication & Security

* User registration
* User login
* BCrypt password hashing
* JWT access tokens
* Refresh tokens
* Logout with token revocation
* Stateless authentication
* Spring Security

## Customer Management

* Create customer profile
* Retrieve customer profile

## Wallet Management

* Create wallet
* Retrieve wallet
* Credit wallet
* Debit wallet
* Insufficient balance validation
* Optimistic locking using `@Version`

## Ledger

* Immutable ledger entries
* Transaction history per wallet

## Transfers

* Transfer money between users
* Idempotency support
* Transfer status tracking (`PENDING`, `SUCCESS`, `FAILED`)

## Infrastructure

* Spring Cloud Config Server
* API Gateway routing
* OpenFeign inter-service communication
* Global exception handling

---

# 🏗️ Architecture Overview

```text
Client / Mobile App / Frontend
            |
            v
      API Gateway (8080)
            |
            +-------------------+
            |                   |
            v                   v
   Auth Service (8081)   Customer Service (8082)
            |
            v
       PostgreSQL (auth_db)

            |
            +-------------------+
            |                   |
            v                   v
 Wallet Service (8083)   Ledger Service (8084)
            |                   |
            v                   v
    PostgreSQL (wallet_db) PostgreSQL (ledger_db)

            |
            v
 Transfer Service (8085)
            |
            +-------> Wallet Service
            |
            +-------> Ledger Service
            |
            v
     PostgreSQL (transfer_db)

Config Server (8888)
```

---

# 🧰 Tech Stack

## Backend

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Spring Cloud Gateway
* Spring Cloud Config Server
* OpenFeign
* Lombok

## Database

* PostgreSQL

## Security

* JWT (JJWT)
* BCrypt

## Build Tool

* Maven

---

# 📦 Microservices

| Service          | Port | Responsibility                           |
| ---------------- | ---: | ---------------------------------------- |
| Config Server    | 8888 | Centralized configuration                |
| API Gateway      | 8080 | Routing and entry point                  |
| Auth Service     | 8081 | Registration, login, JWT, refresh tokens |
| Customer Service | 8082 | Customer profile management              |
| Wallet Service   | 8083 | Wallet balances and balance updates      |
| Ledger Service   | 8084 | Immutable financial entries              |
| Transfer Service | 8085 | Money transfer orchestration             |

---

# 🔐 Authentication Flow

## 1. Register

```http
POST /auth/register
```

Request:

```json
{
  "email": "suraj@example.com",
  "password": "secret123"
}
```

Flow:

1. Validate input.
2. Check if email already exists.
3. Hash password with BCrypt.
4. Save user in `users` table.

---

## 2. Login

```http
POST /auth/login
```

Request:

```json
{
  "email": "suraj@example.com",
  "password": "secret123"
}
```

Response:

```json
{
  "accessToken": "<jwt>",
  "refreshToken": "<uuid>"
}
```

Flow:

1. Find user by email.
2. Verify password using BCrypt.
3. Generate JWT access token.
4. Generate refresh token.
5. Store refresh token in database.
6. Return both tokens.

---

## 3. Access Protected APIs

```http
Authorization: Bearer <access-token>
```

Flow:

1. JWT filter extracts token.
2. Validate signature and expiration.
3. Extract user email.
4. Load user details.
5. Populate Spring Security context.

---

## 4. Refresh Token

```http
POST /auth/refresh
```

Request:

```json
{
  "refreshToken": "<uuid>"
}
```

Flow:

1. Validate refresh token.
2. Ensure not expired or revoked.
3. Generate new access token.
4. Return new JWT.

---

## 5. Logout

```http
POST /auth/logout
```

Flow:

1. Find refresh token.
2. Mark `revoked = true`.
3. Future refresh attempts fail.

---

# 👤 Customer Service Flow

## Create Customer Profile

```http
POST /customers
```

Request:

```json
{
  "userId": 1,
  "fullName": "Suraj Kunte",
  "phone": "9876543210",
  "dateOfBirth": "2002-01-15",
  "address": "Bengaluru"
}
```

Flow:

1. Validate request.
2. Ensure phone number is unique.
3. Save customer profile.

---

# 💰 Wallet Service Flow

## Create Wallet

```http
POST /wallets
```

Flow:

1. Check wallet does not already exist.
2. Create wallet with:

   * `balance = 0`
   * `currency = INR`
   * `status = ACTIVE`

---

## Credit Wallet

```http
POST /wallets/credit
```

Request:

```json
{
  "userId": 1,
  "amount": 1000.00
}
```

Flow:

1. Load wallet.
2. Add amount.
3. Save updated balance.

---

## Debit Wallet

```http
POST /wallets/debit
```

Flow:

1. Load wallet.
2. Check sufficient balance.
3. Subtract amount.
4. Save updated balance.

If insufficient funds:

```json
{
  "status": 400,
  "error": "Insufficient balance"
}
```

---

# 📒 Ledger Service Flow

## Create Ledger Entry

```http
POST /ledger
```

Request:

```json
{
  "walletId": 1,
  "entryType": "DEBIT",
  "amount": 500.00,
  "referenceId": "1",
  "description": "Transfer to user 2"
}
```

Flow:

1. Build immutable ledger entry.
2. Set `createdAt`.
3. Save to `ledger_entries` table.

---

# 🔁 Transfer Service Flow

## Initiate Transfer

```http
POST /transfers
```

Request:

```json
{
  "fromUserId": 1,
  "toUserId": 2,
  "amount": 500.00,
  "currency": "INR",
  "idempotencyKey": "txn-001"
}
```

---

## Detailed Execution Flow

### Step 1: Validate Request

* Sender and receiver must be different.
* Amount must be positive.

### Step 2: Idempotency Check

* Search `transfers` table by `idempotencyKey`.
* If found, return existing transfer.

### Step 3: Create Transfer Record

```text
status = PENDING
```

### Step 4: Debit Sender Wallet

Call Wallet Service:

```http
POST /wallets/debit
```

### Step 5: Credit Receiver Wallet

Call Wallet Service:

```http
POST /wallets/credit
```

### Step 6: Create Ledger Entries

1. Sender → DEBIT
2. Receiver → CREDIT

### Step 7: Mark Transfer SUCCESS

If any step fails:

* Mark transfer `FAILED`
* Save failure reason

---

# 🔄 Transfer Sequence Diagram

```text
Client
  |
  | POST /transfers
  v
Transfer Service
  |
  | save(PENDING)
  |
  +--> Wallet Service: debit(sender)
  |
  +--> Wallet Service: credit(receiver)
  |
  +--> Ledger Service: create DEBIT entry
  |
  +--> Ledger Service: create CREDIT entry
  |
  | update status = SUCCESS
  v
Client receives SUCCESS response
```

---

# 🗄️ Database Tables

## Auth Service

* users
* refresh_tokens

## Customer Service

* customers

## Wallet Service

* wallets

## Ledger Service

* ledger_entries

## Transfer Service

* transfers

---

# 🧠 Key Concepts Demonstrated

* ACID transactions (single database)
* Eventual consistency across services
* JWT authentication
* Refresh token lifecycle
* Optimistic locking
* Idempotency
* Immutable ledger
* Double-entry bookkeeping
* Microservice orchestration
* Global exception handling

---

# ⚠️ ACID Compliance Notes

### Fully ACID

* Registration
* Wallet credit/debit
* Ledger entry creation

### Not Globally ACID

* Cross-service transfers

Why?

Each service has its own database. A local `@Transactional` does not span multiple services.

Production systems solve this using:

* Saga pattern
* Transactional outbox
* Kafka
* Reconciliation jobs

---

# 📌 API Examples

## Login

```http
POST /auth/login
```

## Create Wallet

```http
POST /wallets
```

## Credit Wallet

```http
POST /wallets/credit
```

## Transfer Money

```http
POST /transfers
```

## Get Ledger Entries

```http
GET /ledger/{walletId}
```

---

# 🧪 How to Run the Project

## 1. Start PostgreSQL

Create databases:

* auth_db
* customer_db
* wallet_db
* ledger_db
* transfer_db

## 2. Start Services in Order

1. Config Server
2. API Gateway
3. Auth Service
4. Customer Service
5. Wallet Service
6. Ledger Service
7. Transfer Service

## 3. Test APIs via Postman

Use the API Gateway:

* `http://localhost:8080/auth/login`
* `http://localhost:8080/wallets`
* `http://localhost:8080/transfers`

---

# 📈 Future Enhancements

* Docker Compose
* Kafka
* Saga Pattern
* Transactional Outbox
* Fraud Detection Service
* Limits Service
* Notifications Service
* Reconciliation Jobs
* Kubernetes Deployment
* CI/CD
* Monitoring with Prometheus/Grafana

---

# 🏆 Resume Highlights

This project demonstrates:

* Java 21
* Spring Boot
* Spring Security
* JWT
* PostgreSQL
* Microservices
* OpenFeign
* API Gateway
* Config Server
* Idempotency
* ACID transactions
* Optimistic locking
* Ledger-based accounting

---

# 👨‍💻 Author

**Suraj B Kunte**

Built as a production-grade fintech backend to master enterprise Java, distributed systems, and microservice architecture.
