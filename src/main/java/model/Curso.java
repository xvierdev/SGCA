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
    List<Aluno> listaAluno = new ArrayList<>();

    public Curso(String nome, int cargaHoraria, int limiteAlunos) throws IllegalArgumentException {
        validarNome(nome);
        validarCargaHoraria(cargaHoraria);
        validarLimiteAlunos(limiteAlunos);
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.limiteAlunos = limiteAlunos;
    }

    public Curso(int idCurso, String nome, int cargaHoraria, int limiteAlunos, boolean ativo)
            throws IllegalArgumentException {
        this.idCurso = idCurso;
        validarNome(nome);
        validarCargaHoraria(cargaHoraria);
        validarLimiteAlunos(limiteAlunos);
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.limiteAlunos = limiteAlunos;
        this.ativo = ativo;
    }

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

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    private void validarNome(String nome) {
        if (nome == null || nome.length() < 3) {
            throw new IllegalArgumentException("O nome do curso deve ter no mínimo 3 caracteres.");
        }
    }

    private void validarCargaHoraria(int cargaHoraria) {
        if (cargaHoraria < 20) {
            throw new IllegalArgumentException("A carga horária mínima é de 20 Horas.");
        }
    }

    private void validarLimiteAlunos(int limiteAlunos) {
        if (limiteAlunos < 1) {
            throw new IllegalArgumentException("O limite mínimo de alunos é 1.");
        }
    }

    public void adicionarAluno(Aluno aluno) {
        listaAluno.add(aluno);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Curso curso = (Curso) o;
        return idCurso == curso.idCurso &&
                cargaHoraria == curso.cargaHoraria &&
                limiteAlunos == curso.limiteAlunos &&
                ativo == curso.ativo &&
                Objects.equals(nome, curso.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCurso, nome, cargaHoraria, limiteAlunos, ativo);
    }

    @Override
    public String toString() {
        return "Curso{" +
                "id=" + idCurso +
                ", nome='" + nome + '\'' +
                ", cargaHoraria=" + cargaHoraria +
                ", limiteAlunos=" + limiteAlunos +
                ", ativo=" + ativo +
                '}';
    }

}
