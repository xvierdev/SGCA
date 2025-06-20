package dao;

import java.sql.SQLException;

public class Testador {

    public static void main(String[] args) {
        try {
            TesteCurso ts = new TesteCurso();
//            ts.AdicionarCurso("Filosofia", 60, 33); // Adicionado e testado com sucesso.
//            ts.EditarCurso("Filosofia", "Direito"); // Alterar nome do curso com sucesso.
//            ts.EditarCurso("Sexologia", "Direito"); // Alterar nome do curso com sucesso.
//            ts.trocarCargaHoraria("Direito", 90); // Alteração de carga horária com sucesso
            ts.RemoverCurso();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }
}
