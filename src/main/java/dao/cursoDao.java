package dao;

import factory.ConnectionFactory;
import model.Curso;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class CursoDao {

    private final Connection conn;

    public CursoDao() {
        this.conn = ConnectionFactory.getConnection();
    }

    public Curso obterCurso(int id) throws SQLException {
        String sql = "SELECT * FROM curso WHERE idCurso = (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idCurso = rs.getInt("idCurso");
                    int cargaHoraria = rs.getInt("cargaHoraria");
                    int limiteAlunos = rs.getInt("limiteAlunos");
                    String nomeCurso = rs.getString("nome");
                    boolean ativo = rs.getBoolean("ativo");
                    Curso curso = new Curso(idCurso, nomeCurso, cargaHoraria, limiteAlunos, ativo);
                    return curso;
                } else {
                    return null;
                }
            }
        }
    }

    public boolean desativarCurso(int idCurso) throws SQLException {
        String sql = "UPDATE curso SET ativo = 0 WHERE idCurso = (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean ativarCurso(int idCurso) throws SQLException {
        String sql = "UPDATE curso SET ativo = 1 WHERE idCurso = (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean cursoEstaAtivo(int idCurso) throws SQLException {
        String sql = "SELECT ativo FROM curso WHERE idCurso = (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            ResultSet st = stmt.executeQuery();
            if (st.next()) {
                return st.getBoolean("ativo");
            }
            return false;
        }
    }

    public boolean updateCargaHorariaCurso(int id, int novaCargaHoraria) throws SQLException {
        String sql = "UPDATE curso SET cargaHoraria = (?) WHERE idCurso = (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, novaCargaHoraria);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateLimiteAlunos(int id, int novoLimiteAlunos) throws SQLException {
        String sql = "UPDATE curso SET limiteAlunos = (?) WHERE idCurso = (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, novoLimiteAlunos);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateNomeCurso(int id, String nome) throws SQLException {
        String sql = "UPDATE curso set nome = (?) WHERE idCurso = (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // public boolean updateCurso(int id, int cargaHoraria, int limiteAlunos, String
    // nomeCurso) throws SQLException {
    // String sql = "UPDATE curso SET cargaHoraria = (?), limiteAlunos = (?),
    // nomeCurso = ()";
    // return false;
    // }
    // Funcional
    public boolean addCurso(String nomeCurso, int cargaHoraria, int limiteAlunos) throws SQLException {
        String sql = "INSERT INTO curso (nome, cargaHoraria, limiteAlunos) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeCurso);
            stmt.setInt(2, cargaHoraria);
            stmt.setInt(3, limiteAlunos);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeCurso(int idCurso) throws SQLException {
        String sql = "DELETE FROM curso WHERE idCurso = (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            return stmt.executeUpdate() > 0;
        }
    }

    public int getIdCursoByName(String nomeCurso) throws SQLException {
        String sql = "SELECT idCurso FROM curso WHERE nome = (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeCurso);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idCurso");
                } else {
                    return -1;
                }
            }
        }
    }
}
