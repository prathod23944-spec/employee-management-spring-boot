# Employee Management System

A full-stack Employee Management System built using Java, Spring Boot, MySQL, HTML, CSS, and JavaScript. This project provides REST APIs and a web-based interface to perform employee management operations.

## Features

### Employee Management
- Create employee records
- View all employees
- Retrieve employee details by ID
- Update employee information
- Delete employee records

### Backend
- RESTful API development using Spring Boot
- Layered architecture with Controller, Service, and Repository components
- Spring Data JPA and Hibernate for database operations
- MySQL database integration
- Input validation
- Centralized exception handling
- Application logging
- Standardized error responses using `ApiError`
- Spring Boot Actuator health and metrics endpoints

### Frontend
- HTML5 for page structure
- CSS3 for styling
- JavaScript for user interactions and API communication
- Frontend resources served from the Spring Boot static resources directory

### Testing and Monitoring
- JUnit 5 unit tests
- Mockito for mocking repository dependencies
- Postman collection for REST API testing
- Spring Boot Actuator for application health and metrics

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Backend programming language |
| Spring Boot 4 | Backend application framework |
| Spring Web MVC | REST API development |
| Spring Data JPA | Database access |
| Hibernate | Object-relational mapping |
| MySQL | Relational database |
| HTML5 | Frontend structure |
| CSS3 | Frontend styling |
| JavaScript | Frontend functionality |
| Maven | Build and dependency management |
| JUnit 5 | Unit testing |
| Mockito | Mocking dependencies |
| Postman | API testing |
| Spring Boot Actuator | Health and metrics monitoring |

## Project Structure

```text
employee-management/
├── .mvn/
│   └── wrapper/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/example/employee_management/
│   │   │   │   └── EmployeeManagementApplication.java
│   │   │   ├── controller/
│   │   │   │   └── EmployeeController.java
│   │   │   ├── entity/
│   │   │   │   └── Employee.java
│   │   │   ├── exception/
│   │   │   │   ├── ApiError.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── repository/
│   │   │   │   └── EmployeeRepository.java
│   │   │   ├── service/
│   │   │   │   └── EmployeeService.java
│   │   │   └── com/example/employee_management/
│   │   │       └── TestController.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── index.html
│   │       │   ├── style.css
│   │       │   └── script.js
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/example/employee_management/
│               └── EmployeeServiceTest.java
├── employee-api.http
├── mvnw
├── mvnw.cmd
├── pom.xml
├── .gitignore
└── README.md
```

## REST API Endpoints

Base URL: `http://localhost:8080`

| HTTP Method | Endpoint | Description |
|---|---|---|
| POST | `/api/employees` | Create an employee |
| GET | `/api/employees` | Retrieve all employees |
| GET | `/api/employees/{id}` | Retrieve an employee by ID |
| PUT | `/api/employees/{id}` | Update an employee |
| DELETE | `/api/employees/{id}` | Delete an employee |
| GET | `/actuator/health` | Check application health |
| GET | `/actuator/metrics` | View available metrics |

### Common HTTP Status Codes

- `200 OK` — Request completed successfully
- `201 Created` — Employee created successfully
- `204 No Content` — Employee deleted successfully
- `400 Bad Request` — Invalid request or validation failure
- `404 Not Found` — Employee not found
- `500 Internal Server Error` — Unexpected server error

## Prerequisites

Install the following software:

- JDK 21
- MySQL Server
- IntelliJ IDEA or another Java IDE
- Git
- Postman (for API testing)

The included Maven Wrapper can be used to run Maven commands without a separate Maven installation.

## Database Configuration

### 1. Create the MySQL Database

Start MySQL and execute:

```sql
CREATE DATABASE employee_db;
```

### 2. Configure Environment Variables

The application uses environment variables to configure database credentials.

| Variable | Description |
|---|---|
| `DB_URL` | MySQL JDBC connection URL |
| `DB_USERNAME` | MySQL username |
| `DB_PASSWORD` | MySQL password |

The default database URL is:

```text
jdbc:mysql://localhost:3306/employee_db
```

Configure `DB_USERNAME` and `DB_PASSWORD` in your local environment or IntelliJ run configuration before starting the application.

**Security:** Never commit actual database passwords, API keys, tokens, or secret `.env` files to GitHub.

## How to Run the Application

### 1. Clone the Repository

```bash
git clone https://github.com/prathod23944-spec/employee-management-spring-boot.git
cd employee-management-spring-boot
```

### 2. Configure MySQL

Create the `employee_db` database and set the required environment variables.

### 3. Run the Application

On Windows:

```bat
mvnw.cmd spring-boot:run
```

Alternatively, open the project in IntelliJ IDEA, configure the environment variables in the run configuration, and run `EmployeeManagementApplication.java`.

### 4. Open the Frontend

After the Spring Boot application starts, open:

`http://localhost:8080/`

The frontend files are located in `src/main/resources/static/` and are served by Spring Boot.

### 5. Test the APIs

Use Postman or the included `employee-api.http` file to send requests to the backend.

## Automated Testing

The project contains unit tests for the employee service using JUnit 5 and Mockito.

Run the tests on Windows:

```bat
mvnw.cmd clean test
```

The test suite currently contains seven service unit tests covering employee retrieval, creation, update, and deletion behavior.

A successful test run should report:

```text
BUILD SUCCESS
```

## Postman API Testing

The Postman collection can be stored in the `postman/` directory.

Suggested file:

```text
postman/
└── Employee Management API.postman_collection.json
```

### Import and Run

1. Open Postman.
2. Select **Import**.
3. Choose the exported collection JSON file.
4. Start the Spring Boot application and MySQL database.
5. Run the collection.
6. Review response status codes and response bodies.

The collection can be used to verify CRUD operations, request validation, error handling, and Actuator endpoints.

## Application Monitoring

Spring Boot Actuator exposes the following endpoints:

- `http://localhost:8080/actuator/health`
- `http://localhost:8080/actuator/metrics`

The health endpoint reports application health. The metrics endpoint provides available application, JVM, and other runtime metrics.

Only expose endpoints that are needed, and review security configuration before deploying the application publicly.

## Future Enhancements

- Add Spring Security and authentication
- Implement pagination and sorting
- Improve frontend responsiveness and accessibility
- Add integration tests for controller and database interactions
- Deploy the application to a hosting platform

## Author

**Pravin Indal Rathod**

- GitHub: [prathod23944-spec](https://github.com/prathod23944-spec)

## License

No license has been specified yet. Add a license if you intend to distribute this project under defined open-source terms.
