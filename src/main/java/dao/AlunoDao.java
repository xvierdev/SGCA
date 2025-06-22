package dao;

import factory.ConnectionFactory;
import model.Aluno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlunoDao {

    private final Connection conn;

    public AlunoDao() throws SQLException {
        this.conn = ConnectionFactory.getConnection();
    }

    // --- Métodos CRUD (Create, Read, Update, Delete) ---

    /**
     * Adiciona um novo aluno ao banco de dados.
     * Retorna o objeto Aluno completo com o ID gerado pelo banco.
     * 
     * @param aluno O objeto Aluno a ser adicionado (idAluno pode ser 0).
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public void adicionar(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO aluno (idCurso, nome, cpf, telefone, email, dataNascimento, ativo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, aluno.getIdCurso());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getCpf());
            stmt.setString(4, aluno.getTelefone());
            stmt.setString(5, aluno.getEmail());
            stmt.setDate(6, Date.valueOf(aluno.getDataNascimento())); // Converte LocalDate para java.sql.Date
            stmt.setBoolean(7, aluno.isAtivo());
            stmt.executeUpdate();
        }
    }

    /**
     * Busca um aluno pelo seu CPF.
     * 
     * @param cpf O CPF do aluno a ser buscado.
     * @return O objeto Aluno correspondente ao CPF, ou null se não encontrado.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public Aluno buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT idAluno, idCurso, nome, cpf, telefone, email, dataNascimento, ativo FROM aluno WHERE cpf = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarAlunoDoResultSet(rs);
                }
            }
        }
        return null;
    }

    /**
     * Busca um aluno pelo seu ID.
     * 
     * @param idAluno O ID do aluno a ser buscado.
     * @return O objeto Aluno correspondente ao ID, ou null se não encontrado.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public Optional<Aluno> buscarPorId(int idAluno) throws SQLException {
        String sql = "SELECT idAluno, idCurso, nome, cpf, telefone, email, dataNascimento, ativo FROM aluno WHERE idAluno = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(criarAlunoDoResultSet(rs));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Busca todos os alunos cadastrados no banco de dados.
     * 
     * @return Uma lista de objetos Aluno, vazia se não houver alunos.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public List<Aluno> buscarTodos() throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT idAluno, idCurso, nome, cpf, telefone, email, dataNascimento, ativo FROM aluno";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                alunos.add(criarAlunoDoResultSet(rs));
            }
        }
        return alunos;
    }

    /**
     * Atualiza as informações de um aluno existente no banco de dados.
     * 
     * @param aluno O objeto Aluno com as informações atualizadas.
     * @return true se a atualização foi bem-sucedida, false caso contrário.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public boolean atualizar(Aluno aluno) throws SQLException {
        String sql = "UPDATE aluno SET idCurso = ?, nome = ?, telefone = ?, email = ?, dataNascimento = ?, ativo = ? WHERE idAluno = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, aluno.getIdCurso());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getTelefone());
            stmt.setString(4, aluno.getEmail());
            stmt.setDate(5, Date.valueOf(aluno.getDataNascimento()));
            stmt.setBoolean(6, aluno.isAtivo());
            stmt.setInt(7, aluno.getIdAluno());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Remove um aluno do banco de dados pelo seu CPF.
     * 
     * @param id O CPF do aluno a ser removido.
     * @return true se a remoção foi bem-sucedida, false caso contrário.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM aluno WHERE cpf = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Ativa o status de um aluno no banco de dados.
     * 
     * @param cpf O CPF do aluno a ser ativado.
     * @return true se o aluno foi ativado, false caso contrário.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public boolean ativar(String cpf) throws SQLException {
        String sql = "UPDATE aluno SET ativo = TRUE WHERE cpf = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Desativa o status de um aluno no banco de dados.
     * 
     * @param cpf O CPF do aluno a ser desativado.
     * @return true se o aluno foi desativado, false caso contrário.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public boolean desativar(String cpf) throws SQLException {
        String sql = "UPDATE aluno SET ativo = FALSE WHERE cpf = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Verifica se um aluno está ativo.
     * 
     * @param cpf O CPF do aluno.
     * @return true se o aluno está ativo, false caso contrário (incluindo se não
     *         existir).
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public boolean estaAtivo(String cpf) throws SQLException {
        String sql = "SELECT ativo FROM aluno WHERE cpf = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getBoolean("ativo");
            }
        }
    }

    /**
     * Verifica se um aluno com o CPF fornecido existe no banco de dados.
     * 
     * @param cpf O CPF do aluno a ser verificado.
     * @return true se o aluno existe, false caso contrário.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public boolean existeAluno(String cpf) throws SQLException {
        String sql = "SELECT COUNT(*) FROM aluno WHERE cpf = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0; // Verifica se há pelo menos uma linha e a contagem é maior que 0
            }
        }
    }

    /**
     * Busca todos os alunos matriculados em um curso específico.
     * 
     * @param idCurso O ID do curso para listar os alunos.
     * @return Uma lista de objetos Aluno, vazia se não houver alunos nesse curso.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public List<Aluno> buscarAlunosPorCurso(int idCurso) throws SQLException {
        List<Aluno> alunosDoCurso = new ArrayList<>();
        String sql = "SELECT idAluno, idCurso, nome, cpf, telefone, email, dataNascimento, ativo FROM aluno WHERE idCurso = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    alunosDoCurso.add(criarAlunoDoResultSet(rs));
                }
            }
        }
        return alunosDoCurso;
    }

    // --- Método Auxiliar Privado ---
    /**
     * Cria um objeto Aluno a partir de um ResultSet.
     * Centraliza a lógica de mapeamento de ResultSet para objeto Aluno.
     * 
     * @param rs O ResultSet contendo os dados do aluno.
     * @return Um objeto Aluno preenchido.
     * @throws SQLException Se ocorrer um erro ao ler os dados do ResultSet.
     */
    private Aluno criarAlunoDoResultSet(ResultSet rs) throws SQLException {
        int idAluno = rs.getInt("idAluno");
        int idCurso = rs.getInt("idCurso");
        String nome = rs.getString("nome");
        String cpf = rs.getString("cpf");
        String telefone = rs.getString("telefone");
        String email = rs.getString("email");
        LocalDate dataNascimento = rs.getDate("dataNascimento").toLocalDate();
        boolean ativo = rs.getBoolean("ativo");
        return new Aluno(idAluno, idCurso, nome, cpf, telefone, email, dataNascimento, ativo);
    }
}
