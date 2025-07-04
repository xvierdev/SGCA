/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import exceptions.CursoInvalidoException;
import exceptions.ErroSistemaException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import services.AlunoService;
import services.CursoService;

/**
 *
 * @author Família
 */
public class NovoCursoDialog extends javax.swing.JDialog {

    private CursoService cs;

    public NovoCursoDialog(JFrame owner, CursoService cs, AlunoService as) {
        super(owner, true);
        this.cs = cs;
        initComponents();
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtNomeCurso = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnConfirm = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        txtCargaHoraria = new javax.swing.JFormattedTextField();
        txtLimiteAlunos = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Adicione um novo curso");
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        jLabel1.setText("Nome");

        jLabel2.setText("Carga Horária");

        jLabel3.setText("Limite de Alunos");

        btnConfirm.setText("OK");
        btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancelar");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        txtCargaHoraria.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtCargaHoraria.setText("20");
        txtCargaHoraria.setInheritsPopupMenu(true);

        txtLimiteAlunos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtLimiteAlunos.setText("1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel1))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtNomeCurso)
                                                        .addComponent(txtCargaHoraria)
                                                        .addComponent(txtLimiteAlunos)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                layout.createSequentialGroup()
                                                        .addGap(0, 69, Short.MAX_VALUE)
                                                        .addComponent(btnConfirm)
                                                        .addPreferredGap(
                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(btnCancel)
                                                        .addGap(70, 70, 70)))
                                .addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtNomeCurso, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtCargaHoraria, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(txtLimiteAlunos, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnConfirm)
                                        .addComponent(btnCancel))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        pack();
        setLocationRelativeTo(null);
    }

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnConfirmActionPerformed
        String nomeCurso = txtNomeCurso.getText();
        String LimiteAlunos = txtLimiteAlunos.getText();
        String cargaHoraria = txtCargaHoraria.getText();

        try {
            int cursoLimite = Integer.parseInt(cargaHoraria);
            int alunoLimite = Integer.parseInt(LimiteAlunos);
            cs.adicionarCurso(nomeCurso, cursoLimite, alunoLimite);
            dispose();
        } catch (ErroSistemaException | NumberFormatException | CursoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }

    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnConfirm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JFormattedTextField txtCargaHoraria;
    private javax.swing.JFormattedTextField txtLimiteAlunos;
    private javax.swing.JTextField txtNomeCurso;
}
