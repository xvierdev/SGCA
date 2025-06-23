package model;

import java.util.List;
import java.util.Objects;

import model.enums.Status;

public class Curso {

    private int idCurso;
    private String nome;
    private int cargaHoraria;
    private int limiteAlunos;
    private Status status;
    private List<Aluno> listAlunos;

    // --- Construtores ---
    public Curso(String nome, int cargaHoraria, int limiteAlunos) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.limiteAlunos = limiteAlunos;
        this.status = Status.ATIVO;
    }

    public Curso(int idCurso, String nome, int cargaHoraria, int limiteAlunos, Status status) {
        this.idCurso = idCurso;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.limiteAlunos = limiteAlunos;
        this.status = status;
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

    public Status isAtivo() {
        return status;
    }

    public void setAtivo(Status status) {
        this.status = status;
    }

    public void setAlunos(List<Aluno> listAlunos) {
        this.listAlunos = listAlunos;
    }

    public List<Aluno> getAlunos() {
        return this.listAlunos;
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
                ", status=" + status + '}';
    }
}