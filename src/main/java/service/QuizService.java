package service;

import dao.QuestionDAO;
import dao.ResultDAO;
import model.Question;
import model.Result;

import java.sql.SQLException;
import java.util.List;

public class QuizService {
    private final QuestionDAO questionDAO;
    private final ResultDAO resultDAO;

    public QuizService() {
        this.questionDAO = new QuestionDAO();
        this.resultDAO = new ResultDAO();
    }

    public List<Question> getQuizQuestions(int count) {
        try {
            return questionDAO.getRandomQuestions(count);
        } catch (SQLException e) {
            System.err.println("Error fetching quiz questions: " + e.getMessage());
            return List.of();
        }
    }

    public Question getQuestionById(int id) {
        try {
            return questionDAO.getQuestionById(id);
        } catch (SQLException e) {
            System.err.println("Error fetching question: " + e.getMessage());
            return null;
        }
    }

    public List<Question> getAllQuestions() {
        try {
            return questionDAO.getAllQuestions();
        } catch (SQLException e) {
            System.err.println("Error fetching questions: " + e.getMessage());
            return List.of();
        }
    }

    public boolean addQuestion(Question question) {
        try {
            return questionDAO.addQuestion(question);
        } catch (SQLException e) {
            System.err.println("Error adding question: " + e.getMessage());
            return false;
        }
    }

    public boolean updateQuestion(Question question) {
        try {
            return questionDAO.updateQuestion(question);
        } catch (SQLException e) {
            System.err.println("Error updating question: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteQuestion(int id) {
        try {
            return questionDAO.deleteQuestion(id);
        } catch (SQLException e) {
            System.err.println("Error deleting question: " + e.getMessage());
            return false;
        }
    }

    public boolean saveQuizResult(Result result) {
        try {
            return resultDAO.addResult(result);
        } catch (SQLException e) {
            System.err.println("Error saving quiz result: " + e.getMessage());
            return false;
        }
    }

    public List<Result> getUserResults(int userId) {
        try {
            return resultDAO.getResultsByUserId(userId);
        } catch (SQLException e) {
            System.err.println("Error fetching user results: " + e.getMessage());
            return List.of();
        }
    }

    public List<Result> getAllResults() {
        try {
            return resultDAO.getAllResults();
        } catch (SQLException e) {
            System.err.println("Error fetching all results: " + e.getMessage());
            return List.of();
        }
    }

    public boolean deleteResult(int id) {
        try {
            return resultDAO.deleteResult(id);
        } catch (SQLException e) {
            System.err.println("Error deleting result: " + e.getMessage());
            return false;
        }
    }
}
