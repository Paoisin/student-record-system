-- ============================================================
-- Student Record Management System
-- Database Schema
-- ============================================================

-- Create database
CREATE DATABASE IF NOT EXISTS student_management;

-- Use the database
USE student_management;

-- Create students table
CREATE TABLE IF NOT EXISTS students (
    student_id      INT PRIMARY KEY AUTO_INCREMENT,
    student_name    VARCHAR(100) NOT NULL,
    email           VARCHAR(100) UNIQUE NOT NULL,
    age             INT NOT NULL,
    course          VARCHAR(50) NOT NULL,
    gpa             DECIMAL(3,2) NOT NULL,
    enrollment_date DATE NOT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Sample data (optional - for testing)
INSERT INTO students (student_name, email, age, course, gpa, enrollment_date)
VALUES ('Juan Dela Cruz', 'juan@email.com', 20, 'CS', 3.50, '2024-01-15');

INSERT INTO students (student_name, email, age, course, gpa, enrollment_date)
VALUES ('Maria Santos', 'maria@email.com', 19, 'IT', 3.80, '2024-02-20');

INSERT INTO students (student_name, email, age, course, gpa, enrollment_date)
VALUES ('Pedro Reyes', 'pedro@email.com', 21, 'IS', 3.20, '2024-03-10');

-- Verify table was created
SELECT * FROM students;
