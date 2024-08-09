# Library Management System API Documentation

## Overview
This document provides instructions on how to set up, run, and interact with the Library Management System API. The API allows librarians to manage books, patrons, and borrowing records.

## 1. Prerequisites

Java 17 or higher
Maven for build and dependency management
PostgreSQL
Postman or any API testing tool (for interacting with the API)

## 2. Setup Instructions

### Clone the Repository:

bash
Copy code
git clone <https://github.com/mirnaihab/Library-Management-Server.git>
cd library-management-system

### Configure the Database:

Update the application.properties file in the src/main/resources directory with your database credentials.
Example for PostgreSQL:

properties
Copy code
spring.datasource.url=jdbc:postgresql://localhost:5432/library
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

### Build the Project:

bash
Copy code
mvn clean install
Run the Application:

bash
Copy code
mvn spring-boot:run

## 3. API Endpoints

### Books:

GET /api/books - Retrieve all books.
GET /api/books/{id} - Retrieve a book by ID.
POST /api/books - Add a new book.
PUT /api/books/{id} - Update an existing book.
DELETE /api/books/{id} - Remove a book.

### Patrons:

GET /api/patrons - Retrieve all patrons.
GET /api/patrons/{id} - Retrieve a patron by ID.
PUT /api/patrons/{id} - Update an existing patron.
DELETE /api/patrons/{id} - Remove a patron.

### Borrowing:

POST /api/borrow/{bookId}/patron/{patronId} - Borrow a book.
PUT /api/return/{bookId}/patron/{patronId} - Return a book.

## 4. Authentication

### Login:
POST /api/Authentication/Login - Authenticate and retrieve a JWT token.
### Register:
POST /api/Authentication/RegisterUser - Register a new user.
POST /api/Authentication/RegisterAdmin - Register a new Admin .

Include the JWT token in the Authorization header for all endpoints other than authrntication:

Admin has all the privilages to access all endpoints while a user only has roles to view all the books, borrow and return book.

http
Copy code
Authorization: Bearer <your-token>

## 5. Testing the API
Use Postman or any API client to send requests to the API endpoints.
Example request for adding a book:
http
Copy code
POST /api/books
Content-Type: application/json

{
  "title": "Book Title",
  "author": "Author Name",
  "publicationYear": 2021,
  "isbn": "1234567890123"
}

## 6. Running Tests
Run unit tests using:

bash
Copy code
mvn test

## 7. Additional Features
Transaction Management: Ensures data integrity during critical operations.
Logging: Tracks method calls, exceptions, and performance metrics using AOP.

## 8. API Documentation

Swagger Integration
The Library Management System API includes Swagger for easy exploration and interaction with the API endpoints. Swagger provides a user-friendly interface to view the available endpoints, their request/response formats, and allows you to test the API directly from the browser.

Accessing Swagger UI
Once the application is running, you can access the Swagger UI at the following URL:

bash
Copy code
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html#/)

Features:
Interactive API Documentation: View all available endpoints with detailed information.
Try Out Feature: Test API requests directly from the Swagger UI.
Schema Definitions: Explore the data models used by the API.
Example:
To explore and test the POST /api/Authentication/Login endpoint:

Navigate to the Swagger UI.
Locate the POST /api/Authentication/Login endpoint in the list.
Click on "Try it out."
Provide Username and email as stated in the example.
Click "Execute" to send the request and view the response.
Postman in recomended for testing APIs other than authentication because of the access token validation.
