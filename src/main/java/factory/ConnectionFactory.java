package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "fatec");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Connection cn = null;
        try {
            cn = getConnection();
            if (cn != null) {
                System.out.println("Status da conexão: " + !cn.isClosed());
            }
        } catch (SQLException e) {
            System.err.println("Erro de conexão: " + e.getMessage());
        } finally {
            if (cn != null) {
                try {
                    cn.close();
                    System.out.println("Conexão fechada.");
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar a conexão: " + e.getMessage());
                }
            }
        }
    }
}
