package services;

import dao.AlunoDao;
import dao.CursoDao;
import exceptions.CursoInvalidoException;
import exceptions.ErroSistemaException;
import model.Aluno;
import model.Curso;
import model.enums.Status;
import utils.FileSaver;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * Classe de serviço para gerenciar as operações de negócio relacionadas a
 * Cursos. Esta camada é responsável por validações de dados, regras de negócio
 * e coordenação entre as camadas de apresentação e persistência (DAO).
 */
public class CursoService {

    private final CursoDao cursoDao;
    private final AlunoDao alunoDao;

    /**
     * Construtor do CursoService, injetando as dependências do CursoDao e
     * AlunoDao.
     *
     * @param cursoDao A instância de CursoDao a ser utilizada para operações de
     *                 persistência de cursos.
     * @param alunoDao A instância de AlunoDao a ser utilizada para operações
     *                 relacionadas a alunos (ex: listar alunos de um curso).
     */
    public CursoService(CursoDao cursoDao, AlunoDao alunoDao) {
        this.cursoDao = cursoDao;
        this.alunoDao = alunoDao;
    }

    // --- Métodos de Validação ---
    /**
     * Valida o nome do curso.
     *
     * @param nome O nome a ser validado.
     * @throws CursoInvalidoException Se o nome for nulo, vazio ou tiver menos
     *                                de 3 caracteres.
     */
    private void validarNome(String nome) {
        if (nome == null || nome.trim().length() < 3) {
            throw new CursoInvalidoException("Nome do curso deve ter no mínimo 3 caracteres.");
        }
    }

    /**
     * Valida a carga horária do curso.
     *
     * @param cargaHoraria A carga horária a ser validada.
     * @throws CursoInvalidoException Se a carga horária for menor que 20 horas.
     */
    private void validarCargaHoraria(int cargaHoraria) {
        if (cargaHoraria < 20) {
            throw new CursoInvalidoException("Carga Horária do curso deve ser de no mínimo 20 horas.");
        }
    }

    /**
     * Valida o limite de alunos do curso.
     *
     * @param limiteAlunos O limite de alunos a ser validado.
     * @throws CursoInvalidoException Se o limite de alunos for menor que 1.
     */
    private void validarLimiteAlunos(int limiteAlunos) {
        if (limiteAlunos < 1) {
            throw new CursoInvalidoException("Limite de Alunos do curso deve ser de no mínimo 1.");
        }
    }

    // --- Métodos de Negócio (CRUD e Outros) ---
    /**
     * Adiciona um novo curso ao sistema, após validar os dados de entrada.
     *
     * @param nome         O nome do curso.
     * @param cargaHoraria A carga horária do curso.
     * @param limiteAlunos O limite de alunos do curso.
     * @throws CursoInvalidoException Se alguma validação de dados do curso
     *                                falhar.
     * @throws ErroSistemaException   Se ocorrer um erro de persistência no banco
     *                                de dados.
     */
    public void adicionarCurso(String nome, int cargaHoraria, int limiteAlunos) {
        List<String> erros = new ArrayList<>();
        try {
            validarNome(nome);
        } catch (CursoInvalidoException e) {
            erros.add(e.getMessage());
        }
        try {
            validarCargaHoraria(cargaHoraria);
        } catch (CursoInvalidoException e) {
            erros.add(e.getMessage());
        }
        try {
            validarLimiteAlunos(limiteAlunos);
        } catch (CursoInvalidoException e) {
            erros.add(e.getMessage());
        }

        if (!erros.isEmpty()) {
            throw new CursoInvalidoException(String.join("\n", erros));
        }

        Curso novoCurso = new Curso(nome, cargaHoraria, limiteAlunos);
        try {
            cursoDao.adicionar(novoCurso);
            System.out.println("Curso '" + nome + "' adicionado com sucesso!");
        } catch (SQLException e) {
            throw new ErroSistemaException("Falha ao cadastrar o curso devido a um erro no banco de dados.", e);
        }
    }

