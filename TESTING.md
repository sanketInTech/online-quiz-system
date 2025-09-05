# Testing the Online Quiz System

This document outlines the test cases to verify the functionality of the Online Quiz System.

## Test Environment Setup

1. **Database Setup**
   - Ensure MySQL server is running
   - Database `quiz_system` is created and initialized with sample data
   - Test user accounts exist (admin/user1/user2)

2. **Application Setup**
   - Application is built with `mvn clean package`
   - All dependencies are resolved
   - Database connection details are correctly configured

## Test Cases

### 1. User Authentication

#### 1.1 Admin Login
- [ ] Launch the application
- [ ] Select option 1 to login
- [ ] Enter username: admin
- [ ] Enter password: admin123
- [ ] Verify: Admin menu is displayed

#### 1.2 Regular User Login
- [ ] Launch the application
- [ ] Select option 1 to login
- [ ] Enter username: user1
- [ ] Enter password: pass123
- [ ] Verify: User menu is displayed

#### 1.3 Invalid Login
- [ ] Launch the application
- [ ] Select option 1 to login
- [ ] Enter invalid credentials
- [ ] Verify: Error message is displayed

### 2. Admin Functionality

#### 2.1 Add New Question
- [ ] Login as admin
- [ ] Select option 1 (Add New Question)
- [ ] Enter question details
- [ ] Verify: Question is added successfully

#### 2.2 View All Questions
- [ ] Login as admin
- [ ] Select option 2 (View All Questions)
- [ ] Verify: List of questions is displayed

#### 2.3 Delete Question
- [ ] Login as admin
- [ ] Select option 3 (Delete Question)
- [ ] Enter a valid question ID
- [ ] Verify: Question is deleted successfully

### 3. User Functionality

#### 3.1 Take a Quiz
- [ ] Login as user
- [ ] Select option 1 (Start New Quiz)
- [ ] Answer all questions
- [ ] Verify: Score is displayed at the end

#### 3.2 View Results
- [ ] Login as user
- [ ] Select option 2 (View My Results)
- [ ] Verify: Quiz history and statistics are displayed

## Test Data

### Sample Questions
1. What is the capital of France?
   - A) London
   - B) Paris ✓
   - C) Berlin
   - D) Madrid

2. Which planet is known as the Red Planet?
   - A) Venus
   - B) Mars ✓
   - C) Jupiter
   - D) Saturn

## Expected Output

### Admin Menu
```
ADMIN DASHBOARD - Welcome, admin!
1. Add New Question
2. View All Questions
3. Delete Question
4. View All Results
5. Manage Users
0. Logout
```

### User Menu
```
USER DASHBOARD - Welcome, user1!
1. Start New Quiz
2. View My Results
0. Logout
```

## Common Issues and Solutions

1. **Database Connection Failed**
   - Verify MySQL service is running
   - Check database credentials in DBConnection.java
   - Ensure the database and user exist with correct permissions

2. **Build Failures**
   - Run `mvn clean install` to refresh dependencies
   - Check for any compilation errors

3. **Application Crashes**
   - Check the console for error messages
   - Verify all required database tables exist
   - Ensure all environment variables are set correctly
