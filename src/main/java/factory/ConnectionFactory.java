package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/sgca", "root", "fatec");
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Connection testConnection = null;
        try {
            testConnection = getConnection();
            if (testConnection != null) {
                System.out.println("Connection status: " + !testConnection.isClosed());
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        } finally {
            if (testConnection != null) {
                try {
                    testConnection.close();
                    System.out.println("Connection closed.");
                } catch (SQLException e) {
                    System.err.println("Error on close connection: " + e.getMessage());
                }
            }
        }
    }
}
