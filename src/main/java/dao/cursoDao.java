package dao;

import factory.ConnectionFactory;
import model.Curso;
import model.enums.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class CursoDao implements AutoCloseable {

    final Connection conn;

    public CursoDao() throws SQLException {
        this.conn = ConnectionFactory.getConnection();
    }

    // --- Métodos CRUD (Create, Read, Update, Delete) ---
    /**
     * Adiciona um novo curso ao banco de dados. Retorna o objeto Curso completo
     * com o ID gerado pelo banco.
     *
     * @param curso O objeto Curso a ser adicionado (ID não preenchido).
     * @return O objeto Curso com o ID gerado, ou null se a inserção falhar.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public Curso adicionar(Curso curso) throws SQLException {
        String sql = "INSERT INTO curso (nome, cargaHoraria, limiteAlunos, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, curso.getNome());
            stmt.setInt(2, curso.getCargaHoraria());
            stmt.setInt(3, curso.getLimiteAlunos());
            stmt.setBoolean(4, curso.isAtivo().isAtivo());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGerado = rs.getInt(1);
                        return new Curso(idGerado, curso.getNome(), curso.getCargaHoraria(), curso.getLimiteAlunos(),
                                curso.isAtivo());
                    }
                }
            }
            return null;
        }
    }

    /**
     * Obtém um curso pelo seu ID.
     *
     * @param id O ID do curso.
     * @return Um Optional contendo o objeto Curso correspondente ao ID, ou um
     *         Optional vazio se não for encontrado.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public Optional<Curso> buscarPorId(int id) throws SQLException {
        String sql = "SELECT idCurso, nome, cargaHoraria, limiteAlunos, status FROM curso WHERE idCurso = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(criarCursoDoResultSet(rs));
                }
                return Optional.empty(); // Retorna Optional vazio se não encontrar
            }
        }
    }

    /**
     * Retorna uma lista de todos os cursos no banco de dados.
     *
     * @return Uma lista de objetos Curso.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public List<Curso> buscarTodos() throws SQLException {
        String sql = "SELECT idCurso, nome, cargaHoraria, limiteAlunos, status FROM curso";
        List<Curso> listaCursos = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                listaCursos.add(criarCursoDoResultSet(rs));
            }
        }
        return listaCursos;
    }

    public List<Curso> buscarTodosFiltrado(Status filterStatus) throws SQLException {
        String sql = "SELECT idCurso, nome, cargaHoraria, limiteAlunos, status FROM curso WHERE status = ?";
        List<Curso> listaCursos = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, filterStatus.isAtivo());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    listaCursos.add(criarCursoDoResultSet(rs));
                }
            }
        }
        return listaCursos;
    }

    /**
     * Atualiza as informações de um curso existente.
     *
     * @param curso O objeto Curso com as informações atualizadas (ID deve estar
     *              preenchido).
     * @return true se o curso foi atualizado com sucesso, false caso contrário.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public boolean atualizar(Curso curso) throws SQLException {
        String sql = "UPDATE curso SET nome = ?, cargaHoraria = ?, limiteAlunos = ?, status = ? WHERE idCurso = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, curso.getNome());
            stmt.setInt(2, curso.getCargaHoraria());
            stmt.setInt(3, curso.getLimiteAlunos());
            stmt.setBoolean(4, curso.isAtivo().isAtivo());
            stmt.setInt(5, curso.getIdCurso());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Remove um curso do banco de dados pelo seu ID.
     *
     * @param idCurso O ID do curso a ser removido.
     * @return true se o curso foi removido com sucesso, false caso contrário.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public boolean remover(int idCurso) throws SQLException {
        String sql = "DELETE FROM curso WHERE idCurso = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            return stmt.executeUpdate() > 0;
        }
    }

    // --- Métodos de Status (Ativar/Desativar) ---
    /**
     * Desativa um curso pelo seu ID.
     *
     * @param idCurso O ID do curso a ser desativado.
     * @return true se o curso foi desativado, false caso contrário.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public boolean desativar(int idCurso) throws SQLException {
        String sql = "UPDATE curso SET status = FALSE WHERE idCurso = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Ativa um curso pelo seu ID.
     *
     * @param idCurso O ID do curso a ser ativado.
     * @return true se o curso foi ativado, false caso contrário.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public boolean ativar(int idCurso) throws SQLException {
        String sql = "UPDATE curso SET status = TRUE WHERE idCurso = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Verifica se um curso está status.
     *
     * @param idCurso O ID do curso.
     * @return true se o curso estiver status, false caso contrário ou se não
     *         for encontrado.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public boolean estaAtivo(int idCurso) throws SQLException {
        String sql = "SELECT status FROM curso WHERE idCurso = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getBoolean("status");
            }
        }
    }

    // --- Métodos de Consulta Específicos/Utilitários ---
    /**
     * Obtém o ID de um curso pelo seu nome.
     *
     * @param nomeCurso O nome do curso.
     * @return Um OptionalInt contendo o ID do curso, ou um OptionalInt vazio se
     *         não for encontrado.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public OptionalInt buscarIdPorNome(String nomeCurso) throws SQLException {
        String sql = "SELECT idCurso FROM curso WHERE nome = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeCurso);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return OptionalInt.of(rs.getInt("idCurso"));
                }
                return OptionalInt.empty(); // Retorna OptionalInt vazio se não encontrar
            }
        }
    }

    /**
     * Obtém o limite de alunos para um curso específico.
     *
     * @param idCurso O ID do curso.
     * @return Um OptionalInt contendo o limite de alunos do curso, ou um
     *         OptionalInt vazio se o curso não for encontrado.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public OptionalInt getLimiteAlunos(int idCurso) throws SQLException {
        String sql = "SELECT limiteAlunos FROM curso WHERE idCurso = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return OptionalInt.of(rs.getInt("limiteAlunos"));
                }
                return OptionalInt.empty();
            }
        }
    }

    /**
     * Obtém o número total de alunos matriculados em um curso específico.
     *
     * @param idCurso O ID do curso.
     * @return O número de alunos, ou 0 se não houver alunos ou o curso não
     *         existir.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public int getTotalAlunosNoCurso(int idCurso) throws SQLException {
        String sql = "SELECT COUNT(idAluno) AS total_alunos FROM aluno WHERE idCurso = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total_alunos");
                }
                return 0; // Retorna 0 se não houver alunos ou o curso não existir (assumindo que COUNT
                          // retorna 0 se não houver linhas)
            }
        }
    }

    /**
     * Verifica se um curso atingiu seu limite máximo de alunos.
     *
     * @param idCurso O ID do curso.
     * @return true se o curso estiver cheio, false caso contrário.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public boolean cursoCheio(int idCurso) throws SQLException {
        int totalAlunos = getTotalAlunosNoCurso(idCurso);
        OptionalInt limiteAlunosOpt = getLimiteAlunos(idCurso);
        int limiteAlunos = limiteAlunosOpt.getAsInt();
        return totalAlunos >= limiteAlunos;
    }

    // --- Método Auxiliar Privado ---
    /**
     * Cria um objeto Curso a partir de um ResultSet. Centraliza a lógica de
     * mapeamento de ResultSet para objeto Curso.
     *
     * @param rs O ResultSet contendo os dados do curso.
     * @return Um objeto Curso preenchido.
     * @throws SQLException Se ocorrer um erro ao ler os dados do ResultSet.
     */
    private Curso criarCursoDoResultSet(ResultSet rs) throws SQLException {
        int idCurso = rs.getInt("idCurso");
        String nome = rs.getString("nome");
        int cargaHoraria = rs.getInt("cargaHoraria");
        int limiteAlunos = rs.getInt("limiteAlunos");
        boolean status = rs.getBoolean("status");
        return new Curso(idCurso, nome, cargaHoraria, limiteAlunos, Status.fromBoolean(status));
    }

    /**
     * Implementação do método close() da interface AutoCloseable. Garante que a
     * conexão com o banco de dados seja fechada.
     *
     * @throws SQLException Se ocorrer um erro ao fechar a conexão.
     */
    @Override
    public void close() throws SQLException {
        if (this.conn != null && !this.conn.isClosed()) {
            this.conn.close();
            System.out.println("Conexão do CursoDao fechada.");
        }
    }
}
