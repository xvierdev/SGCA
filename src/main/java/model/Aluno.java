package model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects; // Importe Objects para equals e hashCode

public class Aluno {

    // --- Atributos ---
    private final int idAluno; // Geralmente definido no banco, mas mantido final se não mudar após a criação
    private int idCurso;
    private String nome;
    private final String cpf; // CPF é um identificador, geralmente imutável
    private String telefone;
    private String email;
    private LocalDate dataNascimento; // Removi 'final' para permitir setters, se necessário, ou mantenha se for imutável
    private boolean ativo;

    // --- Construtores ---
    // Construtor completo para quando o aluno já tem um ID (vindo do banco, por exemplo)
    public Aluno(int idAluno, int idCurso, String nome, String cpf, String telefone, String email, LocalDate dataNascimento, boolean ativo) {
        // Validações devem vir primeiro para garantir que o objeto seja válido antes de atribuir
        validarNome(nome);
        validarIdade(dataNascimento);
        validarCpf(cpf);
        validarEmail(email); // Valida o email também aqui

        this.idAluno = idAluno;
        this.idCurso = idCurso;
        this.nome = nome;
        this.cpf = cpf; // CPF é final, atribuído uma vez
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento; // Data de nascimento é final, atribuída uma vez
        this.ativo = ativo;
    }

    // Construtor para criar um novo aluno (ID pode ser gerado no banco)
    // idAluno e ativo podem ser omitidos se forem gerados/definidos automaticamente
    public Aluno(int idCurso, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) {
        // Validações
        validarNome(nome);
        validarIdade(dataNascimento);
        validarCpf(cpf);
        validarEmail(email);

        // Atribuições
        this.idAluno = 0; // Ou algum valor padrão, ou remova se for gerado exclusivamente pelo DB
        this.idCurso = idCurso;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.ativo = true; // Novo aluno geralmente começa ativo
    }


    // --- Getters e Setters ---
    public int getIdAluno() {
        return this.idAluno;
    }

    // Não há setter para idAluno se ele é final e é definido apenas no construtor.
    // Se o idAluno for definido pelo banco após a persistência, remova o `final` e adicione um setter.

    public int getIdCurso() {
        return this.idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        validarNome(nome); // Validação no setter
        this.nome = nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    // Não há setter para cpf se ele é final

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        // Você pode adicionar uma validação de telefone aqui se tiver um padrão
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validarEmail(email); // Validação no setter
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        validarIdade(dataNascimento); // Validação no setter
        this.dataNascimento = dataNascimento;
    }

    // Não há setter para dataNascimento se ele é final.
    // Se quiser permitir a mudança da data de nascimento (raro), remova 'final' e adicione um setter com validação.

    public boolean isAtivo() { // Convenção Java para getters de booleanos é 'is'
        return this.ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    // --- Métodos de Negócio ---
    public int getIdade() {
        return Period.between(this.dataNascimento, LocalDate.now()).getYears();
    }


    // --- Métodos de Validação (Privados) ---
    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty() || nome.trim().length() < 3) {
            throw new IllegalArgumentException("O nome do aluno deve ter no mínimo 3 caracteres e não pode ser vazio.");
        }
    }

    private void validarIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            throw new IllegalArgumentException("Data de nascimento não pode ser nula.");
        }
        Period p = Period.between(dataNascimento, LocalDate.now());
        if (p.getYears() < 16) {
            throw new IllegalArgumentException("A idade mínima do aluno é de 16 anos.");
        }
    }

    private void validarCpf(String cpf) throws IllegalArgumentException {
        if (cpf == null) {
            throw new IllegalArgumentException("CPF não pode ser nulo.");
        }
        String cleanCpf = cpf.trim().replace("-", "").replace(".", "");

        if (!cleanCpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido: Deve conter 11 dígitos numéricos.");
        }

        if (cleanCpf.matches("(\\d)\\1{10}")) {
            throw new IllegalArgumentException("CPF inválido: Dígitos repetidos.");
        }

        int[] digitos = new int[11];
        for (int i = 0; i < 11; i++) {
            digitos[i] = Character.getNumericValue(cleanCpf.charAt(i));
        }

        // Validação do primeiro dígito verificador
        int sum = 0;
        int pos = 10;
        for (int i = 0; i < 9; i++) {
            sum += digitos[i] * pos;
            pos--;
        }
        int resto = sum % 11;
        int expectedDigito1 = (resto < 2) ? 0 : (11 - resto);

        if (digitos[9] != expectedDigito1) {
            throw new IllegalArgumentException("CPF inválido.");
        }

        // Validação do segundo dígito verificador
        sum = 0;
        pos = 11;
        for (int i = 0; i < 10; i++) {
            sum += digitos[i] * pos;
            pos--;
        }
        resto = sum % 11;
        int expectedDigito2 = (resto < 2) ? 0 : (11 - resto);

        if (digitos[10] != expectedDigito2) {
            throw new IllegalArgumentException("CPF inválido.");
        }
    }

    private void validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("E-mail não pode ser nulo ou vazio.");
        }
        // Uma regex mais robusta pode ser necessária dependendo do seu requisito de validação
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
    }

    // --- Métodos Padrão de Objeto (Overrides) ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aluno aluno = (Aluno) o;
        // Para Aluno, CPF é geralmente o identificador único mais forte, além do idAluno.
        // Se idAluno é auto-gerado e só está disponível após persistência, usar CPF é crucial.
        return idAluno == aluno.idAluno &&
               cpf.equals(aluno.cpf);
    }

    @Override
    public int hashCode() {
        // Hashing baseado nos mesmos campos usados no equals
        return Objects.hash(idAluno, cpf);
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "idAluno=" + idAluno +
                ", idCurso=" + idCurso +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", ativo=" + ativo +
                '}';
    }
}