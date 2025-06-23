package cli;

import java.sql.SQLException;
import java.util.Scanner;

import dao.AlunoDao;
import dao.CursoDao;
import services.AlunoService;
import services.CursoService;

public class Teste {

    public static void main(String[] args) {
        try {
            try (Scanner sc = new Scanner(System.in)) {
                boolean running = true;
                AlunoDao alunoDao = new AlunoDao();
                CursoDao cursoDao = new CursoDao();
                AlunoService as = new AlunoService(alunoDao, cursoDao);
                CursoService cs = new CursoService(cursoDao, alunoDao);
                while (running) {
                    LimpaConsole.Limpar();
                    System.out.println("\n=== Sistema de Gestão de Cursos e Alunos ===");
                    System.out.println("1. Gerenciar Cursos");
                    System.out.println("2. Gerenciar Alunos");
                    System.out.println("3. Relatórios e Listagens");
                    System.out.println("0. Sair");
                    System.out.print("\nEscolha uma opção: ");
                    // Lidar com a exceção de entrada de forma mais robusta
                    if (!sc.hasNextInt()) {
                        System.out.println("Opção inválida. Por favor, digite um número.");
                        sc.next(); // Consome a entrada inválida para evitar loop infinito
                    } else {
                        int opcao = sc.nextInt();
                        sc.nextLine();
                        LimpaConsole.Limpar();

                        try {
                            switch (opcao) {
                                case 0 -> {
                                    System.out.println("Saindo do sistema. Até mais!");
                                    running = false;
                                }
                                case 1 -> {
                                    GerenciarCursos gerenciarCursos = new GerenciarCursos(cs);
                                    gerenciarCursos.menuGerenciarCursos(sc);
                                }
                                case 2 -> {
                                    GerenciarAlunos gerenciarAlunos = new GerenciarAlunos(as, cs);
                                    gerenciarAlunos.menuGerenciarAlunos(sc);
                                }
                                case 3 -> {
                                    Relatorios relatorios = new Relatorios(cs, as);
                                    relatorios.menuRelatorios(sc);
                                }
                                default -> System.out.println("Opção inválida. Por favor, tente novamente.");
                            }
                        } catch (IllegalStateException e) {
                            System.err.println("Erro de regra de negócio: " + e.getMessage());
                        } catch (Exception e) { // Capturar outras exceções inesperadas
                            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro no banco de dados: " + e.getMessage());
        }
    }
}