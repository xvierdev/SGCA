package cli;

import exceptions.AlunoInvalidoException;
import exceptions.ErroSistemaException;
import exceptions.CursoInvalidoException;
import model.Aluno;
import services.AlunoService;
import services.CursoService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;
import model.enums.Status;

public class GerenciarAlunos {

    private AlunoService alunoService;

    // Construtor agora recebe as instâncias de serviço
    public GerenciarAlunos(AlunoService alunoService, CursoService cursoService) {
        this.alunoService = alunoService;
    }

    public void menuGerenciarAlunos(Scanner sc) {
        while (true) {
            System.out.println("\n=== Gerenciamento de Alunos ===");
            System.out.println("1. Adicionar Aluno");
            System.out.println("2. Buscar Aluno por CPF");
            System.out.println("3. Buscar Aluno por ID");
            System.out.println("4. Atualizar Aluno");
            System.out.println("5. Remover Aluno");
            System.out.println("6. Ativar Aluno");
            System.out.println("7. Desativar Aluno");
            System.out.println("8. Verificar se Aluno Está Ativo");
            System.out.println("9. Verificar se Aluno Existe por CPF");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            if (!sc.hasNextInt()) {
                System.out.println("Entrada inválida. Digite um número.");
                sc.next();
                continue;
            }
            int opcao = sc.nextInt();
            sc.nextLine(); // Consumir a nova linha
            LimpaConsole.Limpar();

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
                        atualizarAluno(sc);
                        break;
                    case 5:
                        removerAluno(sc);
                        break;
                    case 6:
                        ativarAluno(sc);
                        break;
                    case 7:
                        desativarAluno(sc);
                        break;
                    case 8:
                        verificarAlunoAtivo(sc);
                        break;
                    case 9:
                        verificarAlunoExiste(sc);
                        break;
                    case 0:
                        return; // Volta ao menu principal
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (AlunoInvalidoException e) {
                System.err.println("Erro de validação do Aluno: " + e.getMessage());
            } catch (CursoInvalidoException e) { // Caso o AlunoService interaja com CursoService e lance essa exceção
                System.err.println("Erro de validação do Curso (relacionado ao Aluno): " + e.getMessage());
            } catch (ErroSistemaException e) {
                System.err.println("Erro interno do sistema: " + e.getMessage());
                // Opcional: e.printStackTrace(); para depuração mais detalhada
            } catch (DateTimeParseException e) {
                System.err.println("Formato de data inválido. Use AAAA-MM-DD. " + e.getMessage());
            } catch (Exception e) { // Capturar outras exceções inesperadas
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void adicionarAluno(Scanner sc) {
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
            // Chamar o serviço para adicionar o aluno
            alunoService.adicionarAluno(idCurso, nome, cpf, telefone, email, dataNascimento);
            // Mensagem de sucesso já é exibida pelo serviço
        } catch (AlunoInvalidoException | ErroSistemaException e) {
            // Exceções já tratadas no menu principal
            throw e; // Re-lançar para ser capturado pelo try-catch do menu
        }
    }

    private void buscarAlunoPorCpf(Scanner sc) throws ErroSistemaException {
        System.out.print("CPF do Aluno: ");
        String cpf = sc.nextLine();

        Optional<Aluno> alunoOpt = alunoService.obterAlunoPorCpf(cpf);
        if (alunoOpt.isPresent()) {
            System.out.println("--- Dados do Aluno ---");
            System.out.println(alunoOpt.get());
        } else {
            System.out.println("Aluno não encontrado com o CPF: " + cpf);
        }
    }

    private void buscarAlunoPorId(Scanner sc) throws ErroSistemaException {
        System.out.print("ID do Aluno: ");
        int id = sc.nextInt();
        sc.nextLine();

        Optional<Aluno> alunoOpt = alunoService.obterAlunoPorId(id);
        if (alunoOpt.isPresent()) {
            System.out.println("--- Dados do Aluno ---");
            System.out.println(alunoOpt.get());
        } else {
            System.out.println("Aluno não encontrado com o ID: " + id);
        }

    }

    private void atualizarAluno(Scanner sc) {
        System.out.print("ID do Aluno a ser atualizado: ");
        int id = sc.nextInt();
        sc.nextLine();

        Optional<Aluno> alunoExistenteOpt;
        try {
            alunoExistenteOpt = alunoService.obterAlunoPorId(id);
        } catch (ErroSistemaException e) {
            throw e; // Re-lançar para ser capturado pelo try-catch do menu
        }

        if (alunoExistenteOpt.isEmpty()) {
            System.out.println("Aluno não encontrado com o ID: " + id);
            return;
        }

        Aluno alunoExistente = alunoExistenteOpt.get();
        System.out.println("Informações atuais: " + alunoExistente);

        // Coleta de novas informações
        System.out.print("Novo ID do Curso (deixe em branco para manter " + alunoExistente.getIdCurso() + "): ");
        String novoIdCursoStr = sc.nextLine();
        int novoIdCurso = alunoExistente.getIdCurso(); // Manter o original por padrão
        if (!novoIdCursoStr.trim().isEmpty()) {
            try {
                novoIdCurso = Integer.parseInt(novoIdCursoStr);
            } catch (NumberFormatException e) {
                System.out.println("ID do curso inválido, mantendo o original.");
            }
        }

        System.out.print("Novo Nome (deixe em branco para manter '" + alunoExistente.getNome() + "'): ");
        String novoNome = sc.nextLine();
        if (novoNome.trim().isEmpty()) {
            novoNome = alunoExistente.getNome();
        }

        System.out.print("Novo CPF (deixe em branco para manter '" + alunoExistente.getCpf() + "'): ");
        String novoCpf = sc.nextLine();
        if (novoCpf.trim().isEmpty()) {
            novoCpf = alunoExistente.getCpf();
        }

        System.out.print("Novo Telefone (deixe em branco para manter '" + alunoExistente.getTelefone() + "'): ");
        String novoTelefone = sc.nextLine();
        if (novoTelefone.trim().isEmpty()) {
            novoTelefone = alunoExistente.getTelefone();
        }

        System.out.print("Novo Email (deixe em branco para manter '" + alunoExistente.getEmail() + "'): ");
        String novoEmail = sc.nextLine();
        if (novoEmail.trim().isEmpty()) {
            novoEmail = alunoExistente.getEmail();
        }

        System.out.print("Nova Data de Nascimento (AAAA-MM-DD, deixe em branco para manter '"
                + alunoExistente.getDataNascimento() + "'): ");
        String novaDataNascimentoStr = sc.nextLine();
        LocalDate novaDataNascimento = alunoExistente.getDataNascimento(); // Manter o original
        if (!novaDataNascimentoStr.trim().isEmpty()) {
            try {
                novaDataNascimento = LocalDate.parse(novaDataNascimentoStr);
            } catch (DateTimeParseException e) {
                System.err.println("Formato de data inválido. Data não alterada.");
            }
        }

        try {
            alunoService.atualizarAluno(id, novoIdCurso, novoNome, novoCpf, novoTelefone, novoEmail,
                    novaDataNascimento, Status.ATIVO);
            // Mensagem de sucesso já é exibida pelo serviço
        } catch (AlunoInvalidoException | ErroSistemaException e) {
            throw e; // Re-lançar para ser capturado pelo try-catch do menu
        }
    }

    private void removerAluno(Scanner sc) throws AlunoInvalidoException, ErroSistemaException {
        System.out.print("ID do Aluno a ser removido: ");
        int id = sc.nextInt();
        sc.nextLine();
        alunoService.removerAluno(id);
    }

    private void ativarAluno(Scanner sc) throws AlunoInvalidoException, ErroSistemaException {
        System.out.print("Id do Aluno a ser ativado: ");
        int id = sc.nextInt();
        sc.nextLine();
        alunoService.ativar(id);
        System.out.println("Aluno ativado com sucesso!");
    }

    private void desativarAluno(Scanner sc) throws AlunoInvalidoException, ErroSistemaException {
        System.out.print("Id do Aluno a ser desativado: ");
        int id = sc.nextInt();
        sc.nextLine();
        alunoService.desativar(id);
        System.out.println("Aluno desativado com sucesso!");

    }

    private void verificarAlunoAtivo(Scanner sc) throws ErroSistemaException {
        System.out.print("Id do Aluno para verificar status: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (alunoService.alunoAtivo(id)) {
            System.out.println("O aluno com Id " + id + " está ATIVO.");
        } else {
            System.out.println("O aluno com Id " + id + " está INATIVO.");
        }
    }

    private void verificarAlunoExiste(Scanner sc) throws ErroSistemaException {
        System.out.print("Id do Aluno para verificar existência: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (alunoService.existe(id)) {
            System.out.println("Aluno com Id " + id + " existe.");
        } else {
            System.out.println("Aluno com Id " + id + " NÃO existe.");
        }

    }
}
