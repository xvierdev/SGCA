package dao;

import factory.ConnectionFactory;
import model.Aluno;
import model.enums.Status;

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

public class AlunoDao implements AutoCloseable {

    private final Connection conn;

    public AlunoDao() throws SQLException {
        this.conn = ConnectionFactory.getConnection();
    }

    // --- Métodos CRUD (Create, Read, Update, Delete) ---

    /**
     * Adiciona um novo aluno ao banco de dados.
     * Esta camada é responsável apenas pela persistência. As validações de negócio
     * (ex: curso ativo/cheio) devem ser realizadas na camada de serviço
     * (AlunoService).
     * Retorna o objeto Aluno completo com o ID gerado pelo banco.
     * 
     * @param aluno O objeto Aluno a ser adicionado (idAluno pode ser 0).
     * @return O objeto Aluno com o ID gerado, ou null se a inserção falhar.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public Aluno adicionar(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO aluno (idCurso, nome, cpf, telefone, email, dataNascimento, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, aluno.getIdCurso());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getCpf());
            stmt.setString(4, aluno.getTelefone());
            stmt.setString(5, aluno.getEmail());
            stmt.setDate(6, Date.valueOf(aluno.getDataNascimento())); // Converte LocalDate para java.sql.Date
            stmt.setBoolean(7, aluno.isAtivo().isAtivo());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGerado = rs.getInt(1);
                        // Cria um novo objeto Aluno com o ID gerado para retornar
                        return new Aluno(idGerado, aluno.getIdCurso(), aluno.getNome(), aluno.getCpf(),
                                aluno.getTelefone(), aluno.getEmail(), aluno.getDataNascimento(), aluno.isAtivo());
                    }
                }
            }
        }
        return null; // Retorna null se a inserção falhar ou o ID não for gerado
    }

    /**
     * Busca um aluno pelo seu CPF.
     * 
     * @param cpf O CPF do aluno a ser buscado.
     * @return Um Optional contendo o objeto Aluno correspondente ao CPF, ou um
     *         Optional vazio se não encontrado.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public Optional<Aluno> buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT idAluno, idCurso, nome, cpf, telefone, email, dataNascimento, status FROM aluno WHERE cpf = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(criarAlunoDoResultSet(rs)); // Retorna Optional com o aluno
                }
            }
        }
        return Optional.empty(); // Retorna Optional vazio se não encontrar
    }

    /**
     * Busca um aluno pelo seu ID.
     * 
     * @param idAluno O ID do aluno a ser buscado.
     * @return Um Optional contendo o objeto Aluno correspondente ao ID, ou um
     *         Optional vazio se não encontrado.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public Optional<Aluno> buscarPorId(int idAluno) throws SQLException {
        String sql = "SELECT idAluno, idCurso, nome, cpf, telefone, email, dataNascimento, status FROM aluno WHERE idAluno = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(criarAlunoDoResultSet(rs)); // Retorna Optional com o aluno
                }
            }
        }
        return Optional.empty(); // Retorna Optional vazio se não encontrar
    }

    /**
     * Busca todos os alunos cadastrados no banco de dados.
     * 
     * @return Uma lista de objetos Aluno, vazia se não houver alunos.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public List<Aluno> buscarTodos() throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT idAluno, idCurso, nome, cpf, telefone, email, dataNascimento, status FROM aluno";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                alunos.add(criarAlunoDoResultSet(rs));
            }
        }
        return alunos;
    }

    public List<Aluno> buscarTodosFiltrado(Status filterStatus) throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT idAluno, idCurso, nome, cpf, telefone, email, dataNascimento, status FROM aluno WHERE status = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, filterStatus.isAtivo());
            ResultSet rs = stmt.executeQuery();
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
    /**
     * Atualiza as informações de um aluno existente no banco de dados.
     * 
     * @param aluno O objeto Aluno com as informações atualizadas.
     * @return true se a atualização foi bem-sucedida, false caso contrário.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public boolean atualizar(Aluno aluno) throws SQLException {
        String sql = "UPDATE aluno SET idCurso = ?, nome = ?, telefone = ?, email = ?, dataNascimento = ?, status = ? WHERE idAluno = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, aluno.getIdCurso());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getTelefone());
            stmt.setString(4, aluno.getEmail());
            stmt.setDate(5, Date.valueOf(aluno.getDataNascimento()));
            stmt.setBoolean(6, aluno.isAtivo().isAtivo());
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
    public boolean deletar(int id) throws SQLException {
        String sql = "DELETE FROM aluno WHERE idAluno = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Ativa o status de um aluno no banco de dados.
     * 
     * @param id O CPF do aluno a ser ativado.
     * @return true se o aluno foi ativado, false caso contrário.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public boolean ativar(int id) throws SQLException {
        String sql = "UPDATE aluno SET status = TRUE WHERE idAluno = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Desativa o status de um aluno no banco de dados.
     * 
     * @param idAluno
     * @return true se o aluno foi desativado, false caso contrário.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public boolean desativar(int idAluno) throws SQLException {
        String sql = "UPDATE aluno SET status = FALSE WHERE idAluno = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Verifica se um aluno está ativo.
     * 
     * @param id O CPF do aluno.
     * @return true se o aluno está ativo, false caso contrário (incluindo se não
     *         existir).
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public boolean estaAtivo(int id) throws SQLException {
        String sql = "SELECT status FROM aluno WHERE idAluno = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getBoolean("status");
            }
        }
    }

    /**
     * Verifica se um aluno com o CPF fornecido existe no banco de dados.
     * 
     * @param id O CPF do aluno a ser verificado.
     * @return true se o aluno existe, false caso contrário.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public boolean existeAluno(int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM aluno WHERE idAluno = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
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
        String sql = "SELECT idAluno, idCurso, nome, cpf, telefone, email, dataNascimento, status FROM aluno WHERE idCurso = ?";
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

    public List<Aluno> buscarAlunosPorCurso(int idCurso, Status filterStatus) throws SQLException {
        List<Aluno> alunosFiltradosDoCurso = new ArrayList<>();
        String sql = "SELECT idAluno, idCurso, nome, cpf, telefone, email, dataNascimento, status FROM aluno WHERE idCurso = ? AND status = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            stmt.setBoolean(2, filterStatus.isAtivo());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    alunosFiltradosDoCurso.add(criarAlunoDoResultSet(rs));
                }
            }
        }
        return alunosFiltradosDoCurso;
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
        boolean status = rs.getBoolean("status");
        return new Aluno(idAluno, idCurso, nome, cpf, telefone, email, dataNascimento, Status.fromBoolean(status));
    }

    /**
     * Implementação do método close() da interface AutoCloseable.
     * Garante que a conexão com o banco de dados seja fechada.
     * 
     * @throws SQLException Se ocorrer um erro ao fechar a conexão.
     */
    @Override
    public void close() throws SQLException {
        if (this.conn != null && !this.conn.isClosed()) {
            this.conn.close();
            System.out.println("Conexão do AlunoDao fechada.");
        }
    }
}
