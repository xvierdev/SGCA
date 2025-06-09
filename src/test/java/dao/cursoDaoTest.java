package dao;

import org.junit.jupiter.api.Test;

import model.Curso;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

public class cursoDaoTest {
    private cursoDao cd = new cursoDao();

    @Test
    void testarConsulta() throws SQLException {
        int idCurso = 1001;

        Curso expected = new Curso(1001, "curso falso", 33, 20, true);
        Curso result = cd.getCurso(idCurso);
        
        assertEquals(expected, result, "Erro na consulta.");
    }

    @Test
    void testarAdicionaCurso() throws SQLException{
        String nomeCurso = "Curso de Gambiarra";
        int cargaHoraria = 20;
        int limiteAlunos = 10;

        boolean result = cd.addCurso(nomeCurso, cargaHoraria, limiteAlunos);

        assertTrue(result);
    }

}
