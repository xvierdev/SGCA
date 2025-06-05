package model;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private int idCurso;
    private String nome;
    private int cargaHoraria;
    private int limiteAlunos;
    private int ativo;
    List<Aluno> listaAluno = new ArrayList<>();
}
