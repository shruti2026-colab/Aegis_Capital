# Aegis Capital – Secure Banking Backend System

## Project Overview

The Aegis Capital Banking System is a Spring Boot–based backend application that provides secure banking operations such as user registration, login, account creation, deposit, withdrawal, fund transfer, transaction history, admin management, and role-based authorization.

The application is designed using a layered architecture consisting of Controller, Service, Repository, and Entity layers, with additional components such as DTOs, Global Exception Handling, JWT-based security, and Optimistic Locking for concurrency control.

---

## Application Flow

1. A user registers and logs in to receive a JWT token.
2. The client sends the token in the Authorization header for secured API requests.
3. JwtFilter intercepts the request and validates the token.
4. User details are extracted and authentication is set in the SecurityContext.
5. The request reaches the controller.
6. Controller calls the service layer.
7. Service layer processes business logic and interacts with repositories.
8. Data is fetched/updated in the database.
9. Global exception handler handles errors and returns proper responses.
10. Admin users can access admin APIs.


<img width="2161" height="1546" alt="mermaid-diagram" src="https://github.com/user-attachments/assets/84866a4a-9e3e-4c1c-87fa-5d8168823ff2" />



---


## Features

### User Features

- Register new user  
- Login using email/password or account ID  
- Logout  
- Reset password  
- Open bank account  
- Deposit money  
- Withdraw money  
- Check account balance  
- View account details  
- Transfer funds  
- View transaction history  

### Admin Features

- View all users  
- View all accounts  
- View all transactions  
- Promote user to admin  
- Deactivate account (soft delete)  

---


## Modules

### Authentication & Authorization

- User Registration  
- Login using Email/Password  
- JWT Token Generation  
- Role-Based Access Control (RBAC)  
- Stateless Authentication  

### User Module

- Unique user ID generation  
- Secure password encryption (BCrypt)  
- One user → Multiple accounts  

### Account Module

- Open account for registered users  
- Deposit money  
- Withdraw money  
- Check account balance  

### Transaction Module

- Transfer funds between accounts  
- Maintain transaction history  
- Store transaction metadata:
  - sender  
  - receiver  
  - timestamp  
  - status  

---

## Exception Handling

- Centralized exception handling  
- Custom exceptions:
  - AccountNotFoundException  
  - InsufficientBalanceException
  - ConcurrentTransactionException
  - IncorrectPasswordException
  - IncorrectPinException
  - InvalidUserException
  - NegativeNumberException
  - Invalid User Exception
  - SimilarEmailException
- Clean API error responses  

---

## Concurrency Handling

- Optimistic locking using `@Version`  
- Prevents lost updates during concurrent transactions  

---

## Architecture

The project follows a layered architecture:

Client (Postman / Frontend)  
→ Controller Layer (API Endpoints)  
→ Service Layer (Business Logic)  
→ Repository Layer (Database Access)  
→ Database (MySQL / H2)  

---

## Layers Explained

### 1. Controller Layer
- Handles HTTP requests  
- Maps endpoints  
- Converts request to DTO  

### 2. Service Layer
- Core business logic  
- Transaction handling  
- Validation  
- Authorization checks  

### 3. Repository Layer
- Interface to database  
- Uses Spring Data JPA  
- Performs CRUD operations  

### 4. Entity Layer
- Represents database tables  
- Defines relationships  

### 5. DTO Layer
- Used for request/response transfer  
- Prevents exposing internal entities  

---

## Tech Stack

- Backend: Java, Spring Boot  
- Database: MySQL  
- ORM: Spring Data JPA (Hibernate)  
- Security: Spring Security + JWT  
- Build Tool: Maven  
- Testing: JUnit 5, Mockito  
- Lombok: For boilerplate reduction  

---

## Security Design

### Authentication Flow

1. User logs in  
2. Server validates credentials  
3. JWT token is generated  
4. Client stores the token  
5. Client sends token in Authorization header  

### JWT Structure

- Subject: User email  
- Role: USER / ADMIN  
- Issued time  
- Expiration time  

### Request Security Flow

Incoming Request  
→ JwtFilter  
→ Validate Token  
→ Extract Email & Role  
→ Set Authentication in SecurityContext  
→ Allow Request  

### Password Security

- Uses BCryptPasswordEncoder  
- Passwords stored in encrypted form  

---

## Database Design

### User Table

| Field | Description |
|------|------------|
| id | Primary key |
| userId | Unique business ID |
| name | User name |
| email | Unique email |
| password | Encrypted password |
| role | USER / ADMIN |

### Account Table

| Field | Description |
|------|------------|
| id | Primary key |
| accountNumber | Unique account ID |
| balance | Account balance |
| version | Optimistic locking |
| user_id | Foreign key |

### Transaction Table

| Field | Description |
|------|------------|
| id | Primary key |
| transactionId | Unique transaction ID |
| fromAccount | Sender |
| toAccount | Receiver |
| amount | Transaction amount |
| status | SUCCESS / FAILED |
| timestamp | Date and time |

---


## End-to-End Request Flow

### Example: Fund Transfer

1. Client sends transfer request  
2. Controller receives request DTO  
3. Service layer:
   - Fetch sender and receiver accounts  
   - Check balance  
   - Deduct and add funds  
   - Save updated accounts  
   - Create transaction record  
4. Return success response  

---

## Transaction Management

Implemented using `@Transactional`

| Property | Meaning |
|--------|--------|
| Atomicity | All operations succeed or fail together |
| Consistency | Data remains valid |
| Isolation | Transactions don’t interfere |
| Durability | Data persists |

---

Prevents:
- Lost updates  
- Race conditions  

---

## API Endpoints

### Auth APIs

| Method | Endpoint | Description |
|------|--------|-------------|
| POST | /api/auth/register | Register user |
| POST | /api/auth/login | Login |
| POST | /api/auth/loginUsingAccountId | Login via account |
| GET | /api/auth/{userId}/logout | Logout |
| POST | /api/auth/resetPassword | Reset password |

---

### Account APIs

| Method | Endpoint | Description |
|------|--------|-------------|
| POST | /api/accounts/deposit | Deposit money |
| POST | /api/accounts/withdraw | Withdraw money |
| GET | /api/accounts/{accountNumber}/balance | Get balance |
| POST | /api/accounts/openAccount | Open account |
| GET | /api/accounts/{accountNumber}/getdetails | Account details |
| GET | /api/accounts/{userId}/details | User accounts |

---

### Transaction APIs

| Method | Endpoint | Description |
|------|--------|-------------|
| POST | /api/accounts/transfer | Transfer funds |
| GET | /api/accounts/transactions/{accountNumber} | Transaction history |

---

### Admin APIs

| Method | Endpoint | Description |
|------|--------|-------------|
| GET | /api/admin/users | Get all users |
| GET | /api/admin/accounts | Get all accounts |
| GET | /api/admin/transactions | Get all transactions |
| DELETE | /api/admin/account/{accountNumber} | Deactivate account |
| PUT | /api/admin/promote/{email} | Promote user |

---

## Unit Testing

### Testing Strategy

- Controller Layer: `@WebMvcTest` + MockMvc  
- Service Layer: Mockito (`@Mock`, `@InjectMocks`)  
- Repository Layer: `@DataJpaTest` with H2  

---

## Future Enhancements

- Refresh Tokens  
- Pagination for transactions  
- Redis caching  
- Rate limiting  
- Microservices architecture  
- Docker deployment  
- CI/CD pipeline  
- Monitoring (Prometheus, Grafana)  

---
