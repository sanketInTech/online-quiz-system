package dao;

import model.Question;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionDAO {
    
    public Question getQuestionById(int id) throws SQLException {
        String sql = "SELECT * FROM questions WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractQuestionFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Question> getAllQuestions() throws SQLException {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                questions.add(extractQuestionFromResultSet(rs));
            }
        }
        return questions;
    }

    public List<Question> getRandomQuestions(int count) throws SQLException {
        List<Question> allQuestions = getAllQuestions();
        List<Question> randomQuestions = new ArrayList<>();
        
        if (allQuestions.size() <= count) {
            return allQuestions;
        }
        
        Random random = new Random();
        while (randomQuestions.size() < count && !allQuestions.isEmpty()) {
            int randomIndex = random.nextInt(allQuestions.size());
            randomQuestions.add(allQuestions.remove(randomIndex));
        }
        
        return randomQuestions;
    }

    public boolean addQuestion(Question question) throws SQLException {
        String sql = "INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_option) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, question.getQuestionText());
            stmt.setString(2, question.getOptionA());
            stmt.setString(3, question.getOptionB());
            stmt.setString(4, question.getOptionC());
            stmt.setString(5, question.getOptionD());
            stmt.setString(6, String.valueOf(question.getCorrectOption()));
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                return false;
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    question.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        }
    }

    public boolean updateQuestion(Question question) throws SQLException {
        String sql = "UPDATE questions SET question_text = ?, option_a = ?, option_b = ?, option_c = ?, " +
                    "option_d = ?, correct_option = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, question.getQuestionText());
            stmt.setString(2, question.getOptionA());
            stmt.setString(3, question.getOptionB());
            stmt.setString(4, question.getOptionC());
            stmt.setString(5, question.getOptionD());
            stmt.setString(6, String.valueOf(question.getCorrectOption()));
            stmt.setInt(7, question.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteQuestion(int id) throws SQLException {
        String sql = "DELETE FROM questions WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    private Question extractQuestionFromResultSet(ResultSet rs) throws SQLException {
        Question question = new Question();
        question.setId(rs.getInt("id"));
        question.setQuestionText(rs.getString("question_text"));
        question.setOptionA(rs.getString("option_a"));
        question.setOptionB(rs.getString("option_b"));
        question.setOptionC(rs.getString("option_c"));
        question.setOptionD(rs.getString("option_d"));
        question.setCorrectOption(rs.getString("correct_option").charAt(0));
        return question;
    }
}
