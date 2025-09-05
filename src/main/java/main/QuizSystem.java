package main;

import ui.LoginMenu;
import util.DBConnection;

public class QuizSystem {
    public static void main(String[] args) {
        try {
            // Initialize database connection
            DBConnection.getConnection();
            
            // Start the application with login menu
            LoginMenu loginMenu = new LoginMenu();
            loginMenu.display();
            
        } catch (Exception e) {
            System.err.println("Error starting the application: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close database connection when application exits
            DBConnection.closeConnection();
        }
    }
}
