package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe utilitária para conectar-se a um banco de dados MySQL e executar
 * scripts SQL. Esta classe é projetada para criar o banco de dados 'sgca' e
 * suas tabelas 'curso' e 'aluno' conforme o script SQL fornecido.
 */
public class DatabaseConnector {

    // Configurações do banco de dados
    // A URL foi atualizada para permitir múltiplos comandos SQL em uma única chamada.
    private static final String DB_URL = "jdbc:mysql://localhost:3306/?allowMultiQueries=true"; // URL base sem o nome do banco
    private static final String DB_NAME = "sgca"; // Nome do banco de dados a ser criado
    private static final String USER = "aluno"; // Usuário do banco de dados (ajuste conforme sua configuração)
    private static final String PASS = "fatec"; // Senha do banco de dados (AJUSTE AQUI!)

    // Script SQL para criação do banco de dados e tabelas com nomes para as restrições
    // Este script foi ajustado para seguir exatamente o que foi fornecido pelo usuário.
    private static final String SQL_SCRIPT
            = "CREATE DATABASE IF NOT EXISTS " + DB_NAME + ";\n"
            + "USE " + DB_NAME + ";\n\n"
            + "-- Criação da tabela de cursos\n"
            + "CREATE TABLE IF NOT EXISTS curso (\n"
            + "    idCurso INTEGER PRIMARY KEY auto_increment,\n"
            + "    nome VARCHAR(20) NOT NULL UNIQUE,\n"
            + "    CONSTRAINT chk_curso_nome_length CHECK (LENGTH (nome) >= 3),\n"
            + "    cargaHoraria INTEGER NOT NULL,\n"
            + "    CONSTRAINT chk_curso_carga_horaria_minima CHECK (cargaHoraria >= 20),\n"
            + "    limiteAlunos INTEGER NOT NULL,\n"
            + "    CONSTRAINT chk_curso_limite_alunos_minimo CHECK (limiteAlunos >= 1),\n"
            + "    ativo TINYINT DEFAULT 1,\n"
            + "    CONSTRAINT chk_curso_ativo_valido CHECK (ativo IN (0, 1))\n"
            + ");\n\n"
            + "-- Criação da tabela de alunos\n"
            + "CREATE TABLE IF NOT EXISTS aluno (\n"
            + "    idAluno INTEGER PRIMARY KEY auto_increment,\n"
            + "    idCurso INTEGER NOT NULL,\n"
            + "    FOREIGN KEY (idCurso) REFERENCES curso (idCurso) ON DELETE CASCADE,\n"
            + "    nome VARCHAR(50) NOT NULL,\n"
            + "    cpf VARCHAR(11) UNIQUE NOT NULL,\n"
            + "    telefone VARCHAR(20),\n"
            + "    email VARCHAR(255),\n"
            + "    dataNascimento DATE,\n"
            + "    ativo TINYINT DEFAULT 1,\n"
            + "    CONSTRAINT chk_aluno_ativo_valido CHECK (ativo IN (0, 1)),\n"
            + "    CONSTRAINT chk_aluno_cpf_length CHECK (LENGTH (cpf) = 11),\n"
            + "    CONSTRAINT chk_alunos_nome_length CHECK (LENGTH (nome) >= 3),\n"
            + "    CONSTRAINT chk_aluno_email_valido CHECK (\n"
            + "    email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$'\n"
            + "    )\n"
            + ");";

    /**
     * Estabelece uma conexão com o banco de dados MySQL. Tenta conectar-se ao
     * servidor MySQL sem especificar um banco de dados inicialmente.
     *
     * @return Uma instância de Connection se a conexão for bem-sucedida, caso
     * contrário, null.
     */
    private static Connection getConnection() {
        Connection conn = null;
        try {
            // Carrega o driver JDBC do MySQL (necessário para versões mais antigas do JDBC, mas boa prática)
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Tentando conectar ao banco de dados...");
            // Agora a URL inclui 'allowMultiQueries=true' para suportar múltiplos comandos SQL
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Conexão estabelecida com sucesso!");
        } catch (SQLException se) {
            System.err.println("Erro de SQL ao conectar ao banco: " + se.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC do MySQL não encontrado. Certifique-se de que o JAR está no classpath.");
        }
        return conn;
    }

    /**
     * Executa o script SQL fornecido para criar o banco de dados e as tabelas.
     *
     * @param connection A conexão JDBC ativa com o servidor MySQL.
     */
    private static void executeSqlScript(Connection connection) {
        if (connection == null) {
            System.err.println("Conexão nula. Não é possível executar o script SQL.");
            return;
        }

        try (Statement stmt = connection.createStatement()) {
            // O MySQL permite a execução de múltiplos comandos em uma única chamada de statement
            // se o driver for configurado com allowMultiQueries=true na URL (já corrigido).
            System.out.println("Executando script SQL...");
            stmt.executeUpdate(SQL_SCRIPT);
            System.out.println("Script SQL executado com sucesso!");
        } catch (SQLException se) {
            System.err.println("Erro de SQL ao executar o script: " + se.getMessage());
        }
    }

    /**
     * Método principal para executar a criação do banco de dados e tabelas.
     *
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        Connection connection = null;
        try {
            // 1. Obter conexão com o servidor MySQL (sem especificar o banco de dados 'sgca' ainda)
            connection = getConnection();

            if (connection != null) {
                // 2. Executar o script SQL para criar o banco de dados e as tabelas
                // Note que o script inclui "CREATE DATABASE IF NOT EXISTS sgca;" e "USE sgca;"
                executeSqlScript(connection);
            }
        } finally {
            // 3. Fechar a conexão
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Conexão fechada.");
                } catch (SQLException se) {
                    System.err.println("Erro ao fechar a conexão: " + se.getMessage());
                }
            }
        }
    }
}
