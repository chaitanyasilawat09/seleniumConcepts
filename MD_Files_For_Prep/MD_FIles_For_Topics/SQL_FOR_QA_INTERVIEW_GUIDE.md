# SQL for QA - Complete Interview Preparation Guide

---

## TABLE OF CONTENTS
1. SQL Fundamentals
2. Database Concepts
3. SELECT Statement
4. WHERE Clause
5. ORDER BY Clause
6. LIMIT and OFFSET
7. Aggregate Functions
8. GROUP BY Clause
9. HAVING Clause
10. JOIN Operations
11. UNION and UNION ALL
12. Subqueries
13. Views
14. Indexes
15. Transactions
16. DML Operations (INSERT, UPDATE, DELETE)
17. DDL Operations (CREATE, ALTER, DROP)
18. Constraints
19. Stored Procedures
20. Functions
21. Triggers
22. SQL Injection
23. Database Testing
24. Performance Optimization
25. Common Interview Questions

---

## 1. SQL FUNDAMENTALS

### What is SQL?
SQL (Structured Query Language) is a standard language for managing and manipulating relational databases. It's used for querying, updating, and managing data in databases.

### SQL Categories

**DDL (Data Definition Language):**
- CREATE: Create database objects
- ALTER: Modify database objects
- DROP: Delete database objects
- TRUNCATE: Remove table data

**DML (Data Manipulation Language):**
- SELECT: Retrieve data
- INSERT: Insert new data
- UPDATE: Update existing data
- DELETE: Delete data

**DCL (Data Control Language):**
- GRANT: Grant permissions
- REVOKE: Revoke permissions

**TCL (Transaction Control Language):**
- COMMIT: Save changes
- ROLLBACK: Undo changes
- SAVEPOINT: Create savepoint

### Database Management Systems

**Popular DBMS:**
- MySQL
- PostgreSQL
- Oracle
- SQL Server
- SQLite

### Interview Questions

**Q1: What is SQL?**
- Structured Query Language
- Used for database operations
- Standard for relational databases
- Query, update, manage data

**Q2: What are the categories of SQL?**
- DDL: Create, Alter, Drop
- DML: Select, Insert, Update, Delete
- DCL: Grant, Revoke
- TCL: Commit, Rollback

**Q3: What are popular database management systems?**
- MySQL
- PostgreSQL
- Oracle
- SQL Server
- SQLite

---

## 2. DATABASE CONCEPTS

### What is a Database?
A database is an organized collection of structured data stored electronically. It allows efficient storage, retrieval, and management of data.

### Tables

**Table Structure:**
```sql
CREATE TABLE users (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    age INT,
    created_at TIMESTAMP
);
```

**Table Components:**
- Columns: Fields in table
- Rows: Records in table
- Primary Key: Unique identifier
- Foreign Key: Reference to another table

### Relationships

**One-to-One:**
- Each record in table A relates to one record in table B
- Example: User and Profile

**One-to-Many:**
- Each record in table A relates to multiple records in table B
- Example: User and Orders

**Many-to-Many:**
- Multiple records in table A relate to multiple records in table B
- Example: Students and Courses (requires junction table)

### Normalization

**First Normal Form (1NF):**
- Atomic values
- No repeating groups

**Second Normal Form (2NF):**
- 1NF + No partial dependencies
- All non-key attributes depend on entire primary key

**Third Normal Form (3NF):**
- 2NF + No transitive dependencies
- Non-key attributes depend only on primary key

### Interview Questions

**Q1: What is a database table?**
- Collection of related data
- Organized in rows and columns
- Has a defined structure
- Primary key for uniqueness

**Q2: What are the different types of relationships in databases?**
- One-to-One
- One-to-Many
- Many-to-Many
- Self-referencing

**Q3: What is database normalization?**
- Process of organizing data
- Reduces redundancy
- Improves data integrity
- 1NF, 2NF, 3NF

---

## 3. SELECT STATEMENT

### Basic SELECT

```sql
-- Select all columns
SELECT * FROM users;

-- Select specific columns
SELECT id, name, email FROM users;

-- Select with alias
SELECT name AS user_name, email AS user_email FROM users;
```

### SELECT DISTINCT

```sql
-- Select unique values
SELECT DISTINCT country FROM users;

-- Select unique combinations
SELECT DISTINCT country, city FROM users;
```

### SELECT with Expressions

```sql
-- Mathematical operations
SELECT name, salary, salary * 12 AS annual_salary FROM employees;

-- String concatenation
SELECT first_name || ' ' || last_name AS full_name FROM employees;

-- Date operations
SELECT name, AGE(created_at) AS account_age FROM users;
```

### SELECT with CASE

```sql
SELECT name,
    CASE
        WHEN age < 18 THEN 'Minor'
        WHEN age BETWEEN 18 AND 60 THEN 'Adult'
        ELSE 'Senior'
    END AS age_group
FROM users;
```

### Interview Questions

**Q1: How do you select all columns from a table?**
- Use SELECT * FROM table_name
- Returns all columns
- Not recommended for production
- Use specific columns instead

**Q2: What is SELECT DISTINCT?**
- Returns unique values
- Removes duplicates
- Can be used with multiple columns
- Example: SELECT DISTINCT country

**Q3: How do you use CASE in SELECT?**
- Conditional logic in SELECT
- Similar to IF-ELSE
- Can create derived columns
- Example: CASE WHEN condition THEN value

---

## 4. WHERE CLAUSE

### Basic WHERE

```sql
-- Simple condition
SELECT * FROM users WHERE age > 18;

-- Multiple conditions with AND
SELECT * FROM users WHERE age > 18 AND country = 'USA';

-- Multiple conditions with OR
SELECT * FROM users WHERE country = 'USA' OR country = 'UK';
```

### Comparison Operators

