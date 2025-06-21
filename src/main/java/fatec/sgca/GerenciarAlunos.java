package fatec.sgca; // Assumindo que GerenciarAlunos está no mesmo pacote de Teste

import dao.AlunoDao;
import dao.CursoDao; // Necessário para exibir informações do curso ao listar
import model.Aluno;
import model.Curso; // Para exibir nome do curso
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class GerenciarAlunos {

    private AlunoDao alunoDao;

    public GerenciarAlunos() throws SQLException { // Construtor pode lançar SQLException
        this.alunoDao = new AlunoDao();
    }

    public void menuGerenciarAlunos(Scanner sc) {
        while (true) {
            System.out.println("\n=== Gerenciamento de Alunos ===");
            System.out.println("1. Adicionar Aluno");
            System.out.println("2. Buscar Aluno por CPF");
            System.out.println("3. Buscar Aluno por ID");
            System.out.println("4. Buscar Todos os Alunos");
            System.out.println("5. Atualizar Aluno");
            System.out.println("6. Remover Aluno por CPF");
            System.out.println("7. Ativar Aluno");
            System.out.println("8. Desativar Aluno");
            System.out.println("9. Verificar se Aluno Está Ativo");
            System.out.println("10. Verificar se Aluno Existe por CPF");
            System.out.println("11. Listar Alunos de um Curso Específico"); // Implementação extra
            System.out.println("12. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            if (!sc.hasNextInt()) {
                System.out.println("Entrada inválida. Digite um número.");
                sc.next();
                continue;
            }
            int opcao = sc.nextInt();
            sc.nextLine(); // Consumir a nova linha

            try {
                switch (opcao) {
                    case 1:
                        adicionarAluno(sc);
                        break;
                    case 2:
                        buscarAlunoPorCpf(sc);
                        break;
                    case 3:
                        buscarAlunoPorId(sc);
                        break;
                    case 4:
                        buscarTodosAlunos();
                        break;
                    case 5:
                        atualizarAluno(sc);
                        break;
                    case 6:
                        removerAluno(sc);
                        break;
                    case 7:
                        ativarAluno(sc);
                        break;
                    case 8:
                        desativarAluno(sc);
                        break;
                    case 9:
                        verificarAlunoAtivo(sc);
                        break;
                    case 10:
                        verificarAlunoExiste(sc);
                        break;
                    case 11:
                        listarAlunosDeCursoEspecifico(sc);
                        break; // Nova função
                    case 12:
                        return; // Volta ao menu principal
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (SQLException e) {
                System.err.println("Erro de banco de dados: " + e.getMessage());
                e.printStackTrace();
            } catch (IllegalArgumentException e) { // Captura exceções de validação do modelo
                System.err.println("Erro de validação: " + e.getMessage());
            } catch (IllegalStateException e) { // Captura exceções de regra de negócio (curso cheio/inativo)
                System.err.println("Erro de regra de negócio: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void adicionarAluno(Scanner sc) throws SQLException {
        System.out.print("ID do Curso: ");
        int idCurso = sc.nextInt();
        sc.nextLine();

        System.out.print("Nome do Aluno: ");
        String nome = sc.nextLine();
        System.out.print("CPF (11 dígitos, apenas números): ");
        String cpf = sc.nextLine();
        System.out.print("Telefone: ");
        String telefone = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Data de Nascimento (AAAA-MM-DD): ");
        String dataNascimentoStr = sc.nextLine();
        LocalDate dataNascimento;
        try {
            dataNascimento = LocalDate.parse(dataNascimentoStr);
        } catch (DateTimeParseException e) {
            System.err.println("Formato de data inválido. Use AAAA-MM-DD.");
            return;
        }

        try {
            // A classe Aluno valida os dados ao ser instanciada
            Aluno novoAluno = new Aluno(0, idCurso, nome, cpf, telefone, email, dataNascimento, true); // ID será gerado
                                                                                                       // pelo banco
            Aluno alunoSalvo = alunoDao.adicionar(novoAluno);
            if (alunoSalvo != null) {
                System.out.println("Aluno adicionado com sucesso! ID: " + alunoSalvo.getIdAluno());
            } else {
                System.out.println("Falha ao adicionar aluno.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de validação: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("Erro de regra de negócio: " + e.getMessage());
        }
    }

    private void buscarAlunoPorCpf(Scanner sc) throws SQLException {
        System.out.print("CPF do Aluno: ");
        String cpf = sc.nextLine();

        Aluno aluno = alunoDao.buscarPorCpf(cpf);
        if (aluno != null) {
            System.out.println("--- Dados do Aluno ---");
            System.out.println(aluno); // Assume que Aluno tem um bom toString()
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    private void buscarAlunoPorId(Scanner sc) throws SQLException {
        System.out.print("ID do Aluno: ");
        int id = sc.nextInt();
        sc.nextLine();

        Aluno aluno = alunoDao.buscarPorId(id);
        if (aluno != null) {
            System.out.println("--- Dados do Aluno ---");
            System.out.println(aluno); // Assume que Aluno tem um bom toString()
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    private void buscarTodosAlunos() throws SQLException {
        List<Aluno> alunos = alunoDao.buscarTodos();
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            System.out.println("\n--- Todos os Alunos ---");
            for (Aluno aluno : alunos) {
                System.out.println(aluno); // Imprime cada aluno
            }
        }
    }

    private void atualizarAluno(Scanner sc) throws SQLException {
        System.out.print("ID do Aluno a ser atualizado: "); // Atualizando pelo ID agora
        int id = sc.nextInt();
        sc.nextLine();

        Aluno alunoExistente = alunoDao.buscarPorId(id);
        if (alunoExistente == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        System.out.println("Informações atuais: " + alunoExistente);
        System.out.print("Novo ID do Curso (0 para manter " + alunoExistente.getIdCurso() + "): ");
        int novoIdCurso = sc.nextInt();
        if (novoIdCurso != 0) {
            alunoExistente.setIdCurso(novoIdCurso);
        }
        sc.nextLine();

        System.out.print("Novo Nome (deixe em branco para manter '" + alunoExistente.getNome() + "'): ");
        String novoNome = sc.nextLine();
        if (!novoNome.isEmpty()) {
            alunoExistente.setNome(novoNome);
        }

        System.out.print("Novo Telefone (deixe em branco para manter '" + alunoExistente.getTelefone() + "'): ");
        String novoTelefone = sc.nextLine();
        if (!novoTelefone.isEmpty()) {
            alunoExistente.setTelefone(novoTelefone);
        }

        System.out.print("Novo Email (deixe em branco para manter '" + alunoExistente.getEmail() + "'): ");
        String novoEmail = sc.nextLine();
        if (!novoEmail.isEmpty()) {
            alunoExistente.setEmail(novoEmail);
        }

        System.out.print("Nova Data de Nascimento (AAAA-MM-DD, deixe em branco para manter '"
                + alunoExistente.getDataNascimento() + "'): ");
        String novaDataNascimentoStr = sc.nextLine();
        if (!novaDataNascimentoStr.isEmpty()) {
            try {
                alunoExistente.setDataNascimento(LocalDate.parse(novaDataNascimentoStr));
            } catch (DateTimeParseException e) {
                System.err.println("Formato de data inválido. Data não alterada.");
            }
        }

        System.out.print("Aluno Ativo? (s/n, atual: " + (alunoExistente.isAtivo() ? "sim" : "não") + "): ");
        String ativoStr = sc.nextLine().toLowerCase();
        if (ativoStr.equals("s")) {
            alunoExistente.setAtivo(true);
        } else if (ativoStr.equals("n")) {
            alunoExistente.setAtivo(false);
        }

        try {
            if (alunoDao.atualizar(alunoExistente)) {
                System.out.println("Aluno atualizado com sucesso!");
            } else {
                System.out.println("Falha ao atualizar aluno.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de validação: " + e.getMessage());
        }
    }

    private void removerAluno(Scanner sc) throws SQLException {
        System.out.print("CPF do Aluno a ser removido: ");
        String cpf = sc.nextLine();

        if (alunoDao.remover(cpf)) {
            System.out.println("Aluno removido com sucesso!");
        } else {
            System.out.println("Falha ao remover aluno (CPF não encontrado ou erro no banco).");
        }
    }

    private void ativarAluno(Scanner sc) throws SQLException {
        System.out.print("CPF do Aluno a ser ativado: ");
        String cpf = sc.nextLine();

        if (alunoDao.ativar(cpf)) {
            System.out.println("Aluno ativado com sucesso!");
        } else {
            System.out.println("Falha ao ativar aluno (CPF não encontrado ou já ativo).");
        }
    }

    private void desativarAluno(Scanner sc) throws SQLException {
        System.out.print("CPF do Aluno a ser desativado: ");
        String cpf = sc.nextLine();

        if (alunoDao.desativar(cpf)) {
            System.out.println("Aluno desativado com sucesso!");
        } else {
            System.out.println("Falha ao desativar aluno (CPF não encontrado ou já inativo).");
        }
    }

    private void verificarAlunoAtivo(Scanner sc) throws SQLException {
        System.out.print("CPF do Aluno para verificar status: ");
        String cpf = sc.nextLine();

        if (alunoDao.estaAtivo(cpf)) {
            System.out.println("O aluno com CPF " + cpf + " está ATIVO.");
        } else {
            System.out.println("O aluno com CPF " + cpf + " está INATIVO ou não existe.");
        }
    }

    private void verificarAlunoExiste(Scanner sc) throws SQLException {
        System.out.print("CPF do Aluno para verificar existência: ");
        String cpf = sc.nextLine();

        if (alunoDao.existeAluno(cpf)) {
            System.out.println("Aluno com CPF " + cpf + " existe.");
        } else {
            System.out.println("Aluno com CPF " + cpf + " NÃO existe.");
        }
    }

    // --- Nova Função: Listar Alunos de um Curso Específico ---
    private void listarAlunosDeCursoEspecifico(Scanner sc) throws SQLException {
        System.out.print("ID do Curso para listar alunos: ");
        int idCurso = sc.nextInt();
        sc.nextLine();

        CursoDao cursoDao = new CursoDao();
        Curso curso = cursoDao.buscarPorId(idCurso);
        if (curso == null) {
            System.out.println("Curso com ID " + idCurso + " não encontrado.");
            return;
        }
        System.out.println("\n--- Alunos do Curso: " + curso.getNome() + " (ID: " + curso.getIdCurso() + ") ---");

        // Chamada ao novo método no AlunoDao
        List<Aluno> alunosDoCurso = alunoDao.buscarAlunosPorCurso(idCurso);
        if (alunosDoCurso.isEmpty()) {
            System.out.println("Nenhum aluno encontrado neste curso.");
        } else {
            for (Aluno aluno : alunosDoCurso) {
                System.out.println(aluno);
            }
        }
    }
}