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


## End-to-end flow

1. Register and authenticate a user in `auth-service`.
2. Create customer profile in `customer-service`.
3. Submit and review KYC in `kyc-service`.
4. Create wallets in `wallet-service`.
5. Perform wallet transfers in `transfer-service`.
6. Record or query transaction, fraud, notification, audit, ledger, and reconciliation data through their services or via `api-gateway`.

Microservices:
1. auth-service
User registration and login

2. customer-service
Stores customer profile details

4. kyc-service
KYC submission and approval workflow

6. wallet-service
Wallet creation
Balance management
Fund reservation and release

8. transfer-service
Orchestrates transfers between wallets

10. transaction-service
Tracks transaction states (PENDING, SUCCESS, FAILED)

12. ledger-service
Double-entry bookkeeping
Immutable financial records

14. fraud-service
Daily transaction limits

15. notification-service
Sends email/SMS/in-app notifications

17. reconciliation-service
Detects mismatches between wallet balances and ledger totals

19. api-gateway
Single entry point for clients

21. config-server
Centralized configuration management