```sql
-- Equal
SELECT * FROM users WHERE age = 25;

-- Not equal
SELECT * FROM users WHERE age != 25;

-- Greater than
SELECT * FROM users WHERE age > 25;

-- Less than
SELECT * FROM users WHERE age < 25;

-- Greater than or equal
SELECT * FROM users WHERE age >= 25;

-- Less than or equal
SELECT * FROM users WHERE age <= 25;
```

### Pattern Matching

```sql
-- LIKE with wildcard
SELECT * FROM users WHERE name LIKE 'J%';  -- Starts with J
SELECT * FROM users WHERE name LIKE '%son';  -- Ends with son
SELECT * FROM users WHERE name LIKE '%an%';  -- Contains an

-- NOT LIKE
SELECT * FROM users WHERE name NOT LIKE 'J%';
```

### IN Operator

```sql
-- IN with list
SELECT * FROM users WHERE country IN ('USA', 'UK', 'Canada');

-- NOT IN
SELECT * FROM users WHERE country NOT IN ('USA', 'UK', 'Canada');
```

### BETWEEN Operator

```sql
-- BETWEEN inclusive
SELECT * FROM users WHERE age BETWEEN 18 AND 60;

-- NOT BETWEEN
SELECT * FROM users WHERE age NOT BETWEEN 18 AND 60;
```

### NULL Handling

```sql
-- IS NULL
SELECT * FROM users WHERE email IS NULL;

-- IS NOT NULL
SELECT * FROM users WHERE email IS NOT NULL;
```

### Interview Questions

**Q1: What is the WHERE clause used for?**
- Filter records
- Apply conditions
- Return specific rows
- Used with SELECT, UPDATE, DELETE

**Q2: What is the difference between = and LIKE?**
- =: Exact match
- LIKE: Pattern matching
- =: Case-sensitive
- LIKE: Can use wildcards

**Q3: How do you handle NULL values in WHERE clause?**
- Use IS NULL
- Use IS NOT NULL
- Cannot use = or !=
- Example: WHERE email IS NULL

---

## 5. ORDER BY CLAUSE

### Basic ORDER BY

```sql
-- Ascending order (default)
SELECT * FROM users ORDER BY name;

-- Descending order
SELECT * FROM users ORDER BY name DESC;

-- Order by multiple columns
SELECT * FROM users ORDER BY country, name;
```

### ORDER BY with Different Directions

```sql
-- Different directions for different columns
SELECT * FROM users ORDER BY country ASC, name DESC;
```

### ORDER BY with Expression

```sql
-- Order by calculated value
SELECT name, salary FROM employees ORDER BY salary * 12 DESC;

-- Order by function result
SELECT name, LENGTH(name) AS name_length FROM users ORDER BY name_length;
```

### ORDER BY with Position

```sql
-- Order by column position
SELECT id, name, age FROM users ORDER BY 3 DESC;  -- Order by age (3rd column)
```

### Interview Questions

**Q1: What is ORDER BY used for?**
- Sort result set
- Ascending or descending
- Can sort by multiple columns
- Can use expressions

**Q2: How do you sort in descending order?**
- Use DESC keyword
- Example: ORDER BY name DESC
- Default is ascending
- Can use with multiple columns

**Q3: Can you ORDER BY a column not in SELECT?**
- Yes, in most databases
- Not required to be in SELECT
- Can use expressions
- Example: ORDER BY hidden_column

---

## 6. LIMIT AND OFFSET

### LIMIT

```sql
-- Limit number of rows
SELECT * FROM users LIMIT 10;

-- Limit with ORDER BY
SELECT * FROM users ORDER BY created_at DESC LIMIT 5;
```

### OFFSET

```sql
-- Skip first N rows
SELECT * FROM users OFFSET 5;

-- LIMIT with OFFSET (pagination)
SELECT * FROM users LIMIT 10 OFFSET 0;   -- Page 1
SELECT * FROM users LIMIT 10 OFFSET 10;  -- Page 2
SELECT * FROM users LIMIT 10 OFFSET 20;  -- Page 3
```

### Pagination Formula

```sql
-- Page size: 10
-- Page number: n
-- OFFSET = (n - 1) * 10

SELECT * FROM users LIMIT 10 OFFSET (page_number - 1) * 10;
```

### Database-Specific Syntax

```sql
-- MySQL, PostgreSQL, SQLite
SELECT * FROM users LIMIT 10 OFFSET 5;

-- SQL Server
SELECT * FROM users ORDER BY id OFFSET 5 ROWS FETCH NEXT 10 ROWS ONLY;

-- Oracle
SELECT * FROM users OFFSET 5 ROWS FETCH NEXT 10 ROWS ONLY;
```

### Interview Questions

**Q1: What is LIMIT used for?**
- Restrict number of rows
- Return top N records
- Used for pagination
- Example: LIMIT 10

**Q2: What is OFFSET used for?**
- Skip N rows
- Used with LIMIT
- For pagination
- Example: OFFSET 10

**Q3: How do you implement pagination in SQL?**
- Use LIMIT and OFFSET
- Formula: OFFSET = (page - 1) * size
- Example: LIMIT 10 OFFSET 0
- Different syntax per database

---

## 7. AGGREGATE FUNCTIONS

### COUNT

```sql
-- Count all rows
SELECT COUNT(*) FROM users;

-- Count non-null values
SELECT COUNT(email) FROM users;

-- Count distinct values
SELECT COUNT(DISTINCT country) FROM users;
```

### SUM

```sql
-- Sum of values
SELECT SUM(salary) FROM employees;

-- Sum with condition
SELECT SUM(salary) FROM employees WHERE department = 'IT';
```

### AVG

```sql
-- Average value
SELECT AVG(salary) FROM employees;

-- Average with condition
SELECT AVG(salary) FROM employees WHERE department = 'IT';
```

### MIN and MAX

