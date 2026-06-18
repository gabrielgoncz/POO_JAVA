package br.com.biblioteca.ui;

import br.com.biblioteca.dao.LeitorDAO;
import br.com.biblioteca.model.Leitor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeitorView extends JPanel {
    private final LeitorDAO dao = new LeitorDAO();

    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Matrícula", "Nome", "CPF", "Ativo"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabela = new JTable(modelo);
    private final List<Leitor> linhas = new ArrayList<>();

    private final JTextField tfNome     = new JTextField();
    private final JTextField tfCpf      = new JTextField();
    private final JTextField tfEmail    = new JTextField();
    private final JTextField tfTelefone = new JTextField();
    private final JTextField tfMatricula= new JTextField();
    private final JCheckBox  cbAtivo    = new JCheckBox("Ativo", true);

    public LeitorView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(formulario(), BorderLayout.EAST);
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) preencher();
        });
        recarregar();
    }

    private JPanel formulario() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setPreferredSize(new Dimension(280, 0));
        p.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));
        addCampo(p, "Nome", tfNome);
        addCampo(p, "CPF", tfCpf);
        addCampo(p, "E-mail", tfEmail);
        addCampo(p, "Telefone", tfTelefone);
        addCampo(p, "Matrícula", tfMatricula);
        p.add(cbAtivo);
        p.add(Box.createVerticalStrut(8));

        JButton bSalvar = new JButton("Salvar");
        JButton bNovo   = new JButton("Novo");
        JButton bRem    = new JButton("Remover");
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        botoes.add(bSalvar); botoes.add(bNovo); botoes.add(bRem);
        p.add(botoes);

        bNovo.addActionListener(e -> { tabela.clearSelection(); limpar(); });
        bSalvar.addActionListener(e -> Ui.runCatching(this::salvar));
        bRem.addActionListener(e -> Ui.runCatching(this::remover));
        return p;
    }

    static void addCampo(JPanel p, String label, JComponent campo) {
        JLabel l = new JLabel(label);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 26));
        p.add(l); p.add(campo); p.add(Box.createVerticalStrut(4));
    }

    private Leitor selecionado() {
        int i = tabela.getSelectedRow();
        return (i < 0 || i >= linhas.size()) ? null : linhas.get(i);
    }

    private void preencher() {
        Leitor l = selecionado();
        if (l == null) { limpar(); return; }
        tfNome.setText(l.getNome()); tfCpf.setText(l.getCpf());
        tfEmail.setText(l.getEmail()); tfTelefone.setText(l.getTelefone());
        tfMatricula.setText(l.getMatricula()); cbAtivo.setSelected(l.isAtivo());
    }
    private void limpar() {
        tfNome.setText(""); tfCpf.setText(""); tfEmail.setText("");
        tfTelefone.setText(""); tfMatricula.setText(""); cbAtivo.setSelected(true);
    }

    private void salvar() {
        if (tfNome.getText().isBlank()) throw new IllegalArgumentException("Informe o nome.");
        if (tfCpf.getText().isBlank()) throw new IllegalArgumentException("Informe o CPF.");
        if (tfMatricula.getText().isBlank()) throw new IllegalArgumentException("Informe a matrícula.");

        Leitor sel = selecionado();
        if (sel == null) {
            Leitor novo = new Leitor(null, tfNome.getText(), tfCpf.getText(),
                    tfEmail.getText(), tfTelefone.getText(),
                    tfMatricula.getText(), LocalDate.now(), cbAtivo.isSelected());
            dao.inserir(novo);
            Ui.info("Leitor cadastrado.");
        } else {
            sel.setNome(tfNome.getText()); sel.setCpf(tfCpf.getText());
            sel.setEmail(tfEmail.getText()); sel.setTelefone(tfTelefone.getText());
            sel.setMatricula(tfMatricula.getText()); sel.setAtivo(cbAtivo.isSelected());
            dao.atualizar(sel);
            Ui.info("Leitor atualizado.");
        }
        recarregar();
    }

    private void remover() {
        Leitor sel = selecionado();
        if (sel == null) throw new IllegalArgumentException("Selecione um leitor.");
        dao.remover(sel.getId());
        recarregar();
        limpar();
    }

    public void recarregar() {
        Ui.runCatching(() -> {
            linhas.clear();
            linhas.addAll(dao.listarTodos());
            modelo.setRowCount(0);
            for (Leitor l : linhas) {
                modelo.addRow(new Object[]{l.getMatricula(), l.getNome(), l.getCpf(), l.isAtivo()});
            }
        });
    }
}
