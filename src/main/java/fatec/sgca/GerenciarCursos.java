package fatec.sgca; // Assumindo que GerenciarCursos está no mesmo pacote de Teste

import dao.CursoDao;
import model.Curso;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class GerenciarCursos {

    private static final String ERRO_VALIDACAO = "Erro de validação: ";
    private CursoDao cursoDao;

    public GerenciarCursos() throws SQLException { // Construtor pode lançar SQLException
        this.cursoDao = new CursoDao();
    }

    public void menuGerenciarCursos(Scanner sc) {
        while (true) {
            System.out.println("\n=== Gerenciamento de Cursos ===");
            System.out.println("1. Adicionar Curso");
            System.out.println("2. Buscar Curso por ID");
            System.out.println("3. Buscar Todos os Cursos");
            System.out.println("4. Atualizar Curso");
            System.out.println("5. Remover Curso");
            System.out.println("6. Desativar Curso");
            System.out.println("7. Ativar Curso");
            System.out.println("8. Verificar se Curso Está Ativo");
            System.out.println("9. Buscar ID de Curso por Nome");
            System.out.println("10. Obter Limite de Alunos do Curso");
            System.out.println("11. Obter Total de Alunos no Curso");
            System.out.println("12. Verificar se Curso Está Cheio");
            System.out.println("13. Voltar ao Menu Principal");
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
                    case 1: adicionarCurso(sc); break;
                    case 2: buscarCursoPorId(sc); break;
                    case 3: buscarTodosCursos(); break;
                    case 4: atualizarCurso(sc); break;
                    case 5: removerCurso(sc); break;
                    case 6: desativarCurso(sc); break;
                    case 7: ativarCurso(sc); break;
                    case 8: verificarCursoAtivo(sc); break;
                    case 9: buscarIdCursoPorNome(sc); break;
                    case 10: obterLimiteAlunosCurso(sc); break;
                    case 11: obterTotalAlunosNoCurso(sc); break;
                    case 12: verificarCursoCheio(sc); break;
                    case 13: return; // Volta ao menu principal
                    default: System.out.println("Opção inválida.");
            } catch (SQLException e) {
                System.err.println("Erro de banco de dados: " + e.getMessage());
                e.printStackTrace();
            } catch (IllegalArgumentException e) { // Captura exceções de validação do modelo
                System.err.println(ERRO_VALIDACAO + e.getMessage());
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
            }
        }
    }

    private void adicionarCurso(Scanner sc) throws SQLException {
        System.out.print("Nome do Curso: ");
        String nome = sc.nextLine();
        System.out.print("Carga Horária: ");
        int cargaHoraria = sc.nextInt();
        System.out.print("Limite de Alunos: ");
        int limiteAlunos = sc.nextInt();
        sc.nextLine(); // Consumir nova linha
        try {
            // A classe Curso valida os dados ao ser instanciada
            Curso novoCurso = new Curso(0, nome, cargaHoraria, limiteAlunos, true); // ID será gerado pelo banco
            Curso cursoSalvo = cursoDao.adicionar(novoCurso);
            if (cursoSalvo != null) {
                System.out.println("Curso adicionado com sucesso! ID: " + cursoSalvo.getIdCurso());
            } else {
                System.out.println("Falha ao adicionar curso.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(ERRO_VALIDACAO + e.getMessage());
        }
        }
    }

    private void buscarCursoPorId(Scanner sc) throws SQLException {
        System.out.print("ID do Curso: ");
        int id = sc.nextInt();
        sc.nextLine();

        Curso curso = cursoDao.buscarPorId(id);
        if (curso != null) {
            System.out.println("--- Dados do Curso ---");
            System.out.println(curso); // Assume que Curso tem um bom toString()
        } else {
            System.out.println("Curso não encontrado.");
        }
    }

    private void buscarTodosCursos() throws SQLException {
        List<Curso> cursos = cursoDao.buscarTodos();
        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso cadastrado.");
        } else {
            System.out.println("\n--- Todos os Cursos ---");
            for (Curso curso : cursos) {
                System.out.println(curso); // Imprime cada curso
            }
        }
    }

    private void atualizarCurso(Scanner sc) throws SQLException {
        System.out.print("ID do Curso a ser atualizado: ");
        int id = sc.nextInt();
        sc.nextLine();

        Curso cursoExistente = cursoDao.buscarPorId(id);
        if (cursoExistente == null) {
            System.out.println("Curso não encontrado.");
            return;
        }

        System.out.println("Informações atuais: " + cursoExistente);
        System.out.print("Novo Nome (deixe em branco para manter '" + cursoExistente.getNome() + "'): ");
        String novoNome = sc.nextLine();
        if (!novoNome.isEmpty()) {
            cursoExistente.setNome(novoNome);
        }

        System.out.print("Nova Carga Horária (0 para manter " + cursoExistente.getCargaHoraria() + "): ");
        int novaCargaHoraria = sc.nextInt();
        if (novaCargaHoraria != 0) {
            cursoExistente.setCargaHoraria(novaCargaHoraria);
        }
        
        System.out.print("Novo Limite de Alunos (0 para manter " + cursoExistente.getLimiteAlunos() + "): ");
        int novoLimiteAlunos = sc.nextInt();
        if (novoLimiteAlunos != 0) {
            cursoExistente.setLimiteAlunos(novoLimiteAlunos);
        }
        sc.nextLine(); // Consumir nova linha

        System.out.print("Curso Ativo? (s/n, atual: " + (cursoExistente.isAtivo() ? "sim" : "não") + "): ");
        String ativoStr = sc.nextLine().toLowerCase();
        if (ativoStr.equals("s")) {
            cursoExistente.setAtivo(true);
        } else if (ativoStr.equals("n")) {
            cursoExistente.setAtivo(false);
        }
        try {
            if (cursoDao.atualizar(cursoExistente)) {
                System.out.println("Curso atualizado com sucesso!");
            } else {
                System.out.println("Falha ao atualizar curso.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(ERRO_VALIDACAO + e.getMessage());
        }
        }
    }

    private void removerCurso(Scanner sc) throws SQLException {
        System.out.print("ID do Curso a ser removido: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.println("ATENÇÃO: Remover um curso também removerá todos os alunos associados a ele devido à integridade referencial (ON DELETE CASCADE).");
        System.out.print("Deseja realmente remover o curso " + id + "? (s/n): ");
        String confirmacao = sc.nextLine().toLowerCase();

        if (confirmacao.equals("s")) {
            if (cursoDao.remover(id)) {
                System.out.println("Curso removido com sucesso!");
            } else {
                System.out.println("Falha ao remover curso (ID não encontrado ou erro no banco).");
            }
        } else {
            System.out.println("Operação de remoção cancelada.");
        }
    }

    private void desativarCurso(Scanner sc) throws SQLException {
        System.out.print("ID do Curso a ser desativado: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (cursoDao.desativar(id)) {
            System.out.println("Curso desativado com sucesso!");
        } else {
            System.out.println("Falha ao desativar curso (ID não encontrado ou já inativo).");
        }
    }

    private void ativarCurso(Scanner sc) throws SQLException {
        System.out.print("ID do Curso a ser ativado: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (cursoDao.ativar(id)) {
            System.out.println("Curso ativado com sucesso!");
        } else {
            System.out.println("Falha ao ativar curso (ID não encontrado ou já ativo).");
        }
    }

    private void verificarCursoAtivo(Scanner sc) throws SQLException {
        System.out.print("ID do Curso para verificar status: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (cursoDao.estaAtivo(id)) {
            System.out.println("O curso " + id + " está ATIVO.");
        } else {
            System.out.println("O curso " + id + " está INATIVO ou não existe.");
        }
    }

    private void buscarIdCursoPorNome(Scanner sc) throws SQLException {
        System.out.print("Nome do Curso para buscar ID: ");
        String nome = sc.nextLine();

        int id = cursoDao.buscarIdPorNome(nome);
        if (id != -1) {
            System.out.println("ID do curso '" + nome + "': " + id);
        } else {
            System.out.println("Curso com o nome '" + nome + "' não encontrado.");
        }
    }

    private void obterLimiteAlunosCurso(Scanner sc) throws SQLException {
        System.out.print("ID do Curso para obter limite de alunos: ");
        int id = sc.nextInt();
        sc.nextLine();

        int limite = cursoDao.getLimiteAlunos(id);
        if (limite != -1) {
            System.out.println("Limite de alunos para o curso " + id + ": " + limite);
        } else {
            System.out.println("Curso " + id + " não encontrado.");
        }
    }

    private void obterTotalAlunosNoCurso(Scanner sc) throws SQLException {
        System.out.print("ID do Curso para obter total de alunos: ");
        int id = sc.nextInt();
        sc.nextLine();

        int total = cursoDao.getTotalAlunosNoCurso(id);
        System.out.println("Total de alunos matriculados no curso " + id + ": " + total);
    }

    private void verificarCursoCheio(Scanner sc) throws SQLException {
        System.out.print("ID do Curso para verificar se está cheio: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (cursoDao.cursoCheio(id)) {
            System.out.println("O curso " + id + " está CHEIO.");
        } else {
            System.out.println("O curso " + id + " tem vagas disponíveis.");
        }
    }
}