-- Create database
CREATE DATABASE IF NOT EXISTS quiz_system;
USE quiz_system;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'USER') NOT NULL
);

-- Questions table
CREATE TABLE IF NOT EXISTS questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_text TEXT NOT NULL,
    option_a VARCHAR(255) NOT NULL,
    option_b VARCHAR(255) NOT NULL,
    option_c VARCHAR(255) NOT NULL,
    option_d VARCHAR(255) NOT NULL,
    correct_option CHAR(1) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Results table
CREATE TABLE IF NOT EXISTS results (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    score INT NOT NULL,
    taken_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert admin user (password: admin123)
INSERT INTO users (username, password, role) VALUES 
('admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'ADMIN');

-- Insert sample users (password: pass123)
INSERT INTO users (username, password, role) VALUES 
('user1', '1a1dc91c907325c69271ddf0c944bc72', 'USER'),
('user2', '1a1dc91c907325c69271ddf0c944bc72', 'USER');

-- Insert sample questions
INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
('What is the capital of France?', 'London', 'Paris', 'Berlin', 'Madrid', 'B'),
('Which planet is known as the Red Planet?', 'Venus', 'Mars', 'Jupiter', 'Saturn', 'B'),
('What is 2 + 2?', '3', '4', '5', '6', 'B'),
('Which language is used for Android development?', 'Java', 'Python', 'C++', 'JavaScript', 'A'),
('What is the largest mammal in the world?', 'Elephant', 'Blue Whale', 'Giraffe', 'Polar Bear', 'B');

-- Insert sample results
INSERT INTO results (user_id, score, taken_on) VALUES
(2, 4, '2023-01-01 10:00:00'),
(3, 3, '2023-01-02 11:30:00'),
(2, 5, '2023-01-03 14:45:00');
