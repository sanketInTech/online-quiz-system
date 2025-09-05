package dao;

import model.Result;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ResultDAO {
    
    public boolean addResult(Result result) throws SQLException {
        String sql = "INSERT INTO results (user_id, score) VALUES (?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, result.getUserId());
            stmt.setInt(2, result.getScore());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                return false;
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    result.setId(generatedKeys.getInt(1));
                    result.setTakenOn(LocalDateTime.now());
                }
            }
            return true;
        }
    }

    public List<Result> getResultsByUserId(int userId) throws SQLException {
        List<Result> results = new ArrayList<>();
        String sql = "SELECT r.*, u.username FROM results r JOIN users u ON r.user_id = u.id WHERE r.user_id = ? ORDER BY r.taken_on DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(extractResultFromResultSet(rs));
                }
            }
        }
        return results;
    }

    public List<Result> getAllResults() throws SQLException {
        List<Result> results = new ArrayList<>();
        String sql = "SELECT r.*, u.username FROM results r JOIN users u ON r.user_id = u.id ORDER BY r.taken_on DESC";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                results.add(extractResultFromResultSet(rs));
            }
        }
        return results;
    }

    public Result getResultById(int id) throws SQLException {
        String sql = "SELECT r.*, u.username FROM results r JOIN users u ON r.user_id = u.id WHERE r.id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractResultFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public boolean deleteResult(int id) throws SQLException {
        String sql = "DELETE FROM results WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    private Result extractResultFromResultSet(ResultSet rs) throws SQLException {
        Result result = new Result();
        result.setId(rs.getInt("id"));
        result.setUserId(rs.getInt("user_id"));
        result.setUsername(rs.getString("username"));
        result.setScore(rs.getInt("score"));
        
        Timestamp timestamp = rs.getTimestamp("taken_on");
        if (timestamp != null) {
            result.setTakenOn(timestamp.toLocalDateTime());
        }
        
        return result;
    }
}
