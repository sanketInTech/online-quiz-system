package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/quiz_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static Connection connection = null;

    static {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
        }
    }

    private DBConnection() {
        // Private constructor to prevent instantiation
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            Properties connectionProps = new Properties();
            connectionProps.put("user", DB_USER);
            connectionProps.put("password", DB_PASSWORD);
            connectionProps.put("useSSL", "false");
            connectionProps.put("serverTimezone", "UTC");
            
            connection = DriverManager.getConnection(DB_URL, connectionProps);
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
