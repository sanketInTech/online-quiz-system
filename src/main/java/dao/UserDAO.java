package dao;

import model.User;
import util.DBConnection;
import util.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    
    public User authenticate(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    if (PasswordUtil.verifyPassword(password, storedHash)) {
                        return extractUserFromResultSet(rs);
                    }
                }
            }
        }
        return null;
    }

    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        }
        return users;
    }

    public boolean addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, PasswordUtil.hashPassword(user.getPassword()));
            stmt.setString(3, user.getRole());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                return false;
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        }
    }

    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, password = ?, role = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, PasswordUtil.hashPassword(user.getPassword()));
            stmt.setString(3, user.getRole());
            stmt.setInt(4, user.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        return user;
    }
}
