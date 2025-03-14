# Online Store Application

## Overview
This is an Online Store web application built using Java and the Spring Framework. The application provides functionality for customers, shopkeepers, and delivery personnel to interact with the system. It follows a layered architecture with controllers, services, DAOs, and entities. The frontend is implemented using JSP, while the backend leverages Spring MVC and Hibernate.

## Features
### User Authentication and Role Management
- Secure login and registration system.
- Role-based authentication for customers, shopkeepers, and delivery personnel.
- Profile management with password updates and personal details.

### Product and Category Management
- Shopkeepers can add, update, and remove products.
- Products can be categorized for easy navigation.
- Customers can browse and filter products by category and tags.

### Order Management
- Customers can place orders and provide delivery details.
- Shopkeepers can process and manage incoming orders.
- Delivery personnel can track and update order statuses.

### Shopping Cart and Checkout
- Customers can add items to the cart before proceeding to checkout.
- Order summary is displayed before final confirmation.
- Secure payment processing integration (future scope).

### Validation and Exception Handling
- Input validation for user registration, order details, and form submissions.
- Custom exception handling for better error management.

### Security and Encryption
- Secure password storage using encryption.
- Protection against common web vulnerabilities (e.g., SQL injection, CSRF, XSS).
- Role-based access control to prevent unauthorized actions.

## Technologies Used
- **Backend:** Java, Spring Boot, Hibernate
- **Frontend:** JSP, JavaScript, CSS
- **Database:** SQL (MySQL/PostgreSQL)
- **Build Tool:** Gradle
- **Security:** Encryption utilities, authentication filters

## Project Structure
```
.
├── README.md
├── build.gradle
├── settings.gradle
├── db-migration (Database migration scripts)
│   ├── dml.sql
│   └── pre-ddl.sql
├── src
│   ├── main
│   │   ├── java
│   │   │   └── net.therap.onlinestore
│   │   │       ├── controller (Handles HTTP requests)
│   │   │       ├── entity (Database entities)
│   │   │       ├── service (Business logic implementation)
│   │   │       ├── exception (Custom exception handling)
│   │   │       ├── filter (Authentication and request filters)
│   │   │       ├── helper (Utility classes for various functions)
│   │   │       ├── validator (Input validation rules)
│   │   ├── resources
│   │   │   ├── application.properties (Configuration settings)
│   │   │   ├── log4j.properties (Logging configuration)
│   │   │   ├── messages.properties (Internationalization support)
│   │   │   ├── META-INF/persistence.xml (Database configuration)
│   │   ├── webapp
│   │       ├── WEB-INF (Spring MVC and JSP configuration)
│   │       ├── assets (CSS, JavaScript, and images)
```

## Setup Instructions
### Prerequisites
- Java 17+
- Gradle
- Database (MySQL/PostgreSQL/SQLite based on configuration)
- Tomcat or any servlet container

### Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/Nadim-Mahmud/online-store.git
   cd online-store-app
   ```
2. Configure the database in `persistence.xml`.
3. Build the project using Gradle:
   ```sh
   ./gradlew build
   ```
4. Deploy the WAR file to Tomcat or run locally:
   ```sh
   ./gradlew bootRun
   ```
5. Access the application at `http://localhost:8080`.