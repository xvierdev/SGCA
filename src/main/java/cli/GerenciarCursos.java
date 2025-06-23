package cli;

import exceptions.CursoInvalidoException;
import exceptions.ErroSistemaException;
import model.Curso;
import model.enums.Status;
import services.CursoService;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.Scanner;

public class GerenciarCursos {

    private CursoService cursoService;

    public GerenciarCursos(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    public void menuGerenciarCursos(Scanner sc) {
        while (true) {
            System.out.println("\n=== Gerenciamento de Cursos ===");
            System.out.println("1. Adicionar Curso");
            System.out.println("2. Buscar Curso por ID");
            System.out.println("3. Atualizar Curso");
            System.out.println("4. Remover Curso");
            System.out.println("5. Desativar Curso");
            System.out.println("6. Ativar Curso");
            System.out.println("7. Verificar se Curso Está Ativo");
            System.out.println("8. Buscar ID de Curso por Nome");
            System.out.println("9. Obter Limite de Alunos do Curso");
            System.out.println("10. Verificar se Curso Está Cheio");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            if (!sc.hasNextInt()) {
                System.out.println("Entrada inválida. Digite um número.");
                sc.next();
                continue;
            }
            int opcao = sc.nextInt();
            sc.nextLine();
            LimpaConsole.Limpar();

            try {
                switch (opcao) {
                    case 1:
                        adicionarCurso(sc);
                        break;
                    case 2:
                        buscarCursoPorId(sc);
                        break;
                    case 3:
                        atualizarCurso(sc);
                        break;
                    case 4:
                        removerCurso(sc);
                        break;
                    case 5:
                        desativarCurso(sc);
                        break;
                    case 6:
                        ativarCurso(sc);
                        break;
                    case 7:
                        verificarCursoAtivo(sc);
                        break;
                    case 8:
                        buscarIdCursoPorNome(sc);
                        break;
                    case 9:
                        obterLimiteAlunosCurso(sc);
                        break;
                    case 10:
                        verificarCursoCheio(sc);
                        break;
                    case 0:
                        return; // Volta ao menu principal
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (CursoInvalidoException e) {
                System.err.println("Erro de validação do Curso: " + e.getMessage());
            } catch (ErroSistemaException e) {
                System.err.println("Erro interno do sistema: " + e.getMessage());
                // Opcional: e.printStackTrace(); para depuração mais detalhada
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void adicionarCurso(Scanner sc) throws CursoInvalidoException, ErroSistemaException {
        System.out.print("Nome do Curso: ");
        String nome = sc.nextLine();
        System.out.print("Carga Horária: ");
        int cargaHoraria = sc.nextInt();
        sc.nextLine();
        System.out.print("Limite de Alunos: ");
        int limiteAlunos = sc.nextInt();
        sc.nextLine(); // Consumir nova linha
        cursoService.adicionarCurso(nome, cargaHoraria, limiteAlunos);
    }

    private void buscarCursoPorId(Scanner sc) throws ErroSistemaException {
        System.out.print("ID do Curso: ");
        int id = sc.nextInt();
        sc.nextLine();

        Optional<Curso> cursoOpt = cursoService.obterCursoPorId(id);
        if (cursoOpt.isPresent()) {
            System.out.println("--- Dados do Curso ---");
            System.out.println(cursoOpt.get());
        } else {
            System.out.println("Curso não encontrado com o ID: " + id);
        }
    }

    private void atualizarCurso(Scanner sc) {
        System.out.print("ID do Curso a ser atualizado: ");
        int id = sc.nextInt();
        sc.nextLine();

        Optional<Curso> cursoExistenteOpt;
        try {
            cursoExistenteOpt = cursoService.obterCursoPorId(id);
        } catch (ErroSistemaException e) {
            throw e; // Re-lançar para ser capturado pelo try-catch do menu
        }

        if (cursoExistenteOpt.isEmpty()) {
            System.out.println("Curso não encontrado com o ID: " + id);
            return;
        }

        Curso cursoExistente = cursoExistenteOpt.get();
        System.out.println("Informações atuais: " + cursoExistente);

        System.out.print("Novo Nome (deixe em branco para manter '" + cursoExistente.getNome() + "'): ");
        String novoNome = sc.nextLine();
        if (novoNome.trim().isEmpty()) {
            novoNome = cursoExistente.getNome();
        }

        System.out.print("Nova Carga Horária (0 para manter " + cursoExistente.getCargaHoraria() + "): ");
        String novaCargaHorariaStr = sc.nextLine();
        int novaCargaHoraria = cursoExistente.getCargaHoraria();
        if (!novaCargaHorariaStr.trim().isEmpty()) {
            try {
                novaCargaHoraria = Integer.parseInt(novaCargaHorariaStr);
            } catch (NumberFormatException e) {
                System.out.println("Carga horária inválida, mantendo a original.");
            }
        }

        System.out.print("Novo Limite de Alunos (0 para manter " + cursoExistente.getLimiteAlunos() + "): ");
        String novoLimiteAlunosStr = sc.nextLine();
        int novoLimiteAlunos = cursoExistente.getLimiteAlunos();
        if (!novoLimiteAlunosStr.trim().isEmpty()) {
            try {
                novoLimiteAlunos = Integer.parseInt(novoLimiteAlunosStr);
            } catch (NumberFormatException e) {
                System.out.println("Limite de alunos inválido, mantendo o original.");
            }
        }

        System.out.print(
                "Curso Ativo? (s/n, atual: " + (cursoExistente.isAtivo() == Status.ATIVO ? "sim" : "não") + "): ");
        String ativoStr = sc.nextLine().toLowerCase();
        Status ativo = cursoExistente.isAtivo(); // Manter o original por padrão
        if (ativoStr.equals("s")) {
            ativo = Status.ATIVO;
        } else if (ativoStr.equals("n")) {
            ativo = Status.INATIVO;
        }

        try {
            cursoService.atualizarCurso(id, novoNome, novaCargaHoraria, novoLimiteAlunos, ativo);
            // Mensagem de sucesso já é exibida pelo serviço
        } catch (CursoInvalidoException | ErroSistemaException e) {
            throw e; // Re-lançar para ser capturado pelo try-catch do menu
        }
    }

    private void removerCurso(Scanner sc) throws CursoInvalidoException, ErroSistemaException {
        System.out.print("ID do Curso a ser removido: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.println(
                "ATENÇÃO: Remover um curso também removerá todos os alunos associados a ele devido à integridade referencial (ON DELETE CASCADE).");
        System.out.print("Deseja realmente remover o curso " + id + "? (s/n): ");
        String confirmacao = sc.nextLine().toLowerCase();

        if (confirmacao.equals("s")) {
            cursoService.removerCurso(id);
        } else {
            System.out.println("Operação de remoção cancelada.");
        }
    }

    private void desativarCurso(Scanner sc) throws CursoInvalidoException, ErroSistemaException {
        System.out.print("ID do Curso a ser desativado: ");
        int id = sc.nextInt();
        sc.nextLine();
        cursoService.desativarCurso(id);
    }

    private void ativarCurso(Scanner sc) throws CursoInvalidoException, ErroSistemaException {
        System.out.print("ID do Curso a ser ativado: ");
        int id = sc.nextInt();
        sc.nextLine();
        cursoService.ativarCurso(id);
    }

    private void verificarCursoAtivo(Scanner sc) throws ErroSistemaException {
        System.out.print("ID do Curso para verificar status: ");
        int id = sc.nextInt();
        sc.nextLine();

        Optional<Curso> cursoOpt = cursoService.obterCursoPorId(id);
        if (cursoOpt.isPresent()) {
            if (cursoOpt.get().isAtivo().isAtivo()) {
                System.out.println("O curso " + id + " está ATIVO.");
            } else {
                System.out.println("O curso " + id + " está INATIVO.");
            }
        } else {
            System.out.println("O curso com ID " + id + " não existe.");
        }

    }

    private void buscarIdCursoPorNome(Scanner sc) throws ErroSistemaException {
        System.out.print("Nome do Curso para buscar ID: ");
        String nome = sc.nextLine();
        OptionalInt idOpt = cursoService.obterIdCursoPorNome(nome);
        if (idOpt.isPresent()) {
            System.out.println("ID do curso '" + nome + "': " + idOpt.getAsInt());
        } else {
            System.out.println("Curso com o nome '" + nome + "' não encontrado.");
        }

    }

    private void obterLimiteAlunosCurso(Scanner sc) throws ErroSistemaException {
        System.out.print("ID do Curso para obter limite de alunos: ");
        int id = sc.nextInt();
        sc.nextLine();
        int limite = cursoService.obterLimiteAlunosCurso(id);
        System.out.println("Limite de alunos para o curso " + id + ": " + limite);
    }

    private void verificarCursoCheio(Scanner sc) throws CursoInvalidoException, ErroSistemaException {
        System.out.print("ID do Curso para verificar se está cheio: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (cursoService.cursoCheio(id)) {
            System.out.println("O curso " + id + " está CHEIO.");
        } else {
            System.out.println("O curso " + id + " tem vagas disponíveis.");
        }
    }
}