```sql
-- Minimum value
SELECT MIN(salary) FROM employees;

-- Maximum value
SELECT MAX(salary) FROM employees;

-- Min and Max together
SELECT MIN(salary) AS min_salary, MAX(salary) AS max_salary FROM employees;
```

### Multiple Aggregates

```sql
-- Multiple aggregate functions
SELECT 
    COUNT(*) AS total_employees,
    SUM(salary) AS total_salary,
    AVG(salary) AS avg_salary,
    MIN(salary) AS min_salary,
    MAX(salary) AS max_salary
FROM employees;
```

### Interview Questions

**Q1: What are aggregate functions in SQL?**
- Functions that perform calculations
- Operate on multiple rows
- Return single value
- Examples: COUNT, SUM, AVG

**Q2: What is the difference between COUNT(*) and COUNT(column)?**
- COUNT(*): Counts all rows
- COUNT(column): Counts non-null values
- COUNT(*) includes NULLs
- COUNT(column) excludes NULLs

**Q3: How do you calculate average in SQL?**
- Use AVG() function
- Example: AVG(salary)
- Can use with WHERE
- Returns single value

---

## 8. GROUP BY CLAUSE

### Basic GROUP BY

```sql
-- Group by single column
SELECT country, COUNT(*) FROM users GROUP BY country;

-- Group by multiple columns
SELECT country, city, COUNT(*) FROM users GROUP BY country, city;
```

### GROUP BY with Aggregate Functions

```sql
-- Group with COUNT
SELECT department, COUNT(*) AS employee_count 
FROM employees 
GROUP BY department;

-- Group with SUM
SELECT department, SUM(salary) AS total_salary 
FROM employees 
GROUP BY department;

-- Group with AVG
SELECT department, AVG(salary) AS avg_salary 
FROM employees 
GROUP BY department;
```

### GROUP BY with HAVING

```sql
-- Filter groups with HAVING
SELECT department, COUNT(*) AS employee_count 
FROM employees 
GROUP BY department 
HAVING COUNT(*) > 10;

-- Multiple conditions in HAVING
SELECT department, AVG(salary) AS avg_salary 
FROM employees 
GROUP BY department 
HAVING AVG(salary) > 50000 AND COUNT(*) > 5;
```

### GROUP BY with ORDER BY

```sql
-- Order by aggregate
SELECT department, COUNT(*) AS employee_count 
FROM employees 
GROUP BY department 
ORDER BY employee_count DESC;
```

### Interview Questions

**Q1: What is GROUP BY used for?**
- Group rows with same values
- Used with aggregate functions
- Summarize data
- Example: GROUP BY department

**Q2: What is the difference between WHERE and HAVING?**
- WHERE: Filters rows before grouping
- HAVING: Filters groups after grouping
- WHERE: Cannot use aggregates
- HAVING: Can use aggregates

**Q3: Can you use WHERE with GROUP BY?**
- Yes, WHERE filters before grouping
- HAVING filters after grouping
- Both can be used together
- Example: WHERE condition GROUP BY column HAVING condition

---

## 9. HAVING CLAUSE

### Basic HAVING

```sql
-- Filter groups
SELECT department, COUNT(*) AS employee_count 
FROM employees 
GROUP BY department 
HAVING COUNT(*) > 10;
```

### HAVING with Multiple Conditions

```sql
-- Multiple conditions
SELECT department, AVG(salary) AS avg_salary 
FROM employees 
GROUP BY department 
HAVING AVG(salary) > 50000 AND COUNT(*) > 5;
```

### HAVING vs WHERE

```sql
-- WHERE filters before grouping
SELECT department, AVG(salary) 
FROM employees 
WHERE salary > 30000 
GROUP BY department;

-- HAVING filters after grouping
SELECT department, AVG(salary) 
FROM employees 
GROUP BY department 
HAVING AVG(salary) > 50000;
```

### Combined WHERE and HAVING

```sql
-- Both WHERE and HAVING
SELECT department, AVG(salary) AS avg_salary 
FROM employees 
WHERE hire_date > '2020-01-01' 
GROUP BY department 
HAVING AVG(salary) > 50000;
```

### Interview Questions

**Q1: What is HAVING clause used for?**
- Filter groups after GROUP BY
- Used with aggregate functions
- Cannot use without GROUP BY
- Example: HAVING COUNT(*) > 10

**Q2: What is the difference between WHERE and HAVING?**
- WHERE: Filters rows before grouping
- HAVING: Filters groups after grouping
- WHERE: Cannot use aggregates
- HAVING: Can use aggregates

**Q3: Can you use HAVING without GROUP BY?**
- No, HAVING requires GROUP BY
- Used to filter grouped data
- WHERE is for row-level filtering
- HAVING is for group-level filtering

---

## 10. JOIN OPERATIONS

### INNER JOIN

```sql
-- Inner join (only matching rows)
SELECT users.name, orders.order_id
FROM users
INNER JOIN orders ON users.id = orders.user_id;
```

### LEFT JOIN

```sql
-- Left join (all from left, matching from right)
SELECT users.name, orders.order_id
FROM users
LEFT JOIN orders ON users.id = orders.user_id;
```

### RIGHT JOIN

```sql
-- Right join (all from right, matching from left)
SELECT users.name, orders.order_id
FROM users
RIGHT JOIN orders ON users.id = orders.user_id;
```

### FULL OUTER JOIN

```sql
-- Full outer join (all from both tables)
SELECT users.name, orders.order_id
FROM users
FULL OUTER JOIN orders ON users.id = orders.user_id;
```

### SELF JOIN

```sql
-- Self join (join table to itself)
SELECT e1.name AS employee, e2.name AS manager
FROM employees e1
LEFT JOIN employees e2 ON e1.manager_id = e2.id;
```

### CROSS JOIN

