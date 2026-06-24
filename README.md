# Student Management CRUD Application (Spring Boot)

This repository contains a backend RESTful API built with **Spring Boot** to manage student records. It follows clean code principles, incorporating a layered architecture with the Controller-Service-Repository pattern and utilizes Data Transfer Objects (DTOs) to separate database entities from API request/response structures.

---

## 🚀 Key Features

* **Layered Architecture**: Decoupled layers using Controllers, Services, and Repositories.
* **REST API Endpoints**: Full CRUD operations for creating, reading, updating, and deleting student records.
* **Data Mapping**: Uses custom DTO mapping (`StudentMapper`) to handle data transfer between the client and database.
* **Input Validation & Exception Handling**: Validates request parameters (e.g., preventing duplicate email registration) and throws standard Spring Web HTTP exceptions (`400 Bad Request`, `404 Not Found`).
* **Database Integration**: Integrates JpaRepository with MySQL for persistent data storage.

---

## 🛠️ Tech Stack

* **Java Version**: 17
* **Framework**: Spring Boot
* **Persistence ORM**: Spring Data JPA / Hibernate
* **Database**: MySQL (driver: `mysql-connector-j`)
* **Build Tool**: Apache Maven

---

## 📁 Project Structure

Here is an overview of the key source files under `src/main/java/com/kmedtech/my_springboot_app`:

* **`MySpringbootAppApplication.java`**: The main entry point of the Spring Boot application.
* **`model/Student.java`**: The JPA Entity class representing the `students` table in the database.
* **`dto/StudentDTO.java`**: The Data Transfer Object class used for HTTP requests and responses.
* **`dto/StudentMapper.java`**: Helper utility to map entities to DTOs and vice-versa.
* **`repository/StudentRepository.java`**: Repository interface extending `JpaRepository` for data access, including finding students by email.
* **`service/StudentService.java`**: Service class containing the business logic and orchestrating database interactions.
* **`controller/StudentController.java`**: REST Controller defining public API routes and handling HTTP requests.

---

## 🛢️ Database Configuration

By default, the application is configured to connect to a **MySQL** database named `studentdb`. You can view or modify these settings in:
📄 `src/main/resources/application.yml`

```yaml
spring:
  application:
    name: My_Springboot_app
  datasource:
    url: jdbc:mysql://localhost:3306/studentdb
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: password
  jpa:
    database-platform: org.hibernate.dialect.MySQLDialect
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
```

*Note: You can uncomment the PostgreSQL datasource configuration in `application.yml` and the dependency in `pom.xml` if you prefer to use PostgreSQL.*

---

## 🔌 API Documentation

All endpoints are prefixed with `/api/students`.

| HTTP Method | Endpoint | Description | Status Code (Success) | Status Code (Error) |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/api/students` | Retrieve a list of all students | `200 OK` | `500 Internal Error` |
| **GET** | `/api/students/{id}` | Retrieve details of a specific student by ID | `200 OK` | `404 Not Found` |
| **POST** | `/api/students` | Register/Create a new student | `201 Created` | `400 Bad Request` |
| **PUT** | `/api/students/{id}` | Update details of an existing student by ID | `200 OK` | `404 Not Found` / `400 Bad Request` |
| **DELETE** | `/api/students/{id}` | Remove a student by ID | `204 No Content` | `404 Not Found` |

### Sample Payloads

#### Create / Update Student (`POST` / `PUT`)
**Request Body (`application/json`):**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "age": 21
}
```

**Response Body (`201 Created` / `200 OK`):**
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "age": 21
}
```

---

## 🏃 How to Run the Application Locally

### Prerequisites
1. **Java Development Kit (JDK)**: Version 17 installed.
2. **MySQL Server**: Installed and running on port 3306.

### Steps
1. **Create the Database**:
   Connect to your MySQL server and run:
   ```sql
   CREATE DATABASE studentdb;
   ```

2. **Configure Database Credentials**:
   If your MySQL username or password is not `root` / `password`, update them in `src/main/resources/application.yml`.

3. **Run the Application**:
   Open a terminal in the root directory of the project and execute:
   * **On Windows (PowerShell/CMD)**:
     ```powershell
     .\mvnw.cmd spring-boot:run
     ```
   * **On Linux / macOS**:
     ```bash
     ./mvnw spring-boot:run
     ```

4. **Verify**:
   The backend service starts by default on port `8080`. You should see terminal logs indicating the application is running:
   ```
   Tomcat started on port 8080 (http) with context path '/'
   ```
   You can verify it by opening your browser or API client (Postman/Curl) and visiting `http://localhost:8080/api/students`.