package factory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static final String DB_PROPERTIES_FILE = "db.properties"; // Nome do arquivo de propriedades
    private static Properties properties = new Properties();

    // Bloco estático para carregar as propriedades do banco de dados uma vez
    static {
        try (InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream(DB_PROPERTIES_FILE)) {
            if (input == null) {
                System.err.println("Sorry, unable to find " + DB_PROPERTIES_FILE);
                // Você pode lançar uma RuntimeException aqui se o arquivo for obrigatório
                throw new RuntimeException("Arquivo de propriedades do banco de dados não encontrado: " + DB_PROPERTIES_FILE);
            }
            properties.load(input);
        } catch (IOException ex) {
            System.err.println("Error loading database properties: " + ex.getMessage());
            throw new RuntimeException("Falha ao carregar propriedades do banco de dados", ex);
        }
        
        // Opcional: Carregar o driver JDBC explicitamente (não é estritamente necessário para JDBC 4.0+)
        try {
            Class.forName(properties.getProperty("db.driver"));
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
            throw new RuntimeException("Driver JDBC não encontrado. Verifique suas dependências.", e);
        }
    }

    /**
     * Obtém uma nova conexão com o banco de dados.
     * @return Uma instância de Connection.
     * @throws SQLException Se ocorrer um erro ao estabelecer a conexão.
     */
    public static Connection getConnection() throws SQLException {
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String pass = properties.getProperty("db.password");
        return DriverManager.getConnection(url, user, pass);
    }

    /**
     * Fecha uma conexão, Statement e ResultSet de forma segura.
     * Este método é útil para cenários onde try-with-resources não é aplicável diretamente,
     * mas é sempre preferível usar try-with-resources quando possível.
     */
    public static void closeConnection(Connection conn) {
        close(conn, null, null);
    }

    public static void closeConnection(Connection conn, java.sql.PreparedStatement stmt) {
        close(conn, stmt, null);
    }

    public static void closeConnection(Connection conn, java.sql.PreparedStatement stmt, java.sql.ResultSet rs) {
        close(conn, stmt, rs);
    }

    private static void close(Connection conn, java.sql.PreparedStatement stmt, java.sql.ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error closing database resources: " + e.getMessage());
            // Logar a exceção é uma boa prática
        }
    }

    // --- Método main para Teste ---
    public static void main(String[] args) {
        Connection testConnection = null;
        try {
            testConnection = getConnection();
            if (testConnection != null) {
                System.out.println("Connection status: Conectado com sucesso!");
                System.out.println("URL: " + properties.getProperty("db.url"));
                System.out.println("User: " + properties.getProperty("db.user"));
            } else {
                System.err.println("Connection status: Falha ao obter a conexão.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao testar a conexão: " + e.getMessage());
            e.printStackTrace(); // Imprime o stack trace completo para depuração
        } finally {
            closeConnection(testConnection); // Usa o método auxiliar para fechar
        }
    }
}