# Spring Boot Address Management API

A RESTful API for managing addresses with full CRUD operations and dynamic search using Spring Data JPA Specifications.

## 🚀 Technologies

- Java 17
- Spring Boot 3.5+
- Spring Data JPA
- PostgreSQL DB
- Gradle
- Spring Web

## 📋 Features

- ✅ Full CRUD operations for Address entity
- ✅ Dynamic search using Spring Data JPA Specifications
- ✅ Clean layered architecture (Controller → Service → Repository → Specification)
- ✅ Input validation
- ✅ Proper HTTP status codes
- ✅ CORS configuration for frontend access

## 🏗️ Project Structure
src/main/java/com/example/address/
├── controller/ # REST endpoints
├── service/ # Business logic
├── repository/ # Data access layer
├── model/ # Entity classes
├── dto/ # Request/Response DTOs
├── specification/ # JPA Specifications for dynamic search
└── config/ # Configuration classes (CORS, etc.)

🧪 Testing with Simple GUI
A single-file HTML GUI is included for testing:

Start the Spring Boot application

Open address-gui.html in your browser

Test all CRUD and search operations