```sql
-- Cross join (Cartesian product)
SELECT users.name, products.name
FROM users
CROSS JOIN products;
```

### Multiple Joins

```sql
-- Join multiple tables
SELECT u.name, o.order_id, p.product_name
FROM users u
JOIN orders o ON u.id = o.user_id
JOIN order_items oi ON o.order_id = oi.order_id
JOIN products p ON oi.product_id = p.id;
```

### Interview Questions

**Q1: What are the different types of JOINs?**
- INNER JOIN: Matching rows only
- LEFT JOIN: All from left, matching from right
- RIGHT JOIN: All from right, matching from left
- FULL OUTER JOIN: All from both tables

**Q2: What is the difference between INNER JOIN and LEFT JOIN?**
- INNER: Only matching rows
- LEFT: All from left table
- INNER: No NULLs from join
- LEFT: NULLs for non-matching

**Q3: What is a SELF JOIN?**
- Join table to itself
- Use aliases to distinguish
- Used for hierarchical data
- Example: Employee and Manager

---

## 11. UNION AND UNION ALL

### UNION

```sql
-- Combine results (remove duplicates)
SELECT name FROM employees
UNION
SELECT name FROM customers;
```

### UNION ALL

```sql
-- Combine results (keep duplicates)
SELECT name FROM employees
UNION ALL
SELECT name FROM customers;
```

### UNION with ORDER BY

```sql
-- Order combined results
SELECT name FROM employees
UNION
SELECT name FROM customers
ORDER BY name;
```

### UNION with Different Columns

```sql
-- Union with different column names
SELECT id, name FROM employees
UNION
SELECT customer_id AS id, customer_name AS name FROM customers;
```

### Interview Questions

**Q1: What is the difference between UNION and UNION ALL?**
- UNION: Removes duplicates
- UNION ALL: Keeps duplicates
- UNION: Slower (needs to check duplicates)
- UNION ALL: Faster

**Q2: When would you use UNION?**
- Combine results from multiple queries
- Remove duplicates
- Similar column structure
- Example: Combine employee and customer names

**Q3: Can you use ORDER BY with UNION?**
- Yes, at the end of UNION
- Applies to combined results
- Cannot use ORDER BY in individual queries
- Example: SELECT ... UNION SELECT ... ORDER BY name

---

## 12. SUBQUERIES

### Single Row Subquery

```sql
-- Subquery returns single row
SELECT name, salary 
FROM employees 
WHERE salary > (SELECT AVG(salary) FROM employees);
```

### Multiple Row Subquery

```sql
-- Subquery returns multiple rows
SELECT name, department 
FROM employees 
WHERE department IN (SELECT department FROM employees WHERE salary > 50000);
```

### Correlated Subquery

```sql
-- Subquery references outer query
SELECT name, salary 
FROM employees e 
WHERE salary > (SELECT AVG(salary) FROM employees WHERE department = e.department);
```

### Subquery in SELECT

```sql
-- Subquery in SELECT clause
SELECT name, 
       salary,
       (SELECT AVG(salary) FROM employees) AS avg_salary
FROM employees;
```

### Subquery in FROM

```sql
-- Subquery in FROM clause
SELECT department, avg_salary
FROM (SELECT department, AVG(salary) AS avg_salary 
      FROM employees 
      GROUP BY department) AS dept_avg
WHERE avg_salary > 50000;
```

### EXISTS Operator

```sql
-- Check if subquery returns any rows
SELECT name 
FROM employees 
WHERE EXISTS (SELECT 1 FROM orders WHERE employee_id = employees.id);
```

### Interview Questions

**Q1: What is a subquery?**
- Query within another query
- Nested SELECT statement
- Can return single or multiple rows
- Used in WHERE, FROM, SELECT

**Q2: What is a correlated subquery?**
- References outer query
- Executes for each row
- Slower than non-correlated
- Example: WHERE salary > (SELECT AVG(salary) FROM employees WHERE department = e.department)

**Q3: What is the difference between IN and EXISTS?**
- IN: Compares values
- EXISTS: Checks existence
- IN: Can use with subquery
- EXISTS: More efficient for large datasets

---

## 13. VIEWS

### Create View

```sql
-- Create view
CREATE VIEW employee_summary AS
SELECT id, name, department, salary
FROM employees
WHERE salary > 40000;
```

### Use View

```sql
-- Query view
SELECT * FROM employee_summary;
```

### Update View

```sql
-- Update underlying data through view
UPDATE employee_summary SET salary = 50000 WHERE id = 1;
```

### Drop View

```sql
-- Drop view
DROP VIEW employee_summary;
```

### View Benefits

- Simplify complex queries
- Security (hide columns)
- Data independence
- Reusability

### Interview Questions

**Q1: What is a view in SQL?**
- Virtual table based on query
- Doesn't store data
- Simplifies complex queries
- Can be used like table

**Q2: What are the benefits of using views?**
- Simplify complex queries
- Provide security
- Data independence
- Reusability

**Q3: Can you update data through a view?**
- Yes, for simple views
- Depends on view definition
- Cannot update complex views
- Updates affect underlying table

---

## 14. INDEXES

### Create Index

```sql
-- Create index on single column
CREATE INDEX idx_user_email ON users(email);

-- Create index on multiple columns
CREATE INDEX idx_user_name_email ON users(name, email);
```

### Unique Index

```sql
-- Create unique index
CREATE UNIQUE INDEX idx_user_email ON users(email);
```

### Drop Index

```sql
-- Drop index
DROP INDEX idx_user_email;
```

### Index Benefits

- Faster data retrieval
- Improves query performance
- Speeds up WHERE, JOIN, ORDER BY
- Reduces I/O operations

### Index Drawbacks

- Slows down INSERT/UPDATE/DELETE
- Uses disk space
- Requires maintenance
- Can become fragmented

### Interview Questions

