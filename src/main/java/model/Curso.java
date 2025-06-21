package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Curso {

    // --- Atributos ---
    private int idCurso;
    private String nome;
    private int cargaHoraria;
    private int limiteAlunos;
    private boolean ativo;
    private List<Aluno> listaAluno; // Inicializa no construtor ou aqui, dependendo do uso

    // --- Construtores ---
    public Curso(String nome, int cargaHoraria, int limiteAlunos) throws IllegalArgumentException {
        // Validações chamadas antes de atribuir para garantir que o objeto seja válido desde o início.
        validarNome(nome);
        validarCargaHoraria(cargaHoraria);
        validarLimiteAlunos(limiteAlunos);

        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.limiteAlunos = limiteAlunos;
        this.ativo = true; // Novo curso geralmente é ativo por padrão
        this.listaAluno = new ArrayList<>(); // Inicializa a lista de alunos
    }

    public Curso(int idCurso, String nome, int cargaHoraria, int limiteAlunos, boolean ativo)
            throws IllegalArgumentException {
        // Reutiliza as validações para garantir consistência
        validarNome(nome);
        validarCargaHoraria(cargaHoraria);
        validarLimiteAlunos(limiteAlunos);

        this.idCurso = idCurso;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.limiteAlunos = limiteAlunos;
        this.ativo = ativo;
        this.listaAluno = new ArrayList<>(); // Inicializa a lista de alunos
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
        // Adiciona validação também no setter para garantir integridade ao mudar o nome
        validarNome(nome);
        this.nome = nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        // Adiciona validação também no setter
        validarCargaHoraria(cargaHoraria);
        this.cargaHoraria = cargaHoraria;
    }

    public int getLimiteAlunos() {
        return limiteAlunos;
    }

    public void setLimiteAlunos(int limiteAlunos) {
        // Adiciona validação também no setter
        validarLimiteAlunos(limiteAlunos);
        this.limiteAlunos = limiteAlunos;
    }

    public boolean isAtivo() { // Convenção Java para getters de booleanos é 'is'
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    // --- Métodos de Negócio/Relacionamento (para lista de alunos) ---
    public void adicionarAluno(Aluno aluno) {
        if (this.listaAluno.size() < this.limiteAlunos) {
            this.listaAluno.add(aluno);
        } else {
            throw new IllegalStateException("O curso atingiu o limite máximo de alunos.");
        }
    }
    
    public void removerAluno(Aluno aluno) {
        this.listaAluno.remove(aluno);
    }

    public List<Aluno> getListaAlunos() {
        // Retorna uma cópia defensiva para evitar modificações externas diretas na lista interna
        return new ArrayList<>(this.listaAluno); 
    }

    public int getNumeroAtualAlunos() {
        return this.listaAluno.size();
    }

    // --- Métodos de Validação (Privados) ---
    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty() || nome.trim().length() < 3) {
            throw new IllegalArgumentException("O nome do curso deve ter no mínimo 3 caracteres e não pode ser vazio.");
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

    // --- Métodos Padrão de Objeto (Overrides) ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Curso curso = (Curso) o;
        // Para equals, geralmente comparamos campos que definem a unicidade do objeto
        // Id é um bom candidato se você o usar como identificador único
        return idCurso == curso.idCurso &&
               nome.equals(curso.nome); // nome também pode ser único, mas idCurso é mais forte
    }

    @Override
    public int hashCode() {
        // Hashing baseado nos mesmos campos usados no equals
        return Objects.hash(idCurso, nome); 
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