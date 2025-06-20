package dao;

import java.sql.SQLException;

public class TesteCurso {
    // Adicionar curso
    // Atualizar curso
    // Remover curso

    CursoDao cursoDao = new CursoDao();

    public boolean AdicionarCurso() throws SQLException {
        boolean result = cursoDao.addCurso("Filosofia", 60, 20);
        return result;
    }

    public boolean EditarCurso() throws SQLException {
        int idUpdate = cursoDao.getIdCursoByName("Filosofia");
        return cursoDao.updateCurso(idUpdate, "Sociologia");
    }

    public boolean RemoverCurso() throws SQLException {
        int idRemover = cursoDao.getIdCursoByName("Filosofia");
        return cursoDao.removeCurso(idRemover);
    }

}
