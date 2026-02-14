# Spring Boot Address Management API

A RESTful API for managing addresses with full CRUD operations and dynamic search using Spring Data JPA Specifications.

## 🚀 Technologies
- Java 17
- Spring Boot 4.0.2
- Spring Data JPA
- projectlombok
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
```
src/main/java/com/example/address/
├── controller/      # REST endpoints
├── service/         # Business logic
├── repository/      # Data access layer
├── model/           # Entity classes
├── dto/             # Request/Response DTOs
├── specification/   # JPA Specifications for dynamic search
└── config/          # Configuration classes (CORS, etc.)
```
## ⚙️ Setup & Installation
### 1️⃣ Clone the repository
git clone https://github.com/your-username/address-management-api.git  
cd address-management-api  
### 2️⃣ Configure PostgreSQL
Create a database:  
CREATE DATABASE address_db;  
Update application.yml:  
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/address_db  
    username: your_username  
    password: your_password

### 3️⃣ Run the application
./gradlew bootRun

## 🧪 Test with Postman
- **POST**   → http://localhost:8080/addresses/v1
- **PUT**    → http://localhost:8080/addresses/v1/{id}
- **GET**    → http://localhost:8080/addresses/v1/{id}
- **DELETE** → http://localhost:8080/addresses/v1/{id}
- **GET**    → http://localhost:8080/addresses/v1 (Gets All Addresses)  
Example Request Json  
{
    "status" : "PENDING",  
    "source" : "SYSTEM",  
    "streetName": "Second Street",  
    "houseNumber": "35",  
    "houseLetter": "B"  
}   
Example Response Json  
{
  "id": 1,  
  "status": "PENDING",  
  "source": "SYSTEM",  
  "streetName": "Second Street",  
  "houseNumber": "35",  
  "houseLetter": "B",  
  "createdDate": "2026-02-14T10:15:30"
}   
Search API - http://localhost:8080/addresses/v1/search  
### Available Filters:  
- status
- source
- streetName
- houseNumber

🧪 Testing with simple GUI  
A single-file HTML GUI is included for testing:  
Start the Spring Boot application  
Open address-gui.html in your browser  
Test all CRUD and search operations  
