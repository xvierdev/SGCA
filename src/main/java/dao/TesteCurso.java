package dao;

import java.sql.SQLException;

public class TesteCurso {
    // Adicionar curso
    // Atualizar curso
    // Remover curso

    CursoDao cursoDao = new CursoDao();

    public void AdicionarCurso(String nome, int cargaHoraria, int limiteAlunos) throws SQLException {
        boolean result = cursoDao.addCurso(nome, cargaHoraria, limiteAlunos);
        if (result) {
            System.out.println(nome + " adicionado com sucesso!");
        } else {
            System.out.println(nome + " não foi adicionado por algum motivo.");
        }
    }

    public void EditarCurso(String nomeAntigo, String nomeNovo) throws SQLException {
        int idUpdate = cursoDao.getIdCursoByName(nomeAntigo);
        boolean result = cursoDao.updateNomeCurso(idUpdate, nomeNovo);
        if (result) {
            System.out.println(nomeAntigo + " alterado para " + nomeNovo);
        } else {
            System.out.println("Erro ao alterar nome.");
        }
    }

    public void trocarCargaHoraria(String nomeCurso, int cargaHorariaNova) throws SQLException {
        int idUpdate = cursoDao.getIdCursoByName(nomeCurso);
        boolean result = cursoDao.updateCargaHorariaCurso(idUpdate, cargaHorariaNova);
        if (result) {
            System.out.println("Carga horária alterada para " + cargaHorariaNova);
        } else {
            System.out.println("Erro ao mudar carga horária do curso " + nomeCurso);
        }
    }

    public void trocarLimiteAlunos(String nomeCurso, int novoLimite) throws SQLException {
        int idUpdate = cursoDao.getIdCursoByName(nomeCurso);
        boolean result = cursoDao.updateLimiteAlunos(idUpdate, novoLimite);
        if (result) {
            System.out.println("Limite de alunos alterado para " + novoLimite);
        } else {
            System.out.println("Erro ao mudar o limite de alunos do curso " + nomeCurso);
        }
    }

    public boolean RemoverCurso() throws SQLException {
        int idRemover = cursoDao.getIdCursoByName("Filosofia");
        return cursoDao.removeCurso(idRemover);
    }

}
