package gui;

import exceptions.AlunoInvalidoException;
import exceptions.ErroSistemaException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Curso;
import model.enums.Status;
import services.AlunoService;
import services.CursoService;

public class NovoAlunoDialog extends javax.swing.JDialog {

    private CursoService cs;
    private AlunoService as;

    public NovoAlunoDialog(JFrame owner, CursoService cursoService, AlunoService alunoService) {
        super(owner, true);
        this.cs = cursoService;
        this.as = alunoService;
        initComponents();
        List<Curso> listaDeCursos = cs.listarTodosCursos(Status.ATIVO);
        List<String> nomesDosCursos = listaDeCursos.stream()
                .map(Curso::getNome)
                .collect(Collectors.toList());
        cbCursos.setModel(new DefaultComboBoxModel<>(nomesDosCursos.toArray(String[]::new)));
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        cbCursos = new javax.swing.JComboBox<>();
        txtCpf = new javax.swing.JFormattedTextField();
        txtTelefone = new javax.swing.JFormattedTextField();
        txtEmail = new javax.swing.JTextField();
        btnConfirmar = new javax.swing.JButton();
        bntCancelar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtDataNasc = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Adicione um novo aluno");
        setResizable(false);

        jLabel1.setText("Nome");

        jLabel2.setText("Curso");

        jLabel3.setText("CPF");

        jLabel4.setText("Telefone");

        jLabel5.setText("Email");

        cbCursos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "temp1", "temp2" }));

        try {
            txtCpf.setFormatterFactory(
                    new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCpf.setToolTipText("");

        try {
            txtTelefone.setFormatterFactory(
                    new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        bntCancelar.setText("Cancelar");
        bntCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCancelarActionPerformed(evt);
            }
        });

        jLabel6.setText("Data Nascimento");

        txtDataNasc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(120, 120, 120)
                                                                .addComponent(btnConfirmar)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(bntCancelar))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel6)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(txtDataNasc,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 83,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(0, 108, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel4)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel1))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtNome)
                                                        .addComponent(cbCursos, 0, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE)
                                                        .addComponent(txtCpf)
                                                        .addComponent(txtTelefone)
                                                        .addComponent(txtEmail))))
                                .addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(txtDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21,
                                        Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnConfirmar)
                                        .addComponent(bntCancelar))
                                .addContainerGap()));

        pack();
        setLocationRelativeTo(null);
    }

    private void bntCancelarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bntCancelarActionPerformed
        this.dispose();
    }

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnConfirmarActionPerformed
        String nomeCurso = cbCursos.getSelectedItem().toString();
        String nomeAluno = txtNome.getText();
        String cpf = txtCpf.getText().replace(".", "").replace("-", "");
        String telefone = txtTelefone.getText().replace("(", "").replace(")", "").replace("-", "");
        String email = txtEmail.getText();
        String dataNasc = txtDataNasc.getText();
        System.out.println(telefone);
        if (nomeAluno.isBlank() || cpf.isBlank() || telefone.isBlank() || email.isBlank() || dataNasc.isBlank()) {
            JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.\n");
        } else {
            Curso cursoSelecionado = cs.obterCursoPorNome(cbCursos.getSelectedItem().toString());
            if (cursoSelecionado == null) {
                JOptionPane.showMessageDialog(null, "Selecione um curso.\n");
            } else {
                try {
                    OptionalInt id = cs.obterIdCursoPorNome(nomeCurso);
                    if (id.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Curso selecionado não encontrado.\n");
                    } else {
                        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        int idCurso = cs.obterIdCursoPorNome(nomeCurso).getAsInt();
                        as.adicionarAluno(idCurso, nomeAluno, cpf, telefone, email, LocalDate.parse(dataNasc, fmt));
                        this.dispose();
                    }
                } catch (ErroSistemaException | AlunoInvalidoException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        }

    }

    private javax.swing.JButton bntCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox<String> cbCursos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JFormattedTextField txtCpf;
    private javax.swing.JFormattedTextField txtDataNasc;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNome;
    private javax.swing.JFormattedTextField txtTelefone;
}
