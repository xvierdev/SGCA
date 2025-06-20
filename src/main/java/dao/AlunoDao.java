package dao;

import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import model.Aluno;

public class AlunoDao {

    private final Connection conn;

    public AlunoDao() {
        this.conn = ConnectionFactory.getConnection();
    }

    public Aluno getAlunoByCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM aluno WHERE cpf = (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idAluno = rs.getInt("idAluno");
                    int idCurso = rs.getInt("idCurso");
                    String nome = rs.getString("nome");
                    String telefone = rs.getString("telefone");
                    String email = rs.getString("email");
                    LocalDate dataNascimento = rs.getDate("dataNascimento").toLocalDate();
                    return new Aluno(idAluno, idCurso, nome, cpf, telefone, email, dataNascimento);
                } else {
                    return null;
                }
            }
        }
    }

    public boolean addAluno(int idCurso, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) throws SQLException {
        String sql = "INSERT INTO aluno (idCurso, nome, cpf, telefone, email, dataNascimento) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            stmt.setString(2, nome);
            stmt.setString(3, cpf);
            stmt.setString(4, telefone);
            stmt.setString(5, email);
            stmt.setDate(6, Date.valueOf(dataNascimento));
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeAluno(String cpf) throws SQLException {
        String sql = "DELETE FROM aluno WHERE cpf = (?)";
        try (PreparedStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, cpf);
            return stmt.executeUpdate() > 0;
        }
    }
}