    /**
     * Atualiza as informações de um curso existente.
     *
     * @param idCurso      O ID do curso a ser atualizado.
     * @param nome         O novo nome do curso.
     * @param cargaHoraria A nova carga horária do curso.
     * @param limiteAlunos O novo limite de alunos do curso.
     * @param status
     * @throws CursoInvalidoException Se o curso não for encontrado ou se alguma
     *                                validação de dados falhar.
     * @throws ErroSistemaException   Se ocorrer um erro de persistência no banco
     *                                de dados.
     */
    public void atualizarCurso(int idCurso, String nome, int cargaHoraria, int limiteAlunos, Status status) {
        Optional<Curso> cursoExistenteOpt;
        try {
            cursoExistenteOpt = cursoDao.buscarPorId(idCurso);
        } catch (SQLException e) {
            throw new ErroSistemaException("Ocorreu um erro no banco de dados ao buscar o curso para atualização.", e);
        }

        if (cursoExistenteOpt.isEmpty()) {
            throw new CursoInvalidoException("Curso com ID " + idCurso + " não encontrado para atualização.");
        }

        List<String> erros = new ArrayList<>();
        try {
            validarNome(nome);
        } catch (CursoInvalidoException e) {
            erros.add(e.getMessage());
        }
        try {
            validarCargaHoraria(cargaHoraria);
        } catch (CursoInvalidoException e) {
            erros.add(e.getMessage());
        }
        try {
            validarLimiteAlunos(limiteAlunos);
        } catch (CursoInvalidoException e) {
            erros.add(e.getMessage());
        }

        if (!erros.isEmpty()) {
            throw new CursoInvalidoException(String.join("\n", erros));
        }

        // Atualiza o objeto Curso existente com os novos dados
        Curso cursoParaAtualizar = cursoExistenteOpt.get();
        cursoParaAtualizar.setNome(nome);
        cursoParaAtualizar.setCargaHoraria(cargaHoraria);
        cursoParaAtualizar.setLimiteAlunos(limiteAlunos);
        cursoParaAtualizar.setAtivo(status);

        try {
            boolean atualizado = cursoDao.atualizar(cursoParaAtualizar);
            if (!atualizado) {
                throw new ErroSistemaException("Não foi possível atualizar o curso no banco de dados. Verifique o ID.");
            }
            System.out.println("Curso '" + nome + "' atualizado com sucesso!");
        } catch (SQLException e) {
            throw new ErroSistemaException("Falha ao atualizar o curso devido a um erro no banco de dados.", e);
        }
    }

    /**
     * Remove um curso do sistema pelo seu ID.
     *
     * @param idCurso O ID do curso a ser removido.
     * @throws CursoInvalidoException Se o curso não for encontrado para
     *                                remoção.
     * @throws ErroSistemaException   Se ocorrer um erro de persistência no banco
     *                                de dados.
     */
    public void removerCurso(int idCurso) {
        try {
            // Primeiro, verifica se o curso existe antes de tentar remover
            Optional<Curso> cursoExistente = cursoDao.buscarPorId(idCurso);
            if (cursoExistente.isEmpty()) {
                throw new CursoInvalidoException("Curso com ID " + idCurso + " não encontrado para remoção.");
            }

            boolean removido = cursoDao.remover(idCurso);
            if (!removido) {
                throw new CursoInvalidoException("Não foi possível remover o curso no banco de dados. Verifique o ID.");
            }
            System.out.println("Curso com ID " + idCurso + " removido com sucesso!");
        } catch (SQLException e) {
            throw new ErroSistemaException("Falha ao remover o curso devido a um erro no banco de dados.", e);
        }
    }

    /**
     * Desativa um curso pelo seu ID.
     *
     * @param idCurso O ID do curso a ser desativado.
     * @throws CursoInvalidoException Se o curso não for encontrado ou já
     *                                estiver inativo.
     * @throws ErroSistemaException   Se ocorrer um erro de persistência no banco
     *                                de dados.
     */
    public void desativarCurso(int idCurso) {
        Optional<Curso> cursoExistenteOpt;
        try {
            cursoExistenteOpt = cursoDao.buscarPorId(idCurso);
        } catch (SQLException e) {
            throw new ErroSistemaException("Erro ao buscar o curso para desativação no banco de dados.", e);
        }

        if (cursoExistenteOpt.isEmpty()) {
            throw new CursoInvalidoException("Curso com ID " + idCurso + " não encontrado.");
        }

        if (cursoExistenteOpt.get().isAtivo().equals(Status.INATIVO)) {
            throw new CursoInvalidoException("Curso com ID " + idCurso + " já está inativo.");
        }

        try {
            boolean desativado = cursoDao.desativar(idCurso);
            if (!desativado) {
                throw new ErroSistemaException("Não foi possível desativar o curso no banco de dados.");
            }
            System.out.println("Curso com ID " + idCurso + " desativado com sucesso!");
        } catch (SQLException e) {
            throw new ErroSistemaException("Falha ao desativar o curso devido a um erro no banco de dados.", e);
        }
    }

