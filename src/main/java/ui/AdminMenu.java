package ui;

import model.Question;
import model.Result;
import model.User;
import service.QuizService;
import service.UserService;
import util.InputUtil;

import java.util.List;

public class AdminMenu extends BaseMenu {
    private final User currentUser;
    private final QuizService quizService;
    private final UserService userService;

    public AdminMenu(User currentUser) {
        this.currentUser = currentUser;
        this.quizService = new QuizService();
        this.userService = new UserService();
    }

    @Override
    protected void showMenu() {
        showHeader("ADMIN DASHBOARD - Welcome, " + currentUser.getUsername() + "!");
        System.out.println("1. Add New Question");
        System.out.println("2. View All Questions");
        System.out.println("3. Delete Question");
        System.out.println("4. View All Results");
        System.out.println("5. Manage Users");
        System.out.println("0. Logout");
    }

    @Override
    public void handleChoice() {
        int choice = getMenuChoice(5);
        
        switch (choice) {
            case 1:
                addNewQuestion();
                break;
            case 2:
                viewAllQuestions();
                break;
            case 3:
                deleteQuestion();
                break;
            case 4:
                viewAllResults();
                break;
            case 5:
                manageUsers();
                break;
            case 0:
                showMessage("Logging out...");
                isRunning = false;
                break;
            default:
                showError("Invalid choice. Please try again.");
        }
    }

    private void addNewQuestion() {
        showHeader("ADD NEW QUESTION");
        
        String questionText = InputUtil.getStringInput("Enter the question: ");
        String optionA = InputUtil.getStringInput("Enter option A: ");
        String optionB = InputUtil.getStringInput("Enter option B: ");
        String optionC = InputUtil.getStringInput("Enter option C: ");
        String optionD = InputUtil.getStringInput("Enter option D: ");
        char correctOption = InputUtil.getCharInput("Enter the correct option (A/B/C/D): ");
        
        Question question = new Question();
        question.setQuestionText(questionText);
        question.setOptionA(optionA);
        question.setOptionB(optionB);
        question.setOptionC(optionC);
        question.setOptionD(optionD);
        question.setCorrectOption(correctOption);
        
        if (quizService.addQuestion(question)) {
            showMessage("Question added successfully!");
        } else {
            showError("Failed to add question. Please try again.");
        }
        pause();
    }

    private void viewAllQuestions() {
        showHeader("ALL QUESTIONS");
        List<Question> questions = quizService.getAllQuestions();
        
        if (questions.isEmpty()) {
            showMessage("No questions found.");
        } else {
            questions.forEach(System.out::println);
        }
        pause();
    }

    private void deleteQuestion() {
        viewAllQuestions();
        int questionId = InputUtil.getIntInput("\nEnter the question ID to delete: ", 1, Integer.MAX_VALUE);
        
        if (quizService.deleteQuestion(questionId)) {
            showMessage("Question deleted successfully!");
        } else {
            showError("Failed to delete question. Please check the question ID and try again.");
        }
        pause();
    }

    private void viewAllResults() {
        showHeader("ALL QUIZ RESULTS");
        List<Result> results = quizService.getAllResults();
        
        if (results.isEmpty()) {
            showMessage("No results found.");
        } else {
            System.out.printf("%-5s %-15s %-10s %-20s%n", "ID", "Username", "Score", "Taken On");
            System.out.println("-".repeat(50));
            
            for (Result result : results) {
                System.out.printf("%-5d %-15s %-10d %-20s%n",
                        result.getId(),
                        result.getUsername(),
                        result.getScore(),
                        result.getTakenOn());
            }
        }
        pause();
    }

    private void manageUsers() {
        showHeader("MANAGE USERS");
        System.out.println("1. View All Users");
        System.out.println("2. Add New User");
        System.out.println("3. Delete User");
        System.out.println("0. Back to Main Menu");
        
        int choice = InputUtil.getIntInput("Enter your choice: ", 0, 3);
        
        switch (choice) {
            case 1:
                viewAllUsers();
                break;
            case 2:
                addNewUser();
                break;
            case 3:
                deleteUser();
                break;
            case 0:
                return;
        }
        pause();
    }

    private void viewAllUsers() {
        showHeader("ALL USERS");
        List<User> users = userService.getAllUsers();
        
        if (users.isEmpty()) {
            showMessage("No users found.");
        } else {
            System.out.printf("%-5s %-15s %-10s%n", "ID", "Username", "Role");
            System.out.println("-".repeat(30));
            
            for (User user : users) {
                System.out.printf("%-5d %-15s %-10s%n",
                        user.getId(),
                        user.getUsername(),
                        user.getRole());
            }
        }
    }

    private void addNewUser() {
        showHeader("ADD NEW USER");
        
        String username = InputUtil.getStringInput("Enter username: ");
        String password = InputUtil.getPassword("Enter password: ");
        String role = InputUtil.getStringInput("Enter role (ADMIN/USER): ").toUpperCase();
        
        if (!role.equals("ADMIN") && !role.equals("USER")) {
            showError("Invalid role. Defaulting to USER.");
            role = "USER";
        }
        
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(role);
        
        if (userService.addUser(newUser)) {
            showMessage("User added successfully!");
        } else {
            showError("Failed to add user. Username might already exist.");
        }
    }

    private void deleteUser() {
        viewAllUsers();
        int userId = InputUtil.getIntInput("\nEnter the user ID to delete: ", 1, Integer.MAX_VALUE);
        
        // Prevent deleting self
        if (userId == currentUser.getId()) {
            showError("You cannot delete your own account!");
            return;
        }
        
        if (userService.deleteUser(userId)) {
            showMessage("User deleted successfully!");
        } else {
            showError("Failed to delete user. Please check the user ID and try again.");
        }
    }
}
