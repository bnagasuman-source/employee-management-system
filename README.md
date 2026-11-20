# Employee Management System

A full-stack web application to manage employee data, built with **Java, Spring Boot, MySQL, HTML, CSS and JavaScript**.

## Features
- Secure admin login (Spring Security, BCrypt-hashed passwords, session based)
- Add, update, delete and search employee records
- Department management (add / delete, protected when employees still belong to it)
- Salary management with payroll total and average salary dashboard
- REST APIs integrated with MySQL using Spring Data JPA
- Server-side validation with clear error messages

## Tech Stack
| Layer | Technology |
|-------|-----------|
| Backend | Java 17, Spring Boot 3, Spring Security, Spring Data JPA |
| Database | MySQL 8 |
| Frontend | HTML, CSS, JavaScript (fetch API) |
| Build | Maven |

## Project Structure
```
src/main/java/com/ems
 ├── config        SecurityConfig, DataInitializer (seeds admin + departments)
 ├── controller    REST controllers + global exception handler
 ├── dto           EmployeeDto, StatsDto
 ├── model         Employee, Department, AdminUser
 ├── repository    Spring Data JPA repositories
 └── service       Business logic
src/main/resources
 ├── application.properties
 └── static        login.html, index.html, css/, js/
```

## Getting Started

### Prerequisites
- JDK 17+
- Maven 3.8+
- MySQL 8 running locally

### Run
1. Clone the repo
   ```bash
   git clone https://github.com/<your-username>/employee-management-system.git
   cd employee-management-system
   ```
2. Set your MySQL credentials (or edit `application.properties`)
   ```bash
   export DB_USERNAME=root
   export DB_PASSWORD=your_password
   ```
   The database `ems_db` is created automatically.
3. Start the app
   ```bash
   mvn spring-boot:run
   ```
4. Open http://localhost:8080 and log in with the default admin:
   - Username: `admin`
   - Password: `admin123` (change it with `ADMIN_USERNAME` / `ADMIN_PASSWORD` env variables)

## REST API
All endpoints require an authenticated session.

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/employees` | List employees (`?search=` to filter) |
| GET | `/api/employees/{id}` | Get one employee |
| GET | `/api/employees/stats` | Headcount, payroll, average salary |
| POST | `/api/employees` | Add employee |
| PUT | `/api/employees/{id}` | Update employee |
| DELETE | `/api/employees/{id}` | Delete employee |
| GET | `/api/departments` | List departments |
| POST | `/api/departments` | Add department |
| DELETE | `/api/departments/{id}` | Delete department |

## Notes
- CSRF protection is disabled for simplicity; enable it before deploying to production.
- Change the default admin password before deploying.
