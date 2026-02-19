# CheapPharmacy Backend

## Description
CheapPharmacy is a backend REST API that helps users find the most affordable medicines
and locate nearby pharmacies where selected drugs are available.
This project was awarded **Best Project Award** at the university for its social impact
and practical problem-solving approach.

---

## Tech Stack
- Java 17
- Spring Boot
- Spring Security (JWT)
- PostgreSQL
- Hibernate / JPA
- Maven
- Swagger (OpenAPI)

---

## Features
- User authentication and authorization (JWT)
- Role-based access (ADMIN / USER)
- Search medicines by name and price
- Find nearby pharmacies with availability
- CRUD operations for medicines and pharmacies
- Input validation and global exception handling

---

## Architecture
Controller → Service → Repository  
DTO pattern is used for data transfer.

---

## API Documentation
Swagger UI is available after running the application locally:

http://localhost:8080/swagger-ui.html

---

## How to Run
```bash
mvn clean install
mvn spring-boot:run
