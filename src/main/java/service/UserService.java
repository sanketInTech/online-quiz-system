package service;

import dao.UserDAO;
import model.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public User authenticate(String username, String password) {
        try {
            return userDAO.authenticate(username, password);
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
            return null;
        }
    }

    public List<User> getAllUsers() {
        try {
            return userDAO.getAllUsers();
        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
            return List.of();
        }
    }

    public User getUserById(int id) {
        try {
            return userDAO.getUserById(id);
        } catch (SQLException e) {
            System.err.println("Error fetching user: " + e.getMessage());
            return null;
        }
    }

    public boolean addUser(User user) {
        try {
            return userDAO.addUser(user);
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
            return false;
        }
    }

    public boolean updateUser(User user) {
        try {
            return userDAO.updateUser(user);
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(int id) {
        try {
            return userDAO.deleteUser(id);
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }
}
