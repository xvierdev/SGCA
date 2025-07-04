package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import exceptions.CursoInvalidoException;
import exceptions.ErroSistemaException;
import model.Curso;
import model.enums.Status;
import services.AlunoService;
import services.CursoService;

public class EditCursoDialog extends javax.swing.JDialog {

        private CursoService cursoService;
        private Curso selectedCurso;

        public EditCursoDialog(JFrame owner, AlunoService alunoService, CursoService cursoService,
                        Curso selectedCurso) {
                super(owner, true);
                this.cursoService = cursoService;
                this.selectedCurso = selectedCurso;
                initComponents();
                lblCursoId.setText(String.valueOf(String.valueOf(selectedCurso.getIdCurso())));
                txtNomeCurso.setText(selectedCurso.getNome());
                txtCargaHoraria.setText(String.valueOf(selectedCurso.getCargaHoraria()));
                txtLimiteAlunos.setText(String.valueOf(selectedCurso.getLimiteAlunos()));
                if (selectedCurso.isAtivo().isAtivo()) {
                        radioAtivo.setSelected(true);
                } else {
                        radioInativo.setSelected(true);
                }
        }

        private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        radioAtivo = new javax.swing.JRadioButton();
        radioInativo = new javax.swing.JRadioButton();
        txtLimiteAlunos = new javax.swing.JFormattedTextField();
        txtCargaHoraria = new javax.swing.JFormattedTextField();
        txtNomeCurso = new javax.swing.JTextField();
        lblCursoId = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editar curso");
        setResizable(false);

        jLabel1.setText("ID");

        jLabel2.setText("Nome");

        jLabel3.setText("Carga Horária");

        jLabel4.setText("Limite de Alunos");

        jLabel5.setText("Status");

        buttonGroup1.add(radioAtivo);
        radioAtivo.setText("Ativo");

        buttonGroup1.add(radioInativo);
        radioInativo.setText("Inativo");

        txtLimiteAlunos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        txtCargaHoraria.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        lblCursoId.setText("000");

        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
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
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCursoId))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNomeCurso)
                            .addComponent(txtCargaHoraria)
                            .addComponent(txtLimiteAlunos)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioAtivo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioInativo)
                                .addGap(0, 127, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)
                        .addGap(127, 127, 127)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblCursoId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNomeCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCargaHoraria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtLimiteAlunos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(radioAtivo)
                    .addComponent(radioInativo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

        private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
                this.dispose();
        }

        private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {
                try {
                        boolean ativo = radioAtivo.isSelected();
                        cursoService.atualizarCurso(selectedCurso.getIdCurso(),
                                        txtNomeCurso.getText(),
                                        Integer.parseInt(txtCargaHoraria.getText()),
                                        Integer.parseInt(txtLimiteAlunos.getText()),
                                        ativo ? Status.ATIVO : Status.INATIVO);
                        this.dispose();
                } catch (ErroSistemaException | CursoInvalidoException e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Erro",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblCursoId;
    private javax.swing.JRadioButton radioAtivo;
    private javax.swing.JRadioButton radioInativo;
    private javax.swing.JFormattedTextField txtCargaHoraria;
    private javax.swing.JFormattedTextField txtLimiteAlunos;
    private javax.swing.JTextField txtNomeCurso;
}
