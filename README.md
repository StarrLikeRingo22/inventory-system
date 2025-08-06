# JavaFX Inventory Management System

A desktop application built with **JavaFX** for managing parts and products within an inventory system. This project features a clean, modular architecture and integrates with a **MySQL database** using **prepared statements** to ensure secure and efficient data handling.

## Features

- ✅ **Login Screen** (with basic credential validation)
- **Parts and Products Management**
  - Add, modify, delete, and search parts/products
  - Associate parts with products
- **MySQL Database Integration**
  - Secure CRUD operations using **prepared statements**
  - DAO pattern to abstract database logic
- **FXML-based UI**
  - Designed using **JavaFX SceneBuilder**
  - Clean and responsive user interface
**Input Validation**
  - Type checks (e.g., numbers only)
  - Logical constraints (e.g., inventory within min/max)
- **Data Persistence**
  - MySQL backend (supports future file/XML extensions)

## Technologies Used

- Java 17+
- JavaFX (SceneBuilder + FXML)
- MySQL (JDBC)
- MVC Architecture
- IntelliJ IDEA (or any Java IDE)

pgsql
Copy
Edit

## Database Setup


1. **Create the MySQL database:**

```sql
CREATE DATABASE inventory;
USE inventory;
```

2. **Create tables (`parts`, `products`, etc.) — example:**

```sql
CREATE TABLE parts (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  price DOUBLE,
  stock INT,
  min INT,
  max INT
);
```

> You can expand with more tables as needed (e.g., `products`, `users`, etc.)

3. **Update DB credentials in your DB utility class (`DBConnection.java`):**

```java
private static final String URL = "jdbc:mysql://localhost:3306/inventory";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

## Running the App

1. **Clone the repository:**

```bash
git clone https://github.com/your-username/inventory-system.git
cd inventory-system
```

2. **Open the project in IntelliJ IDEA or Eclipse**

3. **Add JavaFX and MySQL JDBC to your classpath:**

- JavaFX SDK  
- mysql-connector-java.jar

4. **Run `Main.java`**

## Screenshots

> *(Optional — add screenshots of: login screen, product table view, part add/edit window, etc.)*

## Security

- All SQL queries are protected using **prepared statements** to prevent SQL injection.
- Form fields are validated to avoid runtime crashes and bad data entry.

