package br.com.biblioteca.ui;

import br.com.biblioteca.dao.FuncionarioDAO;
import br.com.biblioteca.model.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioView extends JPanel {
    private final FuncionarioDAO dao = new FuncionarioDAO();

    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Matrícula", "Nome", "Cargo", "Salário"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabela = new JTable(modelo);
    private final List<Funcionario> linhas = new ArrayList<>();

    private final JTextField tfNome     = new JTextField();
    private final JTextField tfCpf      = new JTextField();
    private final JTextField tfEmail    = new JTextField();
    private final JTextField tfTelefone = new JTextField();
    private final JTextField tfMatricula= new JTextField();
    private final JTextField tfCargo    = new JTextField();
    private final JTextField tfSalario  = new JTextField();

    public FuncionarioView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(form(), BorderLayout.EAST);
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) preencher();
        });
        recarregar();
    }

    private JPanel form() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setPreferredSize(new Dimension(280, 0));
        p.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));
        LeitorView.addCampo(p, "Nome", tfNome);
        LeitorView.addCampo(p, "CPF", tfCpf);
        LeitorView.addCampo(p, "E-mail", tfEmail);
        LeitorView.addCampo(p, "Telefone", tfTelefone);
        LeitorView.addCampo(p, "Matrícula", tfMatricula);
        LeitorView.addCampo(p, "Cargo", tfCargo);
        LeitorView.addCampo(p, "Salário", tfSalario);

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

    private Funcionario selecionado() {
        int i = tabela.getSelectedRow();
        return (i < 0 || i >= linhas.size()) ? null : linhas.get(i);
    }

    private void preencher() {
        Funcionario f = selecionado();
        if (f == null) { limpar(); return; }
        tfNome.setText(f.getNome()); tfCpf.setText(f.getCpf());
        tfEmail.setText(f.getEmail()); tfTelefone.setText(f.getTelefone());
        tfMatricula.setText(f.getMatricula()); tfCargo.setText(f.getCargo());
        tfSalario.setText(f.getSalario() == null ? "" : f.getSalario().toPlainString());
    }
    private void limpar() {
        tfNome.setText(""); tfCpf.setText(""); tfEmail.setText("");
        tfTelefone.setText(""); tfMatricula.setText("");
        tfCargo.setText(""); tfSalario.setText("");
    }

    private void salvar() {
        if (tfNome.getText().isBlank()) throw new IllegalArgumentException("Informe o nome.");
        BigDecimal salario;
        try { salario = new BigDecimal(tfSalario.getText().replace(",", ".")); }
        catch (Exception ex) { throw new IllegalArgumentException("Salário inválido."); }

        Funcionario sel = selecionado();
        if (sel == null) {
            dao.inserir(new Funcionario(null, tfNome.getText(), tfCpf.getText(),
                    tfEmail.getText(), tfTelefone.getText(),
                    tfMatricula.getText(), tfCargo.getText(), salario, LocalDate.now()));
            Ui.info("Funcionário cadastrado.");
        } else {
            sel.setNome(tfNome.getText()); sel.setCpf(tfCpf.getText());
            sel.setEmail(tfEmail.getText()); sel.setTelefone(tfTelefone.getText());
            sel.setMatricula(tfMatricula.getText()); sel.setCargo(tfCargo.getText());
            sel.setSalario(salario);
            dao.atualizar(sel);
            Ui.info("Funcionário atualizado.");
        }
        recarregar();
    }

    private void remover() {
        Funcionario sel = selecionado();
        if (sel == null) throw new IllegalArgumentException("Selecione um funcionário.");
        dao.remover(sel.getId());
        recarregar(); limpar();
    }

    public void recarregar() {
        Ui.runCatching(() -> {
            linhas.clear();
            linhas.addAll(dao.listarTodos());
            modelo.setRowCount(0);
            for (Funcionario f : linhas) {
                modelo.addRow(new Object[]{f.getMatricula(), f.getNome(), f.getCargo(), f.getSalario()});
            }
        });
    }
}
