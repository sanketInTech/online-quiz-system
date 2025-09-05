package ui;

import model.Question;
import model.Result;
import model.User;
import service.QuizService;
import util.InputUtil;

import java.util.List;

public class UserMenu extends BaseMenu {
    private static final int QUIZ_QUESTION_COUNT = 5;
    private final User currentUser;
    private final QuizService quizService;

    public UserMenu(User currentUser) {
        this.currentUser = currentUser;
        this.quizService = new QuizService();
    }

    @Override
    protected void showMenu() {
        showHeader("USER DASHBOARD - Welcome, " + currentUser.getUsername() + "!");
        System.out.println("1. Start New Quiz");
        System.out.println("2. View My Results");
        System.out.println("0. Logout");
    }

    @Override
    public void handleChoice() {
        int choice = getMenuChoice(2);
        
        switch (choice) {
            case 1:
                startNewQuiz();
                break;
            case 2:
                viewMyResults();
                break;
            case 0:
                showMessage("Logging out...");
                isRunning = false;
                break;
            default:
                showError("Invalid choice. Please try again.");
        }
    }

    private void startNewQuiz() {
        showHeader("NEW QUIZ - " + QUIZ_QUESTION_COUNT + " Questions");
        List<Question> questions = quizService.getQuizQuestions(QUIZ_QUESTION_COUNT);
        
        if (questions.isEmpty()) {
            showError("No questions available. Please contact an administrator.");
            return;
        }
        
        int score = 0;
        int questionNumber = 1;
        
        for (Question question : questions) {
            System.out.printf("\nQuestion %d/%d:\n", questionNumber++, questions.size());
            System.out.println(question.getQuestionText());
            System.out.println("A) " + question.getOptionA());
            System.out.println("B) " + question.getOptionB());
            System.out.println("C) " + question.getOptionC());
            System.out.println("D) " + question.getOptionD());
            
            char userAnswer = InputUtil.getCharInput("Your answer (A/B/C/D): ");
            
            if (Character.toUpperCase(userAnswer) == question.getCorrectOption()) {
                System.out.println("✅ Correct!");
                score++;
            } else {
                System.out.printf("❌ Incorrect! The correct answer was %c.\n", question.getCorrectOption());
            }
            
            System.out.println();
        }
        
        // Save the result
        Result result = new Result();
        result.setUserId(currentUser.getId());
        result.setScore(score);
        
        if (quizService.saveQuizResult(result)) {
            showQuizResult(score, questions.size());
        } else {
            showError("Failed to save your quiz result.");
        }
        
        pause();
    }

    private void showQuizResult(int score, int totalQuestions) {
        double percentage = (double) score / totalQuestions * 100;
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("  QUIZ COMPLETED!");
        System.out.println("  Score: " + score + "/" + totalQuestions);
        System.out.printf("  Percentage: %.1f%%\n", percentage);
        
        if (percentage >= 80) {
            System.out.println("  🎉 Excellent work! You're a quiz master! 🎉");
        } else if (percentage >= 60) {
            System.out.println("  👍 Good job! You passed!");
        } else if (percentage >= 40) {
            System.out.println("  🤔 You passed, but you can do better!");
        } else {
            System.out.println("  📚 Keep practicing!");
        }
        
        System.out.println("=".repeat(50));
    }

    private void viewMyResults() {
        showHeader("MY QUIZ RESULTS");
        List<Result> results = quizService.getUserResults(currentUser.getId());
        
        if (results.isEmpty()) {
            showMessage("You haven't taken any quizzes yet.");
        } else {
            System.out.printf("%-5s %-10s %-20s%n", "ID", "Score", "Taken On");
            System.out.println("-".repeat(40));
            
            for (Result result : results) {
                System.out.printf("%-5d %-10d %-20s%n",
                        result.getId(),
                        result.getScore(),
                        result.getTakenOn());
            }
            
            // Show statistics
            double averageScore = results.stream()
                    .mapToInt(Result::getScore)
                    .average()
                    .orElse(0.0);
            
            int bestScore = results.stream()
                    .mapToInt(Result::getScore)
                    .max()
                    .orElse(0);
            
            System.out.println("\nStatistics:");
            System.out.println("Total Quizzes: " + results.size());
            System.out.printf("Average Score: %.1f\n", averageScore);
            System.out.println("Best Score: " + bestScore);
        }
        
        pause();
    }
}