    /**
     * Ativa um curso pelo seu ID.
     *
     * @param idCurso O ID do curso a ser ativado.
     * @throws CursoInvalidoException Se o curso não for encontrado ou já
     *                                estiver ativo.
     * @throws ErroSistemaException   Se ocorrer um erro de persistência no banco
     *                                de dados.
     */
    public void ativarCurso(int idCurso) {
        Optional<Curso> cursoExistenteOpt;
        try {
            cursoExistenteOpt = cursoDao.buscarPorId(idCurso);
        } catch (SQLException e) {
            throw new ErroSistemaException("Erro ao buscar o curso para ativação no banco de dados.", e);
        }

        if (cursoExistenteOpt.isEmpty()) {
            throw new CursoInvalidoException("Curso com ID " + idCurso + " não encontrado.");
        }

        if (cursoExistenteOpt.get().isAtivo().isAtivo()) {
            throw new CursoInvalidoException("Curso com ID " + idCurso + " já está ativo.");
        }

        try {
            boolean ativado = cursoDao.ativar(idCurso);
            if (!ativado) {
                throw new ErroSistemaException("Não foi possível ativar o curso no banco de dados.");
            }
            System.out.println("Curso com ID " + idCurso + " ativado com sucesso!");
        } catch (SQLException e) {
            throw new ErroSistemaException("Falha ao ativar o curso devido a um erro no banco de dados.", e);
        }
    }

    /**
     * Verifica se o limite de alunos já foi atingido para um determinado curso.
     *
     * @param id O ID do curso a ser verificado.
     * @return true caso o limite de alunos tenha sido alcançado, false no caso
     *         contrário.
     * @throws CursoInvalidoException Se ocorrer um erro ao consultar o banco de
     *                                dados para verificar o curso.
     */
    public boolean cursoCheio(int id) {
        try {
            return cursoDao.cursoCheio(id);
        } catch (SQLException e) {
            throw new CursoInvalidoException("Falha ao verificar curso devido a um erro no banco de dados.", e);
        }
    }

    public int obterTotalAlunosNoCurso(int id) {
        try {
            return cursoDao.getTotalAlunosNoCurso(id);
        } catch (SQLException e) {
            throw new ErroSistemaException("Falha ao obter total de alunos devido a um erro no banco de dados.", e);
        }
    }

    public int obterLimiteAlunosCurso(int id) {
        try {
            return cursoDao.getLimiteAlunos(id).getAsInt();
        } catch (SQLException e) {
            throw new ErroSistemaException("Falha ao obter limite de alunos devido a um erro no banco de dados.", e);
        }
    }

    public OptionalInt obterIdCursoPorNome(String nome) {
        try {
            return cursoDao.buscarIdPorNome(nome);
        } catch (SQLException e) {
            throw new ErroSistemaException("Falha ao consultar nome no banco de dados.", e);
        }
    }

    /**
     * Obtém um curso pelo seu ID.
     *
     * @param id O ID do curso a ser obtido.
     * @return Um Optional contendo o objeto Curso, ou um Optional vazio se não
     *         encontrado.
     * @throws ErroSistemaException Se ocorrer um erro de persistência no banco
     *                              de dados.
     */
    public Optional<Curso> obterCursoPorId(int id) {
        try {
            return cursoDao.buscarPorId(id);
        } catch (SQLException e) {
            throw new ErroSistemaException("Falha ao obter curso devido a um erro no banco de dados.", e);
        }
    }

