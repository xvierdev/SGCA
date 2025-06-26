package gui;

import java.time.LocalDate;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import exceptions.AlunoInvalidoException;
import exceptions.ErroSistemaException;
import model.Aluno;
import model.Curso;
import model.enums.Status;
import services.AlunoService;
import services.CursoService;

public class EditAlunoDialog extends javax.swing.JDialog {

        private AlunoService alunoService;
        private CursoService cursoService;
        private Aluno aluno;

        public EditAlunoDialog(JFrame owner, AlunoService alunoService, CursoService cursoService, Aluno aluno) {
                super(owner, true);
                initComponents();
                this.alunoService = alunoService;
                this.cursoService = cursoService;
                this.aluno = aluno;
                txtNome.setText(aluno.getNome());
                txtCpf.setText(aluno.getCpf());
                txtTelefone.setText(aluno.getTelefone());
                txtEmail.setText(aluno.getEmail());
                txtDataNasc
                                .setText(aluno.getDataNascimento()
                                                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                if (aluno.isAtivo().isAtivo()) {
                        radioAtivo.setSelected(true);
                } else {
                        radioInativo.setSelected(true);
                }
                List<Curso> listaDeCursos = cursoService.listarTodosCursos(Status.ATIVO);
                List<String> nomesDosCursos = listaDeCursos.stream()
                                .map(Curso::getNome)
                                .toList();
                cbCursos.setModel(new DefaultComboBoxModel<>(nomesDosCursos.toArray(String[]::new)));
                lblCursoId.setText(String.valueOf(aluno.getIdAluno()));
        }

        private void initComponents() {

                buttonGroup1 = new javax.swing.ButtonGroup();
                jLabel1 = new javax.swing.JLabel();
                jLabel2 = new javax.swing.JLabel();
                jLabel3 = new javax.swing.JLabel();
                jLabel4 = new javax.swing.JLabel();
                jLabel5 = new javax.swing.JLabel();
                jLabel6 = new javax.swing.JLabel();
                jLabel7 = new javax.swing.JLabel();
                radioAtivo = new javax.swing.JRadioButton();
                radioInativo = new javax.swing.JRadioButton();
                cbCursos = new javax.swing.JComboBox<>();
                txtEmail = new javax.swing.JTextField();
                txtTelefone = new javax.swing.JFormattedTextField();
                txtCpf = new javax.swing.JFormattedTextField();
                txtNome = new javax.swing.JTextField();
                lblCursoId = new javax.swing.JLabel();
                btnConfirme = new javax.swing.JButton();
                btnCancel = new javax.swing.JButton();
                jLabel9 = new javax.swing.JLabel();
                txtDataNasc = new javax.swing.JFormattedTextField();

                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                setTitle("Editar aluno");
                setResizable(false);

                jLabel1.setText("ID");

                jLabel2.setText("Nome");

                jLabel3.setText("CPF");

                jLabel4.setText("Telefone");

                jLabel5.setText("Email");

                jLabel6.setText("Curso");

                jLabel7.setText("Situação");

                buttonGroup1.add(radioAtivo);
                radioAtivo.setText("Ativo");

                buttonGroup1.add(radioInativo);
                radioInativo.setText("Inativo");

                cbCursos.setModel(
                                new javax.swing.DefaultComboBoxModel<>(
                                                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                lblCursoId.setText("0000");

                btnConfirme.setText("Confirmar");
                btnConfirme.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnConfirmeActionPerformed(evt);
                        }
                });

                btnCancel.setText("Cancelar");
                btnCancel.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnCancelActionPerformed(evt);
                        }
                });

                jLabel9.setText("Data de nascimento");

                txtDataNasc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                                new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(jLabel1)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(lblCursoId))
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                layout
                                                                                                                .createSequentialGroup()
                                                                                                                .addGroup(layout
                                                                                                                                .createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                .addComponent(jLabel6)
                                                                                                                                .addComponent(jLabel5)
                                                                                                                                .addComponent(jLabel4)
                                                                                                                                .addComponent(jLabel3)
                                                                                                                                .addComponent(jLabel2))
                                                                                                                .addPreferredGap(
                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addGroup(layout
                                                                                                                                .createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                false)
                                                                                                                                .addComponent(txtNome)
                                                                                                                                .addComponent(txtCpf,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                320,
                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                .addComponent(txtTelefone)
                                                                                                                                .addComponent(txtEmail)
                                                                                                                                .addComponent(cbCursos,
                                                                                                                                                0,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                Short.MAX_VALUE)))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGroup(layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                .addGap(108, 108,
                                                                                                                                                108)
                                                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                                                .addComponent(jLabel7)
                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                .addComponent(radioAtivo)
                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                .addComponent(radioInativo))
                                                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                                                .addComponent(btnConfirme)
                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                .addComponent(btnCancel))))
                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                .addComponent(jLabel9)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(txtDataNasc,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                                .addGap(0, 113, Short.MAX_VALUE)))
                                                                .addContainerGap()));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel1)
                                                                                .addComponent(lblCursoId))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel2)
                                                                                .addComponent(txtNome,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel3)
                                                                                .addComponent(txtCpf,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel4)
                                                                                .addComponent(txtTelefone,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel5)
                                                                                .addComponent(txtEmail,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel6)
                                                                                .addComponent(cbCursos,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel9)
                                                                                .addComponent(txtDataNasc,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                19,
                                                                                Short.MAX_VALUE)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel7)
                                                                                .addComponent(radioAtivo)
                                                                                .addComponent(radioInativo))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(btnConfirme)
                                                                                .addComponent(btnCancel))
                                                                .addContainerGap()));

                pack();
                setLocationRelativeTo(null);
        }

        private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
                this.dispose();
        }

        private void btnConfirmeActionPerformed(java.awt.event.ActionEvent evt) {
                try {
                        String nome = txtNome.getText();
                        String cpf = txtCpf.getText();
                        String telefone = txtTelefone.getText();
                        String email = txtEmail.getText();
                        String curso = (String) cbCursos.getSelectedItem();
                        String dataNascimento = txtDataNasc.getText();
                        boolean ativo = radioAtivo.isSelected();
                        int idCurso = cursoService.obterCursoPorNome(curso).getIdCurso();
                        alunoService.atualizarAluno(aluno.getIdAluno(), idCurso, nome, cpf, telefone, email,
                                        LocalDate.parse(dataNascimento,
                                                        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                        ativo ? Status.ATIVO : Status.INATIVO);
                        this.dispose();
                } catch (AlunoInvalidoException | ErroSistemaException e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Ocorreu um erro ao atualizar o aluno.", "Erro",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

        private javax.swing.JButton btnCancel;
        private javax.swing.JButton btnConfirme;
        private javax.swing.ButtonGroup buttonGroup1;
        private javax.swing.JComboBox<String> cbCursos;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JLabel jLabel7;
        private javax.swing.JLabel jLabel9;
        private javax.swing.JLabel lblCursoId;
        private javax.swing.JRadioButton radioAtivo;
        private javax.swing.JRadioButton radioInativo;
        private javax.swing.JFormattedTextField txtCpf;
        private javax.swing.JFormattedTextField txtDataNasc;
        private javax.swing.JTextField txtEmail;
        private javax.swing.JTextField txtNome;
        private javax.swing.JFormattedTextField txtTelefone;
}
