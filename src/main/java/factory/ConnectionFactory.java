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
        Connection cn = null;
        try {
            cn = getConnection();
            if (cn != null) {
                System.out.println("Connection status: " + !cn.isClosed());
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        } finally {
            if (cn != null) {
                try {
                    cn.close();
                    System.out.println("Connection closed.");
                } catch (SQLException e) {
                    System.err.println("Error on close connection: " + e.getMessage());
                }
            }
        }
    }
}