**Q1: What is an index in SQL?**
- Data structure for fast search
- Improves query performance
- Created on columns
- Like book index

**Q2: What are the benefits of indexes?**
- Faster data retrieval
- Improves query performance
- Speeds up WHERE, JOIN, ORDER BY
- Reduces I/O operations

**Q3: What are the drawbacks of indexes?**
- Slows down INSERT/UPDATE/DELETE
- Uses disk space
- Requires maintenance
- Can become fragmented

---

## 15. TRANSACTIONS

### Transaction Commands

```sql
-- Start transaction
BEGIN TRANSACTION;

-- Execute statements
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;

-- Commit transaction
COMMIT;

-- Or rollback
ROLLBACK;
```

### ACID Properties

**Atomicity:**
- All or nothing
- Either all succeed or all fail
- No partial updates

**Consistency:**
- Database remains consistent
- Valid state before and after
- Follows all rules

**Isolation:**
- Transactions don't interfere
- Concurrent transactions isolated
- Each sees consistent view

**Durability:**
- Committed changes persist
- Survive system failures
- Permanent once committed

### Transaction Isolation Levels

**READ UNCOMMITTED:**
- Can read uncommitted changes
- Lowest isolation
- Dirty reads possible

**READ COMMITTED:**
- Can only read committed changes
- Prevents dirty reads
- Non-repeatable reads possible

**REPEATABLE READ:**
- Same read within transaction
- Prevents non-repeatable reads
- Phantom reads possible

**SERIALIZABLE:**
- Highest isolation
- Prevents all anomalies
- Slowest performance

### Interview Questions

**Q1: What is a transaction in SQL?**
- Group of SQL statements
- Treated as single unit
- All or nothing
- Uses COMMIT and ROLLBACK

**Q2: What are ACID properties?**
- Atomicity: All or nothing
- Consistency: Valid state
- Isolation: No interference
- Durability: Permanent

**Q3: What are transaction isolation levels?**
- READ UNCOMMITTED: Lowest
- READ COMMITTED: Can read committed
- REPEATABLE READ: Same read
- SERIALIZABLE: Highest

---

## 16. DML OPERATIONS

### INSERT

```sql
-- Insert single row
INSERT INTO users (name, email, age) VALUES ('John Doe', 'john@example.com', 25);

-- Insert multiple rows
INSERT INTO users (name, email, age) VALUES
    ('John Doe', 'john@example.com', 25),
    ('Jane Doe', 'jane@example.com', 30),
    ('Bob Smith', 'bob@example.com', 35);

-- Insert from SELECT
INSERT INTO users_archive
SELECT * FROM users WHERE created_at < '2020-01-01';
```

### UPDATE

```sql
-- Update single column
UPDATE users SET age = 26 WHERE id = 1;

-- Update multiple columns
UPDATE users SET age = 26, email = 'newemail@example.com' WHERE id = 1;

-- Update with condition
UPDATE users SET age = age + 1 WHERE country = 'USA';

-- Update all rows
UPDATE users SET status = 'active';
```

### DELETE

```sql
-- Delete specific rows
DELETE FROM users WHERE id = 1;

-- Delete with condition
DELETE FROM users WHERE age < 18;

-- Delete all rows
DELETE FROM users;

-- Delete with subquery
DELETE FROM users WHERE id IN (SELECT user_id FROM orders WHERE order_date < '2020-01-01');
```

### TRUNCATE

```sql
-- Remove all rows (faster than DELETE)
TRUNCATE TABLE users;

-- Cannot use WHERE with TRUNCATE
-- Cannot rollback in some databases
-- Resets auto-increment
```

### Interview Questions

**Q1: How do you insert data into a table?**
- Use INSERT statement
- Specify columns and values
- Can insert multiple rows
- Can insert from SELECT

**Q2: What is the difference between DELETE and TRUNCATE?**
- DELETE: Can use WHERE, slower
- TRUNCATE: Cannot use WHERE, faster
- DELETE: Can rollback
- TRUNCATE: Cannot rollback in some DBMS

**Q3: How do you update data in SQL?**
- Use UPDATE statement
- Specify SET clause
- Use WHERE to filter
- Can update multiple columns

---

## 17. DDL OPERATIONS

### CREATE TABLE

```sql
-- Create table
CREATE TABLE users (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    age INT DEFAULT 18,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### ALTER TABLE

```sql
-- Add column
ALTER TABLE users ADD COLUMN phone VARCHAR(20);

-- Drop column
ALTER TABLE users DROP COLUMN phone;

-- Modify column
ALTER TABLE users MODIFY COLUMN name VARCHAR(200);

-- Rename column
ALTER TABLE users RENAME COLUMN name TO full_name;

-- Add constraint
ALTER TABLE users ADD CONSTRAINT chk_age CHECK (age >= 18);
```

### DROP TABLE

```sql
-- Drop table
DROP TABLE users;

-- Drop table if exists
DROP TABLE IF EXISTS users;
```

### CREATE DATABASE

```sql
-- Create database
CREATE DATABASE my_database;

-- Create database if not exists
CREATE DATABASE IF NOT EXISTS my_database;
```

### DROP DATABASE

```sql
-- Drop database
DROP DATABASE my_database;

-- Drop database if exists
DROP DATABASE IF EXISTS my_database;
```

### Interview Questions

**Q1: What is DDL in SQL?**
- Data Definition Language
- Defines database structure
- CREATE, ALTER, DROP
- Manages schema

**Q2: How do you create a table in SQL?**
- Use CREATE TABLE
- Define columns and types
- Add constraints
- Example: CREATE TABLE users (id INT, name VARCHAR(100))

**Q3: What is the difference between DROP and TRUNCATE?**
- DROP: Removes table and data
- TRUNCATE: Removes data only
- DROP: Cannot rollback
- TRUNCATE: Can rollback in some DBMS

---

## 18. CONSTRAINTS

### PRIMARY KEY

```sql
-- Primary key constraint
CREATE TABLE users (
    id INT PRIMARY KEY,
    name VARCHAR(100)
);

