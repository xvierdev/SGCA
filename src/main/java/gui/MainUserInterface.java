package gui;

import dao.AlunoDao;
import dao.CursoDao;
import exceptions.AlunoInvalidoException;
import exceptions.CursoInvalidoException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Aluno;
import model.Curso;
import model.enums.Status;
import services.AlunoService;
import services.CursoService;

public class MainUserInterface extends javax.swing.JFrame {

    private CursoService cursoService;
    private AlunoService alunoService;
    private CursoDao cursoDao;
    private AlunoDao alunoDao;
    private Curso selectedCurso;
    private Aluno selectedAluno;

    public MainUserInterface() {
        initComponents();
        try {
            this.cursoDao = new CursoDao();
            this.alunoDao = new AlunoDao();
            this.cursoService = new CursoService(cursoDao, alunoDao);
            this.alunoService = new AlunoService(alunoDao, cursoDao);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao acessar banco de dados: " + e.getMessage());
        }
        atualizaTabelaCursos();
        atualizaTabelaAlunos();
        cursoTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = cursoTable.getSelectedRow();
                if (row != -1) {
                    int id = Integer.parseInt(cursoTable.getValueAt(row, 0).toString());
                    selectedCurso = cursoService.obterCursoPorId(id).get();
                }
            }
        });
        alunoTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = alunoTable.getSelectedRow();
                if (row != -1) {
                    int id = Integer.parseInt(alunoTable.getValueAt(row, 0).toString());
                    selectedAluno = alunoService.obterAlunoPorId(id).get();
                }
            }
        });
    }

    private void initComponents() {

        btnNovoCurso = new javax.swing.JButton();
        btnNovoAluno = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        jTabbedPane = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        cursoTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        alunoTable = new javax.swing.JTable();
        checkAtivo = new javax.swing.JCheckBox();
        checkInativo = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SGCA by XvierDev");

        btnNovoCurso.setText("Novo Curso");
        btnNovoCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoCursoActionPerformed(evt);
            }
        });

        btnNovoAluno.setText("Novo Aluno");
        btnNovoAluno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoAlunoActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnExportar.setText("Exportar");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        cursoTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }));
        jScrollPane1.setViewportView(cursoTable);

        jTabbedPane.addTab("Cursos", jScrollPane1);

        alunoTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }));
        jScrollPane2.setViewportView(alunoTable);

        jTabbedPane.addTab("Alunos", jScrollPane2);

        checkAtivo.setSelected(true);
        checkAtivo.setText("Ativos");
        checkAtivo.setToolTipText("");
        checkAtivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkAtivoActionPerformed(evt);
            }
        });

        checkInativo.setText("Inativo");
        checkInativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkInativoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 709,
                                                        Short.MAX_VALUE)
                                                .addContainerGap())
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnNovoCurso)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnNovoAluno)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnEditar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnDelete)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnExportar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(checkAtivo)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(checkInativo)
                                                .addGap(0, 0, Short.MAX_VALUE)))));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnNovoAluno)
                                        .addComponent(btnNovoCurso)
                                        .addComponent(btnEditar)
                                        .addComponent(btnDelete)
                                        .addComponent(btnExportar)
                                        .addComponent(checkAtivo)
                                        .addComponent(checkInativo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                                .addContainerGap()));

        pack();
        setLocationRelativeTo(null);
    }

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {
        if ("Cursos".equals(jTabbedPane.getTitleAt(jTabbedPane.getSelectedIndex())) && selectedCurso != null) {
            EditCursoDialog editCursoDlg = new EditCursoDialog(this, alunoService, cursoService, selectedCurso);
            editCursoDlg.setVisible(true);
            jTabbedPane.setSelectedIndex(0);
            atualizaTabelaCursos();

        } else if ("Alunos".equals(jTabbedPane.getTitleAt(jTabbedPane.getSelectedIndex())) && selectedAluno != null) {
            EditAlunoDialog editAlunoDlg = new EditAlunoDialog(this, alunoService, cursoService, selectedAluno);
            editAlunoDlg.setVisible(true);
            jTabbedPane.setSelectedIndex(1);
            atualizaTabelaAlunos();
        }
    }

    private void checkAtivoActionPerformed(java.awt.event.ActionEvent evt) {
        atualizaTabelaCursos();
        atualizaTabelaAlunos();
    }

    private void checkInativoActionPerformed(java.awt.event.ActionEvent evt) {
        atualizaTabelaCursos();
        atualizaTabelaAlunos();
    }

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {
        ExportDialog exportDlg = new ExportDialog(this, cursoService, alunoService);
        exportDlg.setVisible(true);
    }

    private void btnNovoCursoActionPerformed(java.awt.event.ActionEvent evt) {
        NovoCursoDialog novoCursoDlg = new NovoCursoDialog(this, cursoService, alunoService);
        novoCursoDlg.setVisible(true);
        jTabbedPane.setSelectedIndex(0);
        atualizaTabelaCursos();
    }

    private void btnNovoAlunoActionPerformed(java.awt.event.ActionEvent evt) {
        NovoAlunoDialog novoAlunoDlg = new NovoAlunoDialog(this, cursoService, alunoService);
        novoAlunoDlg.setVisible(true);
        jTabbedPane.setSelectedIndex(1);
        atualizaTabelaAlunos();
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        if ("Cursos".equals(jTabbedPane.getTitleAt(jTabbedPane.getSelectedIndex())) && selectedCurso != null) {
            int option = JOptionPane.showConfirmDialog(null, "Excluir o curso " + selectedCurso.getNome() + "?");
            if (option == JOptionPane.YES_OPTION) {
                try {
                    cursoService.removerCurso(selectedCurso.getIdCurso());
                    atualizaTabelaCursos();
                    selectedCurso = null;
                } catch (CursoInvalidoException e) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir curso: " + e.getMessage());
                }
            }
        } else if ("Alunos".equals(jTabbedPane.getTitleAt(jTabbedPane.getSelectedIndex())) && selectedAluno != null) {
            int option = JOptionPane.showConfirmDialog(null, "Excluir o aluno " + selectedAluno.getNome() + "?");
            if (option == JOptionPane.YES_OPTION) {
                try {
                    alunoService.removerAluno(selectedAluno.getIdAluno());
                    atualizaTabelaAlunos();
                    selectedAluno = null;
                } catch (CursoInvalidoException e) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir aluno: " + e.getMessage());
                }
            }
        }
    }

    private Status isCheckAtivoSelected() {
        if (checkAtivo.isSelected() && checkInativo.isSelected()) {
            return null;
        } else if (checkAtivo.isSelected()) {
            return Status.ATIVO;
        } else if (checkInativo.isSelected()) {
            return Status.INATIVO;
        } else {
            return null;
        }
    }

    private void atualizaTabelaCursos() {
        try {
            List<Curso> cursos = cursoService.listarTodosCursos(isCheckAtivoSelected());
            DefaultTableModel model = new DefaultTableModel(
                    new String[] { "ID", "Nome", "Carga Horária", "Alunos no curso", "Limite Alunos", "Status" }, 0);
            for (Curso curso : cursos) {
                model.addRow(new Object[] { curso.getIdCurso(), curso.getNome(), curso.getCargaHoraria(),
                        cursoService.obterTotalAlunosNoCurso(curso.getIdCurso()), curso.getLimiteAlunos(),
                        curso.isAtivo() });
            }
            cursoTable.setModel(model);
        } catch (CursoInvalidoException e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void atualizaTabelaAlunos() {
        try {
            List<Aluno> alunos = alunoService.listarAlunos(isCheckAtivoSelected());
            DefaultTableModel model = new DefaultTableModel(new String[] { "ID Aluno", "Curso", "Nome", "CPF",
                    "Telefone", "Email", "Data de Nascimento", "Status" }, 0);
            for (Aluno aluno : alunos) {
                String cursoNome = "";
                java.util.Optional<Curso> cursoOpt = cursoService.obterCursoPorId(aluno.getIdCurso());
                if (cursoOpt.isPresent()) {
                    cursoNome = cursoOpt.get().getNome();
                } else {
                    cursoNome = "Curso não encontrado";
                }
                model.addRow(new Object[] { aluno.getIdAluno(), cursoNome, aluno.getNome(), aluno.getCpf(),
                        aluno.getTelefone(), aluno.getEmail(), aluno.getDataNascimento(), aluno.isAtivo() });
            }
            alunoTable.setModel(model);
        } catch (AlunoInvalidoException e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainUserInterface.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainUserInterface.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainUserInterface.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainUserInterface.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainUserInterface().setVisible(true);
            }
        });
    }

    private javax.swing.JTable alunoTable;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnNovoAluno;
    private javax.swing.JButton btnNovoCurso;
    private javax.swing.JCheckBox checkAtivo;
    private javax.swing.JCheckBox checkInativo;
    private javax.swing.JTable cursoTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane;
}
