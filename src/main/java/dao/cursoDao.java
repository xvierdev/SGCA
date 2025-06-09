package dao;

import factory.ConnectionFactory;
import model.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class cursoDao {
    private Connection conn;

    public cursoDao() {
        new ConnectionFactory();
        this.conn = ConnectionFactory.getConnection();
    }

    public Curso getCurso(int id) throws SQLException {
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

    public boolean addCurso(String nomeCurso, int cargaHoraria, int limiteAlunos) throws SQLException {
        String sql = "INSERT INTO curso (nome, cargaHoraria, limiteAlunos) VALUES (?, ?, ?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, nomeCurso);
            stmt.setInt(2, cargaHoraria);
            stmt.setInt(3, limiteAlunos);
            return stmt.executeUpdate() > 0;
        }
    }
}
