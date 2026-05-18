# Banking Wallet System

Spring Boot microservices for a banking wallet platform.

## Services

| Service | Port | API base |
| --- | ---: | --- |
| api-gateway | 8080 | gateway |
| auth-service | 8081 | `/auth` |
| customer-service | 8082 | `/customers` |
| wallet-service | 8083 | `/wallets` |
| ledger-service | 8084 | `/ledger` |
| transfer-service | 8085 | `/transfers` |
| kyc-service | 8086 | `/kyc` |
| transaction-service | 8087 | `/transactions` |
| fraud-service | 8088 | `/fraud` |
| notification-service | 8089 | `/notifications` |
| audit-service | 8090 | `/audit` |
| reconciliation-service | 8091 | `/reconciliation` |
| config-server | 8888 | config |
| service-registry | 8761 | Eureka |

## Run

Build each service jar, then start the stack:

```bash
mvn clean package
docker compose up --build
```

If Maven is not installed globally, use the checked-in wrapper from any existing service:

```powershell
.\api-gateway\mvnw.cmd -f pom.xml clean package
docker compose up --build
```

## End-to-end flow

1. Register and authenticate a user in `auth-service`.
2. Create customer profile in `customer-service`.
3. Submit and review KYC in `kyc-service`.
4. Create wallets in `wallet-service`.
5. Perform wallet transfers in `transfer-service`.
6. Record or query transaction, fraud, notification, audit, ledger, and reconciliation data through their services or via `api-gateway`.
