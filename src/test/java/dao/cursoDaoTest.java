package dao;

import org.junit.jupiter.api.Test;

import model.Curso;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

public class CursoDaoTest {

    private final CursoDao cd = new CursoDao();

    @Test
    void testarAdicionaCurso() throws SQLException {
        String nomeCurso = "Curso de Gambiarra";
        int cargaHoraria = 20;
        int limiteAlunos = 10;

        boolean result = cd.addCurso(nomeCurso, cargaHoraria, limiteAlunos);

        assertTrue(result);
    }

    @Test
    void testarConsulta() throws SQLException {
        int idCurso = 1;

        Curso expected = new Curso(1, "Curso de Gambiarra", 20, 10, true);
        Curso result = cd.getCurso(idCurso);

        assertEquals(expected, result, "Erro na consulta.");

    }

}
