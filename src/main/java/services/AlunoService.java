package services;

import model.Aluno;
import dao.AlunoDao;
import exceptions.AlunoInvalidoException;
import exceptions.ErroSistemaException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList; // Para coletar múltiplos erros

public class AlunoService {
    private AlunoDao alunoDao;

    public AlunoService(AlunoDao alunoDao) {
        this.alunoDao = alunoDao;
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().length() < 3) {
            throw new AlunoInvalidoException("Nome do aluno deve ter no mínimo 3 caracteres.");
        }
    }

    private void validarIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            throw new AlunoInvalidoException("Data de nascimento não pode ser nula.");
        }
        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
        if (idade < 16) {
            throw new AlunoInvalidoException("Aluno deve ter no mínimo 16 anos.");
        }
    }

    private void validarCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new AlunoInvalidoException("CPF deve conter exatamente 11 dígitos numéricos.");
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            throw new AlunoInvalidoException("CPF inválido: Dígitos repetidos.");
        }

        int[] digitos = new int[11];
        for (int i = 0; i < 11; i++) {
            digitos[i] = Character.getNumericValue(cpf.charAt(i));
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
            throw new AlunoInvalidoException("CPF inválido.");
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
            throw new AlunoInvalidoException("CPF inválido.");
        }
    }

    private void validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new AlunoInvalidoException("E-mail não pode ser vazio.");
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new AlunoInvalidoException("Formato de e-mail inválido.");
        }
    }

    private void validarTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new AlunoInvalidoException("Telefone não pode ser vazio.");
        }
        if (!telefone.matches("^\\d{10,11}$")) {
            throw new AlunoInvalidoException(
                    "Formato de telefone inválido. Deve conter 10 ou 11 dígitos numéricos (com DDD).");
        }
    }

    // --- Métodos de Negócio ---

    public void adicionarAluno(int idCurso, String nome, String cpf, String telefone, String email,
            LocalDate dataNascimento) {
        // Lógica para coletar e lançar múltiplos erros
        List<String> erros = new ArrayList<>();
        try {
            validarNome(nome);
        } catch (AlunoInvalidoException e) {
            erros.add(e.getMessage());
        }
        try {
            validarIdade(dataNascimento);
        } catch (AlunoInvalidoException e) {
            erros.add(e.getMessage());
        }
        try {
            validarCpf(cpf);
        } catch (AlunoInvalidoException e) {
            erros.add(e.getMessage());
        }
        try {
            validarEmail(email);
        } catch (AlunoInvalidoException e) {
            erros.add(e.getMessage());
        }
        try {
            validarTelefone(telefone);
        } catch (AlunoInvalidoException e) {
            erros.add(e.getMessage());
        }

        if (!erros.isEmpty()) {
            throw new AlunoInvalidoException(String.join("\n", erros));
        }

        // Se todas as validações passarem, cria e salva o aluno
        Aluno novoAluno = new Aluno(idCurso, nome, cpf, telefone, email, dataNascimento);
        try {
            alunoDao.adicionar(novoAluno);
            System.out.println("Aluno '" + nome + "' adicionado com sucesso!");
        } catch (SQLException e) {
            throw new ErroSistemaException(
                    "Falha ao cadastrar o aluno devido a um erro no banco de dados. " + e.getMessage(), e);
        }

    }

    public void atualizarAluno(int idAluno, int idCurso, String nome, String cpf, String telefone, String email,
            LocalDate dataNascimento) {
        Optional<Aluno> alunoExistenteOpt;
        try {
            alunoExistenteOpt = alunoDao.buscarPorId(idAluno);
        } catch (SQLException e) {
            throw new ErroSistemaException("Ocorreu um erro no banco de dados ao buscar o aluno.", e);
        }
        if (alunoExistenteOpt.isEmpty()) {
            throw new AlunoInvalidoException("Aluno com ID " + idAluno + " não encontrado para atualização.");
        }

        Aluno alunoParaAtualizar = alunoExistenteOpt.get();

        // Lógica para coletar e lançar múltiplos erros
        List<String> erros = new ArrayList<>();
        try {
            validarNome(nome);
        } catch (AlunoInvalidoException e) {
            erros.add(e.getMessage());
        }
        try {
            validarIdade(dataNascimento);
        } catch (AlunoInvalidoException e) {
            erros.add(e.getMessage());
        }
        try {
            validarCpf(cpf);
        } catch (AlunoInvalidoException e) {
            erros.add(e.getMessage());
        } // Passa o ID do aluno para ignorar ele mesmo na unicidade
        try {
            validarEmail(email);
        } catch (AlunoInvalidoException e) {
            erros.add(e.getMessage());
        } // Passa o ID do aluno para ignorar ele mesmo na unicidade
        try {
            validarTelefone(telefone);
        } catch (AlunoInvalidoException e) {
            erros.add(e.getMessage());
        }

        if (!erros.isEmpty()) {
            throw new AlunoInvalidoException(String.join("\n", erros));
        }

        // Se as validações passarem, atualiza os dados do objeto e salva
        alunoParaAtualizar.setIdCurso(idCurso);
        alunoParaAtualizar.setNome(nome);
        alunoParaAtualizar.setCpf(cpf);
        alunoParaAtualizar.setTelefone(telefone);
        alunoParaAtualizar.setEmail(email);
        alunoParaAtualizar.setDataNascimento(dataNascimento);

        try {
            alunoDao.atualizar(alunoParaAtualizar);
            System.out.println("Aluno '" + nome + "' atualizado com sucesso!");
        } catch (SQLException e) {
            throw new ErroSistemaException(
                    "Falha ao atualizar o aluno devido a um erro no banco de dados. " + e.getMessage(), e);
        }

    }

    public Optional<Aluno> obterAlunoPorId(int id) {
        try {
            return alunoDao.buscarPorId(id);
        } catch (SQLException e) {
            throw new ErroSistemaException(
                    "Falha ao obter aluno devido a um erro no banco de dados. " + e.getMessage(), e);
        }
    }

    public List<Aluno> listarTodosAlunos() {
        try {
            return alunoDao.buscarTodos();
        } catch (SQLException e) {
            throw new ErroSistemaException(
                    "Falha ao obter a lista de alunos devido a um erro no banco de dados. " + e.getMessage(), e);
        }
    }

    public void removerAluno(int id) {
        Optional<Aluno> alunoExistente;
        try {
            alunoExistente = alunoDao.buscarPorId(id);
            if (alunoExistente.isPresent()) {
                alunoDao.deletar(id);
                System.out.println("Aluno com ID " + id + " removido com sucesso.");
            } else {
                throw new AlunoInvalidoException("Aluno com ID " + id + " não encontrado para remoção.");
            }
        } catch (SQLException e) {
            throw new ErroSistemaException(
                    "Falha ao remover aluno devido a um erro no banco de dados. " + e.getMessage(), e);
        }
    }
}