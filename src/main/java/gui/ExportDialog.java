package gui;

import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.OptionalInt;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import exceptions.CursoInvalidoException;
import model.Aluno;
import model.Curso;
import model.enums.Status;
import services.AlunoService;
import services.CursoService;

public class ExportDialog extends javax.swing.JDialog {

    private CursoService cursoService;
    private AlunoService alunoService;

    public ExportDialog(JFrame owner, CursoService cursoService, AlunoService alunoService) {
        super(owner, true);
        initComponents();
        this.cursoService = cursoService;
        this.alunoService = alunoService;
        List<Curso> listaDeCursos = cursoService.listarTodosCursos(Status.ATIVO);
        List<String> nomesDosCursos = listaDeCursos.stream()
                .map(Curso::getNome)
                .toList();
        cbxCurso.setModel(new DefaultComboBoxModel<>(nomesDosCursos.toArray(String[]::new)));
    }

    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        bntExportar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        cbxCurso = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        rdTodos = new javax.swing.JRadioButton();
        rdInativos = new javax.swing.JRadioButton();
        rdAtivos = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setType(java.awt.Window.Type.POPUP);

        bntExportar.setText("Exportar");
        bntExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntExportarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        cbxCurso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Curso");

        buttonGroup1.add(rdTodos);
        rdTodos.setSelected(true);
        rdTodos.setText("Ambos");

        buttonGroup1.add(rdInativos);
        rdInativos.setText("Alunos inativos");

        buttonGroup1.add(rdAtivos);
        rdAtivos.setText("Alunos ativos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdAtivos)
                    .addComponent(rdInativos)
                    .addComponent(rdTodos))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {rdAtivos, rdInativos, rdTodos});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdAtivos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdInativos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdTodos)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(64, 64, 64)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bntExportar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addGap(96, 96, 96))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbxCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bntExportar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private Status getSelectedStatus() {
        if (rdAtivos.isSelected()) {
            return Status.ATIVO;
        } else if (rdInativos.isSelected()) {
            return Status.INATIVO;
        } else {
            return null;
        }
    }

    private void bntExportarActionPerformed(java.awt.event.ActionEvent evt) {
        String nomeCurso = cbxCurso.getSelectedItem().toString();
        OptionalInt id = cursoService.obterIdCursoPorNome(nomeCurso);
        try {
            if (id.isEmpty()) {
                throw new CursoInvalidoException("Curso inválido. Por favor, selecione um curso válido.");
            } else {
                List<Aluno> alunos = alunoService.listarAlunosPorCurso(id.getAsInt(), getSelectedStatus());
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("Arquivos CSV (*.csv)", "csv");
                fileChooser.setDialogTitle("Salvar arquivo");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.addChoosableFileFilter(csvFilter);
                fileChooser.setFileFilter(csvFilter);
                int userSelection = fileChooser.showSaveDialog(this);
                if (userSelection != JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(this, "Exportação cancelada.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                File selectedFile = fileChooser.getSelectedFile();
                String path = selectedFile.getAbsolutePath();
        
                if (!path.toLowerCase().endsWith(".csv")) {
                    path += ".csv";
                }

                File finalFile = new File(path);
                if (finalFile.exists()) {
                    int overwriteConfirm = JOptionPane.showConfirmDialog(
                            this,
                            "O arquivo '" + finalFile.getName() + "' já existe. Deseja sobrescrevê-lo?",
                            "Confirmar Sobrescrita",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    if (overwriteConfirm != JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(this, "Exportação cancelada (arquivo existente).", "Aviso",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                alunoService.exportarAlunos(finalFile.getAbsolutePath(), alunos);
                this.dispose();
            }
        } catch (NoSuchElementException e) {
            throw new CursoInvalidoException("Curso inválido. Por favor, selecione um curso válido.");
        }

    }

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private javax.swing.JButton bntExportar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbxCurso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton rdAtivos;
    private javax.swing.JRadioButton rdInativos;
    private javax.swing.JRadioButton rdTodos;

}
