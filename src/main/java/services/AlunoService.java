package services;

import model.Aluno;
import model.Curso;
import dao.AlunoDao;
import dao.CursoDao;
import exceptions.AlunoInvalidoException;
import exceptions.CursoInvalidoException;
import exceptions.ErroSistemaException;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import model.enums.Status;
import utils.FileSaver;

public class AlunoService {

    private final AlunoDao alunoDao;
    private final CursoDao cursoDao;

    public AlunoService(AlunoDao alunoDao, CursoDao cursoDao) {
        this.alunoDao = alunoDao;
        this.cursoDao = cursoDao;
    }

    private void validarCurso(int id) throws SQLException {
        if (!cursoDao.estaAtivo(id)) {
            throw new CursoInvalidoException("Curso não está ativo.");
        }
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
            validarCurso(idCurso);
        } catch (CursoInvalidoException | SQLException e) {
            erros.add(e.getMessage());
        }

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
            if (cursoDao.cursoCheio(idCurso)) {
                throw new CursoInvalidoException("O curso já está cheio.");
            } else {
                alunoDao.adicionar(novoAluno);
            }
            System.out.println("Aluno '" + nome + "' adicionado com sucesso!");
        } catch (SQLException e) {
            throw new ErroSistemaException(
                    "Falha ao cadastrar o aluno devido a um erro no banco de dados.", e);
        }

    }

    public void atualizarAluno(int idAluno, int idCurso, String nome, String cpf, String telefone, String email,
            LocalDate dataNascimento, Status status) {
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
        alunoParaAtualizar.setAtivo(status);

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

    public Optional<Aluno> obterAlunoPorCpf(String cpf) {
        try {
            return alunoDao.buscarPorCpf(cpf);
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

    public List<Aluno> listarAlunos(Status filterStatus) {
        try {
            if (filterStatus == null) {
                return alunoDao.buscarTodos();
            } else {
                return alunoDao.buscarTodosFiltrado(filterStatus);
            }
        } catch (SQLException e) {
            throw new ErroSistemaException(
                    "Falha ao obter a lista de alunos devido a um erro no banco de dados. " + e.getMessage(), e);
        }
    }

    public List<Aluno> listarAlunosPorCurso(int idCurso, Status filterStatus) {
        try {
            if (filterStatus == null) {
                return alunoDao.buscarAlunosPorCurso(idCurso);
            } else {
                return alunoDao.buscarAlunosPorCurso(idCurso, filterStatus);
            }
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
                if (alunoDao.deletar(id)) {
                    System.out.println("Aluno com ID " + id + " removido com sucesso.");
                } else {
                    throw new AlunoInvalidoException("Aluno com ID " + id + " não pôde ser removido.");
                }
            } else {
                throw new AlunoInvalidoException("Aluno com ID " + id + " não encontrado para remoção.");
            }
        } catch (SQLException e) {
            throw new ErroSistemaException(
                    "Falha ao remover aluno devido a um erro no banco de dados. " + e.getMessage(), e);
        }
    }

    public void desativar(int id) {
        Optional<Aluno> alunoDesativar;
        try {
            alunoDesativar = alunoDao.buscarPorId(id);
            if (alunoDesativar.isPresent()) {
                if (alunoAtivo(id)) {
                    alunoDao.desativar(id);
                    System.out.println("Aluno com ID " + id + " desativado com sucesso.");
                }
            } else {
                throw new AlunoInvalidoException("Aluno com ID" + id + " não encontrado para desativação.");
            }
        } catch (SQLException e) {
            throw new ErroSistemaException(
                    "Falha ao desativar aluno devido a um erro no banco de dados. " + e.getMessage(), e);
        }
    }

    public void ativar(int id) {
        Optional<Aluno> alunoAtivar;
        try {
            alunoAtivar = alunoDao.buscarPorId(id);
            if (alunoAtivar.isPresent()) {
                if (!alunoAtivo(id)) {
                    alunoDao.ativar(id);
                    System.out.println("Aluno com ID " + id + " ativado com sucesso.");
                }
            } else {
                throw new AlunoInvalidoException("Aluno com ID" + id + " não encontrado para ativação.");
            }
        } catch (SQLException e) {
            throw new ErroSistemaException(
                    "Falha ao ativar aluno devido a um erro no banco de dados. " + e.getMessage(), e);
        }
    }

    public boolean alunoAtivo(int id) {
        Optional<Aluno> alunoVerificar;
        try {
            alunoVerificar = alunoDao.buscarPorId(id);
            if (alunoVerificar.isPresent()) {
                return alunoDao.estaAtivo(id);
            } else {
                throw new AlunoInvalidoException("Aluno com ID" + id + " não encontrado.");
            }
        } catch (SQLException e) {
            throw new ErroSistemaException(
                    "Falha ao verificar aluno devido a um erro no banco de dados. " + e.getMessage(), e);
        }
    }

    public boolean existe(int id) {
        try {
            return alunoDao.existeAluno(id);
        } catch (SQLException e) {
            throw new ErroSistemaException("Falha ao consultar existência do aluno no banco de dados.", e);
        }
    }

    /**
     *
     * @param caminhoArquivo
     * @param alunos
     */
    public void exportarAlunos(String caminhoArquivo, List<Aluno> alunos) {
        List<String> linhasParaSalvar = new ArrayList<>();

        linhasParaSalvar.add(
                "idCurso;nomeCurso;idAluno;nomeAluno;cpf;telefone;email;dataNascimento");
        try {
            for (Aluno aluno : alunos) {
                if (aluno != null) {
                    Curso curso = cursoDao.buscarPorId(aluno.getIdCurso()).orElse(null);
                    if (curso != null) {
                        linhasParaSalvar.add(String.format("%d;%s;%d;%s;%s;%s;%s;%s",
                                curso.getIdCurso(), curso.getNome(),
                                aluno.getIdAluno(), aluno.getNome(),
                                aluno.getCpf(), aluno.getTelefone(),
                                aluno.getEmail(), aluno.getDataNascimento()));
                    }
                }
            }

            // Usa o FileSaver genérico para salvar a lista de strings formatadas
            FileSaver.saveListToFile(caminhoArquivo, linhasParaSalvar);
            System.out.println("Cursos e alunos exportados com sucesso para: " + caminhoArquivo);
        } catch (SQLException | IOException e) {
            throw new ErroSistemaException("Erro ao salvar arquivo.", e);
        }
    }

}
