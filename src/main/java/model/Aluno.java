package model;

import java.time.LocalDate;
import java.time.Period;

public class Aluno {

    private final int idAluno;
    private int idCurso;
    private String nome;
    private final String cpf;
    private String telefone;
    private String email;
    private boolean ativo;
    private final LocalDate dataNascimento;

    public Aluno(int idAluno, int idCurso, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) {
        validarNome(nome);
        validarIdade(dataNascimento);
        validarCpf(cpf);
        validarEmail(email);
        this.idAluno = idAluno;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.ativo = true;
        this.idCurso = idCurso;
    }

    public int getIdAluno() {
        return this.idAluno;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdCurso() {
        return this.idCurso;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public String getCpf() {
        return this.cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        validarNome(nome);
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAtivo(boolean status) {
        this.ativo = status;
    }

    public boolean getAtivo() {
        return this.ativo;
    }

    private void validarNome(String nome) {
        if (nome == null || nome.length() < 3) {
            throw new IllegalArgumentException("O nome deve ter no mínimo 3 caracteres.");
        }
    }

    private void validarIdade(LocalDate birthDate) {
        Period p = Period.between(birthDate, LocalDate.now());
        if (p.getYears() < 16) {
            throw new IllegalArgumentException("O idade mínima é de 16 anos.");
        }
    }

    private void validarCpf(String cpf) throws IllegalArgumentException {
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

        int digitoVerificador1 = digitos[9];
        int digitoVerificador2 = digitos[10];

        int sum = 0;
        int pos = 10;
        for (int i = 0; i < 9; i++) {
            sum += digitos[i] * pos;
            pos--;
        }
        int resto = sum % 11;
        int expectedDigito1 = (resto < 2) ? 0 : (11 - resto);

        if (digitoVerificador1 != expectedDigito1) {
            throw new IllegalArgumentException("CPF inválido.");
        }

        sum = 0;
        pos = 11;
        for (int i = 0; i < 10; i++) {
            sum += digitos[i] * pos;
            pos--;
        }
        resto = sum % 11;
        int expectedDigito2 = (resto < 2) ? 0 : (11 - resto);

        if (digitoVerificador2 != expectedDigito2) {
            throw new IllegalArgumentException("CPF inválido.");
        }
    }

    private void validarEmail(String email) {
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(idAluno);
        sb.append(", ");
        sb.append(idCurso);
        sb.append(", ");
        sb.append(nome);
        sb.append(", ");
        sb.append(cpf);
        sb.append(", ");
        sb.append(telefone);
        sb.append(", ");
        sb.append(email);
        sb.append(", ");
        sb.append(dataNascimento);
        sb.append("\n");
        return sb.toString();
    }
}
