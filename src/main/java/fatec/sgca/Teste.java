package fatec.sgca;

import java.sql.SQLException;
import java.util.Scanner;

public class Teste {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\n=== Sistema de Gestão de Cursos e Alunos ===");
            System.out.println("1. Gerenciar Cursos");
            System.out.println("2. Gerenciar Alunos");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");

            // Lidar com a exceção de entrada de forma mais robusta
            if (!sc.hasNextInt()) {
                System.out.println("Opção inválida. Por favor, digite um número.");
                sc.next(); // Consome a entrada inválida para evitar loop infinito
            } else {
                int opcao = sc.nextInt();
                sc.nextLine(); // Consumir a nova linha pendente

                try {
                    if (opcao == 3) {
                        System.out.println("Saindo do sistema. Até mais!");
                        running = false;
                    } else if (opcao == 1) {
                        GerenciarCursos gerenciarCursos = new GerenciarCursos();
                        gerenciarCursos.menuGerenciarCursos(sc);
                    } else if (opcao == 2) {
                        GerenciarAlunos gerenciarAlunos = new GerenciarAlunos();
                        gerenciarAlunos.menuGerenciarAlunos(sc);
                    } else {
                        System.out.println("Opção inválida. Por favor, tente novamente.");
                    }
                } catch (SQLException e) {
                    System.err.println("Erro de banco de dados: " + e.getMessage());
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    System.err.println("Erro de regra de negócio: " + e.getMessage());
                } catch (Exception e) { // Capturar outras exceções inesperadas
                    System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        sc.close();
    }
}