    /**
     * Lista todos os cursos cadastrados no sistema.
     *
     * @param filterStatus
     * @return Uma lista de objetos Curso, vazia se não houver cursos.
     * @throws ErroSistemaException Se ocorrer um erro de persistência no banco
     *                              de dados.
     */
    public List<Curso> listarTodosCursos(Status filterStatus) {
        try {
            if (filterStatus == null) {
                return cursoDao.buscarTodos();
            } else {
                return cursoDao.buscarTodosFiltrado(filterStatus);
            }
        } catch (SQLException e) {
            throw new ErroSistemaException("Falha ao obter a lista de cursos devido a um erro no banco de dados.", e);
        }
    }

    /**
     * Lista todos os alunos matriculados em um curso específico.
     *
     * @param idCurso      O ID do curso para listar os alunos.
     * @param filterStatus
     * @return Uma lista de objetos Aluno, vazia se não houver alunos nesse
     *         curso.
     * @throws CursoInvalidoException Se o curso não for encontrado.
     * @throws ErroSistemaException   Se ocorrer um erro de persistência no banco
     *                                de dados.
     */
    public List<Aluno> listarAlunosPorCurso(int idCurso, Status filterStatus) {
        try {
            // Verifica se o curso existe antes de tentar listar seus alunos
            if (cursoDao.buscarPorId(idCurso).isEmpty()) {
                throw new CursoInvalidoException("Curso com ID " + idCurso + " não encontrado.");
            }
            if (filterStatus == null) {
                return alunoDao.buscarAlunosPorCurso(idCurso);
            } else {
                return alunoDao.buscarAlunosPorCurso(idCurso, filterStatus);
            }
        } catch (SQLException e) {
            throw new ErroSistemaException(
                    "Falha ao obter a lista de alunos para o curso devido a um erro no banco de dados.", e);
        }
    }

    // ... outros métodos do serviço ...
    /**
     * Exporta dados de cursos e seus alunos para um arquivo de texto. Cada
     * linha representa um aluno, com informações do curso ao qual pertence.
     *
     * @param caminhoArquivo O caminho onde o arquivo será salvo.
     * @param cursos         A lista de cursos a serem exportados.
     * @throws IOException Se houver um erro de I/O ao salvar o arquivo.
     */
    public void exportarCursosComAlunos(String caminhoArquivo, List<Curso> cursos) throws IOException {
        List<String> linhasParaSalvar = new ArrayList<>();

        linhasParaSalvar.add(
                "ID_Curso;Nome_Curso;Id_Aluno;Nome_Aluno;Cpf_Aluno;Telefone_Aluno;Email_Aluno;Data_Nascimento_Aluno");

        for (Curso curso : cursos) {
            if (curso.getAlunos() != null && !curso.getAlunos().isEmpty()) {
                for (Aluno aluno : curso.getAlunos()) {
                    // CSV
                    String linha = String.format("%d;%s;%d;%s;%s;%s;%s;%s",
                            curso.getIdCurso(),
                            curso.getNome(),
                            aluno.getIdAluno(),
                            aluno.getNome(),
                            aluno.getCpf(),
                            aluno.getTelefone(),
                            aluno.getEmail(),
                            aluno.getDataNascimento());
                    linhasParaSalvar.add(linha);
                }
            } else {
                // Curso sem alunos
                linhasParaSalvar.add(String.format("%d;%s;N/A;N/A", curso.getIdCurso(), curso.getNome()));
            }
        }

        // Usa o FileSaver genérico para salvar a lista de strings formatadas
        FileSaver.saveListToFile(caminhoArquivo, linhasParaSalvar);
        System.out.println("Cursos e alunos exportados com sucesso para: " + caminhoArquivo);
    }

    public Curso obterCursoPorNome(String nomeCurso) {
        try {
            OptionalInt id = cursoDao.buscarIdPorNome(nomeCurso);
            if (id.isPresent()) {
                return cursoDao.buscarPorId(id.getAsInt())
                        .orElseThrow(() -> new CursoInvalidoException("Curso não encontrado."));
            }
            throw new CursoInvalidoException("Curso com nome '" + nomeCurso + "' não encontrado.");
        } catch (SQLException e) {
            throw new ErroSistemaException("Erro ao buscar curso por nome no banco de dados.", e);
        }

    }
}
