package cli;

import java.util.List;
import java.util.Scanner;

import exceptions.CursoInvalidoException;
import exceptions.ErroSistemaException;
import model.Aluno;
import model.Curso;
import model.enums.Status;
import services.AlunoService;
import services.CursoService;

public class Relatorios {

    private final CursoService cursoService;
    private final AlunoService alunoService;

    public Relatorios(CursoService cursoService, AlunoService alunoService) {
        this.cursoService = cursoService;
        this.alunoService = alunoService;
    }

    public void menuRelatorios(Scanner sc) {
        while (true) {
            System.out.println("\n=== Relatórios ===");
            System.out.println("1. Listar alunos em um curso");
            System.out.println("2. listar todos os cursos");
            System.out.println("3. listar todos os cursos ativos");
            System.out.println("4. listar todos os cursos e alunos");
            System.out.println("5. Listar todos os alunos.");
            System.out.println("\n=== Exportar ===");
            System.out.println("6. Listar alunos ativos");
            System.out.println("7. Alunos ativos por curso");
            System.out.println("8. Alunos inativos por curso");
            System.out.println("9. Voltar ao Menu Principal");
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
                    case 1 -> listarAlunosPorCurso(sc, null);
                    case 2 -> listarTodosCursos();
                    case 3 -> listarCursosAtivos();
                    case 4 -> listarTodosAlunosECursos(null);
                    case 5 -> listarTodosAlunos();
                    case 6 -> listarAlunosAtivos();
                    case 7 -> listarAlunosPorCurso(sc, Status.ATIVO);
                    case 8 -> listarAlunosPorCurso(sc, Status.INATIVO);
                    case 9 -> {
                        return; // Volta ao menu principal
                    }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (CursoInvalidoException e) {
                System.err.println("Erro de validação do Curso: " + e.getMessage());
            } catch (ErroSistemaException e) {
                System.err.println("Erro interno do sistema: " + e.getMessage());
                // Opcional: e.printStackTrace(); para depuração mais detalhada
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
            }
        }
    }

    private void listarAlunosPorCurso(Scanner sc, Status filterStatus) throws ErroSistemaException {
        System.out.print("Digite o ID do curso: ");
        int id = sc.nextInt();
        sc.nextLine();
        List<Aluno> listAlunos = cursoService.listarAlunosPorCurso(id, filterStatus);
        if (listAlunos.isEmpty()) {
            System.out.println("Não há alunos neste curso.");
        } else {
            for (Aluno aluno : listAlunos) {
                System.out.println(aluno);
            }
        }
    }

    private void listarTodosCursos() throws ErroSistemaException {
        List<Curso> cursos = cursoService.listarTodosCursos(null);
        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso cadastrado.");
        } else {
            System.out.println("\n--- Todos os Cursos ---");
            for (Curso curso : cursos) {
                System.out.println(curso);
            }
        }
    }

    private void listarTodosAlunosECursos(Status filterStatus) throws ErroSistemaException {
        List<Curso> cursos = cursoService.listarTodosCursos(null);
        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso cadastrado.");
        } else {
            for (Curso curso : cursos) {
                System.out.println("Curso: " + curso.getNome());
                List<Aluno> lAlunos = cursoService.listarAlunosPorCurso(curso.getIdCurso(), filterStatus);
                if (lAlunos.isEmpty()) {
                    System.out.println("Não há alunos matriculados.");
                }
                for (Aluno aluno : lAlunos) {
                    System.out.println("Aluno ativo: " + aluno);
                }
            }
        }
    }

    private void listarTodosAlunos() throws ErroSistemaException {
        List<Aluno> alunos = alunoService.listarTodosAlunos();
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            System.out.println("\n--- Todos os Alunos ---");
            for (Aluno aluno : alunos) {
                System.out.println(aluno);
            }
        }
    }

    private void listarCursosAtivos() {
        List<Curso> cursosAtivos = cursoService.listarTodosCursos(Status.ATIVO);
        if (cursosAtivos.isEmpty()) {
            System.out.println("Nenhum curso ativo.");
        } else {
            System.out.println("\n--- Cursos Ativos ---");
            for (Curso curso : cursosAtivos) {
                System.out.println(curso);
            }
        }
    }

    private void listarAlunosAtivos() throws ErroSistemaException {
        List<Aluno> alunos = alunoService.listarTodosAlunos();
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            System.out.println("\n--- Todos os Alunos ---");
            for (Aluno aluno : alunos) {
                System.out.println(aluno);
            }
        }
    }

}
