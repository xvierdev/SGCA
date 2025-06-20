package dao;

import java.sql.SQLException;

import model.Curso;

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
        int idCurso = cursoDao.getIdCursoByName(nomeAntigo);
        boolean result = cursoDao.updateNomeCurso(idCurso, nomeNovo);
        if (result) {
            System.out.println(nomeAntigo + " alterado para " + nomeNovo);
        } else {
            System.out.println("Erro ao alterar nome.");
        }
    }

    public void trocarCargaHoraria(String nomeCurso, int cargaHorariaNova) throws SQLException {
        int idCurso = cursoDao.getIdCursoByName(nomeCurso);
        boolean result = cursoDao.updateCargaHorariaCurso(idCurso, cargaHorariaNova);
        if (result) {
            System.out.println("Carga horária alterada para " + cargaHorariaNova);
        } else {
            System.out.println("Erro ao mudar carga horária do curso " + nomeCurso);
        }
    }

    public void trocarLimiteAlunos(String nomeCurso, int novoLimite) throws SQLException {
        int idCurso = cursoDao.getIdCursoByName(nomeCurso);
        boolean result = cursoDao.updateLimiteAlunos(idCurso, novoLimite);
        if (result) {
            System.out.println("Limite de alunos alterado para " + novoLimite);
        } else {
            System.out.println("Erro ao mudar o limite de alunos do curso " + nomeCurso);
        }
    }

    public void RemoverCurso(String nomeCurso) throws SQLException {
        int idRemover = cursoDao.getIdCursoByName(nomeCurso);
        boolean result = cursoDao.removeCurso(idRemover);
        if (result) {
            System.out.println(nomeCurso + " removido com sucesso.");
        } else {
            System.out.println("Erro ao remover curso " + nomeCurso);
        }
    }

    public void desativarCurso(String nomeCurso) throws SQLException {
        int idCurso = cursoDao.getIdCursoByName(nomeCurso);
        boolean result = cursoDao.desativarCurso(idCurso);
        if (result) {
            System.out.println(nomeCurso + " desativado com sucesso.");
        }
    }

    public void ativarCurso(String nomeCurso) throws SQLException {
        int idCurso = cursoDao.getIdCursoByName(nomeCurso);
        boolean result = cursoDao.ativarCurso(idCurso);
        if (result) {
            System.out.println(nomeCurso + " ativado com sucesso.");
        }
    }

    public void cursoEstaAtivo(String nomeCurso) throws SQLException {
        int idCurso = cursoDao.getIdCursoByName(nomeCurso);
        boolean result = cursoDao.cursoEstaAtivo(idCurso);
        if (result) {
            System.out.println(nomeCurso + " está ativo.");
        } else {
            System.out.println(nomeCurso + " não está ativo.");
        }
    }

    public void obterCurso(String nomeCurso) throws SQLException {
        int idCurso = cursoDao.getIdCursoByName(nomeCurso);
        Curso curso = cursoDao.obterCurso(idCurso);
        System.out.println(curso);
    }
}
