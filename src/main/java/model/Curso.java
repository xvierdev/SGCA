package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Curso {

    private int idCurso;
    private String nome;
    private int cargaHoraria;
    private int limiteAlunos;
    private boolean ativo;
    private List<Aluno> listaAluno;

    // --- Construtores ---
    public Curso(String nome, int cargaHoraria, int limiteAlunos) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.limiteAlunos = limiteAlunos;
        this.ativo = true;
    }

    public Curso(int idCurso, String nome, int cargaHoraria, int limiteAlunos, boolean ativo) {
        this.idCurso = idCurso;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.limiteAlunos = limiteAlunos;
        this.ativo = ativo;
    }

    // --- Getters e Setters ---
    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public int getLimiteAlunos() {
        return limiteAlunos;
    }

    public void setLimiteAlunos(int limiteAlunos) {
        this.limiteAlunos = limiteAlunos;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Curso curso = (Curso) o;
        return idCurso == curso.idCurso;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCurso);
    }

    @Override
    public String toString() {
        return "Curso{" +
                "idCurso=" + idCurso +
                ", nome='" + nome + '\'' +
                ", cargaHoraria=" + cargaHoraria +
                ", limiteAlunos=" + limiteAlunos +
                ", ativo=" + ativo +
                ", numeroAlunos=" + listaAluno.size() + // Adiciona o número de alunos
                '}';
    }
}