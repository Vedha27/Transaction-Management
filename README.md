# Transaction-Management
# 💳 ABC Bank - JDBC Mini Project

A command-line mini-project that simulates basic banking operations using **Java JDBC** and **MySQL**. This project showcases the use of **transaction management**, including **commit**, **rollback**, and **savepoints**, for a secure fund transfer system.

---

## 🚀 Features

- 🔐 User login with account number and PIN
- 💰 Check account balance
- 🔄 Transfer money to another account
- 🧩 Recipient approval simulation
- 🔄 Rollback transaction on rejection
- 📌 Savepoint-based partial rollback
- 🎯 JDBC APIs used: `Connection`, `PreparedStatement`, `ResultSet`

---

## 🛠️ Technologies Used

| Technology | Version |
|------------|---------|
| Java       | 8 or above |
| MySQL      | 5.7+ |
| JDBC       | MySQL Connector/J |
| IDE        | Eclipse / IntelliJ / VS Code |

---

## 📦 Database Setup

Run the following SQL to prepare your database:

```sql
CREATE DATABASE abc_bank;

USE abc_bank;

CREATE TABLE account (
    acc_num INT PRIMARY KEY,
    acc_name VARCHAR(100),
    pin_num INT,
    balance INT
);

INSERT INTO account VALUES
(101, 'Alice', 1234, 5000),
(102, 'Bob', 5678, 3000);
