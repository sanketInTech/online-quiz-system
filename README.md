# 📚 Online Quiz System

A robust, console-based Java application designed for creating and taking quizzes with secure user authentication and role-based access control. Perfect for educational institutions and training programs.

## 🚀 Features

- **User Authentication**
  - Login with username and password
  - Role-based access (Admin/User)

- **Admin Features**
  - Add/View/Delete quiz questions
  - View all quiz results
  - Manage users (Add/View/Delete)

- **User Features**
  - Take quizzes with random questions
  - View personal quiz history and statistics

## 🛠 Prerequisites

- Java 8 or later
- MySQL 5.7 or later
- Maven 3.6 or later
- Git (for cloning the repository)

## 🚀 Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/online-quiz-system.git
   cd online-quiz-system
   ```

2. **Set up the database** (see Database Setup below)

3. **Build and run the application**
   ```bash
   mvn clean package
   java -jar target/OnlineQuizSystem-1.0-SNAPSHOT.jar
   ```

## 🗄️ Database Setup

1. **Database Setup**
   ```sql
   -- Login to MySQL as root
   mysql -u root -p
   
   -- Create database and user
   CREATE DATABASE quiz_system;
   CREATE USER 'quizuser'@'localhost' IDENTIFIED BY 'quizpass';
   GRANT ALL PRIVILEGES ON quiz_system.* TO 'quizuser'@'localhost';
   FLUSH PRIVILEGES;
   
   -- Import database schema and sample data
   mysql -u quizuser -p quiz_system < src/main/resources/database.sql
   ```

2. **Database Configuration**
   Update the database connection details in `DBConnection.java` if needed:
   ```java
   private static final String DB_URL = "jdbc:mysql://localhost:3306/quiz_system";
   private static final String DB_USER = "quizuser";
   private static final String DB_PASSWORD = "quizpass";
   ```

3. **Build the Project**
   ```bash
   mvn clean package
   ```

4. **Run the Application**
   ```bash
   java -jar target/OnlineQuizSystem-1.0-SNAPSHOT.jar
   ```

## Default Login Credentials

- **Admin**
  - Username: admin
  - Password: admin123

- **Sample User**
  - Username: user1
  - Password: pass123

## Project Structure

```
src/main/java/
├── dao/              # Data Access Objects
├── model/           # Data Models
├── service/         # Business Logic
├── ui/              # Console User Interface
├── util/            # Utility Classes
└── main/            # Main Application Class
```

## Adding Questions (Admin)

1. Log in as admin
2. Select "Add New Question"
3. Enter question details and correct answer
4. Save the question

## Taking a Quiz (User)

1. Log in as a regular user
2. Select "Start New Quiz"
3. Answer the questions (A/B/C/D)
4. View your score at the end

## Viewing Results

- **Admin**: Can view all users' results
- **User**: Can view only their own results and statistics

## Troubleshooting

- **Database Connection Issues**:
  - Verify MySQL service is running
  - Check database credentials in DBConnection.java
  - Ensure the database and user exist with correct permissions

- **Build Issues**:
  - Ensure Maven and Java are properly installed
  - Run `mvn clean install` to refresh dependencies

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Built with Java and MySQL
- Uses Maven for dependency management