-- Add primary key
ALTER TABLE users ADD PRIMARY KEY (id);
```

### FOREIGN KEY

```sql
-- Foreign key constraint
CREATE TABLE orders (
    id INT PRIMARY KEY,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Add foreign key
ALTER TABLE orders ADD FOREIGN KEY (user_id) REFERENCES users(id);
```

### UNIQUE

```sql
-- Unique constraint
CREATE TABLE users (
    id INT PRIMARY KEY,
    email VARCHAR(100) UNIQUE
);

-- Add unique constraint
ALTER TABLE users ADD CONSTRAINT unique_email UNIQUE (email);
```

### NOT NULL

```sql
-- Not null constraint
CREATE TABLE users (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Add not null constraint
ALTER TABLE users MODIFY COLUMN name VARCHAR(100) NOT NULL;
```

### CHECK

```sql
-- Check constraint
CREATE TABLE users (
    id INT PRIMARY KEY,
    age INT CHECK (age >= 18)
);

-- Add check constraint
ALTER TABLE users ADD CONSTRAINT chk_age CHECK (age >= 18);
```

### DEFAULT

```sql
-- Default constraint
CREATE TABLE users (
    id INT PRIMARY KEY,
    status VARCHAR(20) DEFAULT 'active'
);

-- Add default constraint
ALTER TABLE users ALTER COLUMN status SET DEFAULT 'active';
```

### Interview Questions

**Q1: What are constraints in SQL?**
- Rules for data integrity
- Enforce data validity
- Prevent invalid data
- Types: PRIMARY KEY, FOREIGN KEY, UNIQUE, NOT NULL, CHECK

**Q2: What is a PRIMARY KEY constraint?**
- Unique identifier for each row
- Cannot be NULL
- Only one per table
- Enforces uniqueness

**Q3: What is a FOREIGN KEY constraint?**
- References another table's primary key
- Enforces referential integrity
- Can be NULL
- Prevents orphan records

---

## 19. STORED PROCEDURES

### Create Stored Procedure

```sql
-- Create stored procedure
CREATE PROCEDURE get_users_by_country(IN country_name VARCHAR(100))
BEGIN
    SELECT * FROM users WHERE country = country_name;
END;
```

### Call Stored Procedure

```sql
-- Call stored procedure
CALL get_users_by_country('USA');
```

### Stored Procedure with Parameters

```sql
-- Procedure with multiple parameters
CREATE PROCEDURE update_user(
    IN user_id INT,
    IN user_name VARCHAR(100),
    IN user_email VARCHAR(100)
)
BEGIN
    UPDATE users SET name = user_name, email = user_email WHERE id = user_id;
END;
```

### Stored Procedure with Output

```sql
-- Procedure with output parameter
CREATE PROCEDURE get_user_count(OUT user_count INT)
BEGIN
    SELECT COUNT(*) INTO user_count FROM users;
END;
```

### Interview Questions

**Q1: What is a stored procedure?**
- Precompiled SQL code
- Stored in database
- Can accept parameters
- Can return values

**Q2: What are the benefits of stored procedures?**
- Improved performance
- Reduced network traffic
- Code reusability
- Security

**Q3: How do you call a stored procedure?**
- Use CALL or EXEC
- Pass parameters
- Example: CALL procedure_name(param)
- Can return values

---

## 20. FUNCTIONS

### Create Function

```sql
-- Create function
CREATE FUNCTION get_user_age(user_id INT) RETURNS INT
BEGIN
    DECLARE user_age INT;
    SELECT age INTO user_age FROM users WHERE id = user_id;
    RETURN user_age;
END;
```

### Use Function

```sql
-- Use function in query
SELECT name, get_user_age(id) AS age FROM users;
```

### Built-in Functions

```sql
-- String functions
SELECT UPPER(name), LOWER(name), LENGTH(name) FROM users;

-- Date functions
SELECT CURRENT_DATE, CURRENT_TIME, NOW();

-- Mathematical functions
SELECT ABS(-10), ROUND(3.14), CEIL(3.14), FLOOR(3.14);
```

### Interview Questions

**Q1: What is a function in SQL?**
- Returns a value
- Can be used in queries
- Can accept parameters
- Example: get_user_age(id)

**Q2: What is the difference between stored procedure and function?**
- Procedure: Can return multiple values
- Function: Returns single value
- Procedure: Can use DML
- Function: Cannot use DML (in some DBMS)

**Q3: What are built-in SQL functions?**
- String: UPPER, LOWER, LENGTH
- Date: CURRENT_DATE, NOW
- Math: ABS, ROUND, CEIL

---

## 21. TRIGGERS

### Create Trigger

```sql
-- Create trigger
CREATE TRIGGER before_user_insert
BEFORE INSERT ON users
FOR EACH ROW
BEGIN
    SET NEW.created_at = CURRENT_TIMESTAMP;
END;
```

### Trigger for Audit

```sql
-- Audit trigger
CREATE TRIGGER after_user_update
AFTER UPDATE ON users
FOR EACH ROW
BEGIN
    INSERT INTO audit_log (table_name, operation, record_id, old_value, new_value)
    VALUES ('users', 'UPDATE', NEW.id, OLD.name, NEW.name);
END;
```

### Drop Trigger

```sql
-- Drop trigger
DROP TRIGGER before_user_insert;
```

### Interview Questions

**Q1: What is a trigger in SQL?**
- Automatically executes on event
- Associated with table
- Can be BEFORE or AFTER
- Executes for each row

**Q2: When would you use triggers?**
- Audit logging
- Data validation
- Automatic updates
- Enforce business rules

**Q3: What are the types of triggers?**
- BEFORE: Before operation
- AFTER: After operation
- Can be for INSERT, UPDATE, DELETE
- Executes for each row

---

## 22. SQL INJECTION

### What is SQL Injection?
SQL injection is a code injection technique where malicious SQL statements are inserted into input fields to manipulate the database.

### Vulnerable Query

```sql
-- Vulnerable to SQL injection
SELECT * FROM users WHERE name = '" + userInput + "';

-- If userInput is: ' OR '1'='1
-- Becomes: SELECT * FROM users WHERE name = '' OR '1'='1'
-- Returns all users
```

### Prevention

**Parameterized Queries:**
```sql
-- Safe parameterized query
PreparedStatement stmt = connection.prepareStatement(
    "SELECT * FROM users WHERE name = ?");
stmt.setString(1, userInput);
```

**Input Validation:**
```sql
-- Validate input before using
if (userInput.matches("[a-zA-Z ]+")) {
    // Safe to use
}
```

**Stored Procedures:**
```sql
-- Use stored procedures
CALL get_user_by_name(?);
```

### Interview Questions

**Q1: What is SQL injection?**
- Code injection technique
- Malicious SQL in input
- Manipulates database
- Can steal/destroy data

**Q2: How do you prevent SQL injection?**
- Use parameterized queries
- Validate input
- Use stored procedures
- Escape special characters

**Q3: What is a parameterized query?**
- Uses placeholders for parameters
- Database handles escaping
- Prevents SQL injection
- Example: SELECT * WHERE name = ?

---

## 23. DATABASE TESTING

### Test Data Setup

```sql
-- Insert test data
INSERT INTO users (name, email, age) VALUES ('Test User', 'test@example.com', 25);
```

### Validate Data

```sql
-- Verify data insertion
SELECT * FROM users WHERE email = 'test@example.com';

-- Verify data update
SELECT * FROM users WHERE id = 1;

-- Verify data deletion
SELECT * FROM users WHERE id = 1;  -- Should return no rows
```

### Test Constraints

```sql
-- Test primary key constraint
INSERT INTO users (id, name) VALUES (1, 'User1');
INSERT INTO users (id, name) VALUES (1, 'User2');  -- Should fail

-- Test foreign key constraint
INSERT INTO orders (user_id) VALUES (999);  -- Should fail if user doesn't exist
```

### Test Transactions

```sql
-- Test rollback
BEGIN TRANSACTION;
UPDATE users SET age = 26 WHERE id = 1;
ROLLBACK;
SELECT * FROM users WHERE id = 1;  -- Age should be unchanged

-- Test commit
BEGIN TRANSACTION;
UPDATE users SET age = 26 WHERE id = 1;
COMMIT;
SELECT * FROM users WHERE id = 1;  -- Age should be 26
```

### Interview Questions

**Q1: How do you test database operations?**
- Verify INSERT, UPDATE, DELETE
- Test constraints
- Test transactions
- Validate data integrity

**Q2: How do you test database constraints?**
- Try to violate constraints
- Verify error handling
- Test primary key uniqueness
- Test foreign key references

**Q3: How do you test database transactions?**
- Test COMMIT
- Test ROLLBACK
- Verify data persistence
- Test isolation levels

---

## 24. PERFORMANCE OPTIMIZATION

### Indexing

```sql
-- Create index on frequently queried columns
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_order_date ON orders(order_date);
```

### Query Optimization

```sql
-- Use specific columns instead of *
SELECT id, name FROM users;  -- Better than SELECT *

-- Use WHERE instead of HAVING when possible
SELECT department, AVG(salary) 
FROM employees 
WHERE salary > 30000  -- Better than HAVING
GROUP BY department;

-- Use EXISTS instead of IN for large datasets
SELECT * FROM users 
WHERE EXISTS (SELECT 1 FROM orders WHERE user_id = users.id);  -- Better than IN
```

### Avoid Functions in WHERE

```sql
-- Bad: Function in WHERE
SELECT * FROM users WHERE YEAR(created_at) = 2023;

-- Good: Range query
SELECT * FROM users WHERE created_at >= '2023-01-01' AND created_at < '2024-01-01';
```

### Use JOIN Instead of Subqueries

```sql
-- Bad: Subquery
SELECT * FROM users WHERE id IN (SELECT user_id FROM orders);

-- Good: JOIN
SELECT DISTINCT users.* FROM users JOIN orders ON users.id = orders.user_id;
```

### Interview Questions

**Q1: How do you optimize SQL queries?**
- Use indexes
- Select specific columns
- Use WHERE instead of HAVING
- Use EXISTS instead of IN

**Q2: What is query execution plan?**
- Shows how query executes
- Identifies bottlenecks
- Helps optimize queries
- Use EXPLAIN command

**Q3: How do indexes improve performance?**
- Faster data retrieval
- Reduces I/O operations
- Speeds up WHERE, JOIN, ORDER BY
- Like book index

---

## 25. COMMON INTERVIEW QUESTIONS

### SQL Fundamentals

**Q1: What is SQL?**
- Structured Query Language
- Used for database operations
- Standard for relational databases
- Query, update, manage data

**Q2: What are the types of SQL statements?**
- DDL: Create, Alter, Drop
- DML: Select, Insert, Update, Delete
- DCL: Grant, Revoke
- TCL: Commit, Rollback

**Q3: What is a primary key?**
- Unique identifier
- Cannot be NULL
- One per table
- Enforces uniqueness

### SELECT and WHERE

**Q4: What is the difference between WHERE and HAVING?**
- WHERE: Filters rows before grouping
- HAVING: Filters groups after grouping
- WHERE: Cannot use aggregates
- HAVING: Can use aggregates

**Q5: What is the difference between = and LIKE?**
- =: Exact match
- LIKE: Pattern matching
- =: Case-sensitive
- LIKE: Can use wildcards

**Q6: How do you handle NULL values?**
- Use IS NULL
- Use IS NOT NULL
- Cannot use = or !=
- Example: WHERE email IS NULL

### JOINs

**Q7: What are the different types of JOINs?**
- INNER JOIN: Matching rows only
- LEFT JOIN: All from left, matching from right
- RIGHT JOIN: All from right, matching from left
- FULL OUTER JOIN: All from both tables

**Q8: What is the difference between INNER JOIN and LEFT JOIN?**
- INNER: Only matching rows
- LEFT: All from left table
- INNER: No NULLs from join
- LEFT: NULLs for non-matching

**Q9: What is a SELF JOIN?**
- Join table to itself
- Use aliases to distinguish
- Used for hierarchical data
- Example: Employee and Manager

### Aggregates and Grouping

**Q10: What are aggregate functions?**
- Functions that perform calculations
- Operate on multiple rows
- Return single value
- Examples: COUNT, SUM, AVG

**Q11: What is the difference between COUNT(*) and COUNT(column)?**
- COUNT(*): Counts all rows
- COUNT(column): Counts non-null values
- COUNT(*) includes NULLs
- COUNT(column) excludes NULLs

**Q12: What is GROUP BY used for?**
- Group rows with same values
- Used with aggregate functions
- Summarize data
- Example: GROUP BY department

### Subqueries

**Q13: What is a subquery?**
- Query within another query
- Nested SELECT statement
- Can return single or multiple rows
- Used in WHERE, FROM, SELECT

**Q14: What is a correlated subquery?**
- References outer query
- Executes for each row
- Slower than non-correlated
- Example: WHERE salary > (SELECT AVG(salary) FROM employees WHERE department = e.department)

**Q15: What is the difference between IN and EXISTS?**
- IN: Compares values
- EXISTS: Checks existence
- IN: Can use with subquery
- EXISTS: More efficient for large datasets

### DML and DDL

**Q16: What is the difference between DELETE and TRUNCATE?**
- DELETE: Can use WHERE, slower
- TRUNCATE: Cannot use WHERE, faster
- DELETE: Can rollback
- TRUNCATE: Cannot rollback in some DBMS

**Q17: What is the difference between DROP and TRUNCATE?**
- DROP: Removes table and data
- TRUNCATE: Removes data only
- DROP: Cannot rollback
- TRUNCATE: Can rollback in some DBMS

**Q18: How do you insert data into a table?**
- Use INSERT statement
- Specify columns and values
- Can insert multiple rows
- Can insert from SELECT

### Constraints

**Q19: What are constraints in SQL?**
- Rules for data integrity
- Enforce data validity
- Prevent invalid data
- Types: PRIMARY KEY, FOREIGN KEY, UNIQUE, NOT NULL, CHECK

**Q20: What is a FOREIGN KEY constraint?**
- References another table's primary key
- Enforces referential integrity
- Can be NULL
- Prevents orphan records

### Performance

**Q21: How do you optimize SQL queries?**
- Use indexes
- Select specific columns
- Use WHERE instead of HAVING
- Use EXISTS instead of IN

**Q22: What is an index?**
- Data structure for fast search
- Improves query performance
- Created on columns
- Like book index

**Q23: What are the benefits and drawbacks of indexes?**
- Benefits: Faster retrieval, improved performance
- Drawbacks: Slower INSERT/UPDATE/DELETE, uses space
- Need to balance
- Not all columns need indexes

### Transactions

**Q24: What are ACID properties?**
- Atomicity: All or nothing
- Consistency: Valid state
- Isolation: No interference
- Durability: Permanent

**Q25: What are transaction isolation levels?**
- READ UNCOMMITTED: Lowest
- READ COMMITTED: Can read committed
- REPEATABLE READ: Same read
- SERIALIZABLE: Highest

### Security

**Q26: What is SQL injection?**
- Code injection technique
- Malicious SQL in input
- Manipulates database
- Can steal/destroy data

**Q27: How do you prevent SQL injection?**
- Use parameterized queries
- Validate input
- Use stored procedures
- Escape special characters

### Scenario-Based

**Q28: How do you find duplicate records in a table?**
- Use GROUP BY with HAVING
- Example: SELECT name, COUNT(*) FROM users GROUP BY name HAVING COUNT(*) > 1
- Can also use window functions
- DELETE duplicates with ROW_NUMBER()

**Q29: How do you get the Nth highest salary?**
- Use LIMIT and OFFSET
- Use subquery with ORDER BY
- Use DENSE_RANK() window function
- Example: SELECT DISTINCT salary FROM employees ORDER BY salary DESC LIMIT 1 OFFSET N-1

**Q30: How do you find the second highest salary?**
- Use subquery with MAX
- Use LIMIT and OFFSET
- Use DENSE_RANK()
- Example: SELECT MAX(salary) FROM employees WHERE salary < (SELECT MAX(salary) FROM employees)

---

## CONCLUSION

This comprehensive guide covers all essential SQL topics for QA interview preparation. Key takeaways:

1. **SQL Fundamentals**: DDL, DML, DCL, TCL
2. **SELECT**: Retrieve data with conditions
3. **WHERE**: Filter records with conditions
4. **Aggregates**: COUNT, SUM, AVG, MIN, MAX
5. **GROUP BY**: Group data for aggregation
6. **HAVING**: Filter grouped data
7. **JOINs**: Combine data from multiple tables
8. **Subqueries**: Nested queries for complex logic
9. **Indexes**: Improve query performance
10. **Transactions**: ACID properties, isolation levels
11. **Constraints**: Ensure data integrity
12. **Security**: Prevent SQL injection
13. **Performance**: Optimize queries with indexes and best practices
14. **Database Testing**: Validate data and operations

Practice these SQL queries with real databases and be prepared to explain the "why" behind each approach. Good luck with your interview!
