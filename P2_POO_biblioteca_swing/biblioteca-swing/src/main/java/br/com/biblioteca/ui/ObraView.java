package br.com.biblioteca.ui;

import br.com.biblioteca.dao.ObraDAO;
import br.com.biblioteca.model.Obra;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ObraView extends JPanel {
    private final ObraDAO dao = new ObraDAO();

    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Título", "Autor", "Ano", "Categoria"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabela = new JTable(modelo);
    private final List<Obra> linhas = new ArrayList<>();

    private final JTextField tfTitulo    = new JTextField();
    private final JTextField tfAutor     = new JTextField();
    private final JTextField tfEditora   = new JTextField();
    private final JTextField tfAno       = new JTextField();
    private final JTextField tfIsbn      = new JTextField();
    private final JTextField tfCategoria = new JTextField();

    public ObraView() {
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
        LeitorView.addCampo(p, "Título", tfTitulo);
        LeitorView.addCampo(p, "Autor", tfAutor);
        LeitorView.addCampo(p, "Editora", tfEditora);
        LeitorView.addCampo(p, "Ano", tfAno);
        LeitorView.addCampo(p, "ISBN", tfIsbn);
        LeitorView.addCampo(p, "Categoria", tfCategoria);

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

    private Obra selecionado() {
        int i = tabela.getSelectedRow();
        return (i < 0 || i >= linhas.size()) ? null : linhas.get(i);
    }

    private void preencher() {
        Obra o = selecionado();
        if (o == null) { limpar(); return; }
        tfTitulo.setText(o.getTitulo()); tfAutor.setText(o.getAutor());
        tfEditora.setText(o.getEditora());
        tfAno.setText(o.getAno() == null ? "" : o.getAno().toString());
        tfIsbn.setText(o.getIsbn()); tfCategoria.setText(o.getCategoria());
    }
    private void limpar() {
        tfTitulo.setText(""); tfAutor.setText(""); tfEditora.setText("");
        tfAno.setText(""); tfIsbn.setText(""); tfCategoria.setText("");
    }

    private void salvar() {
        if (tfTitulo.getText().isBlank()) throw new IllegalArgumentException("Informe o título.");
        if (tfAutor.getText().isBlank()) throw new IllegalArgumentException("Informe o autor.");
        Integer ano = null;
        if (!tfAno.getText().isBlank()) {
            try { ano = Integer.parseInt(tfAno.getText()); }
            catch (Exception e) { throw new IllegalArgumentException("Ano inválido."); }
        }
        Obra sel = selecionado();
        if (sel == null) {
            dao.inserir(new Obra(null, tfTitulo.getText(), tfAutor.getText(),
                    tfEditora.getText(), ano, tfIsbn.getText(), tfCategoria.getText()));
            Ui.info("Obra cadastrada.");
        } else {
            sel.setTitulo(tfTitulo.getText()); sel.setAutor(tfAutor.getText());
            sel.setEditora(tfEditora.getText()); sel.setAno(ano);
            sel.setIsbn(tfIsbn.getText()); sel.setCategoria(tfCategoria.getText());
            dao.atualizar(sel);
            Ui.info("Obra atualizada.");
        }
        recarregar();
    }

    private void remover() {
        Obra sel = selecionado();
        if (sel == null) throw new IllegalArgumentException("Selecione uma obra.");
        dao.remover(sel.getId());
        recarregar(); limpar();
    }

    public void recarregar() {
        Ui.runCatching(() -> {
            linhas.clear();
            linhas.addAll(dao.listarTodos());
            modelo.setRowCount(0);
            for (Obra o : linhas) {
                modelo.addRow(new Object[]{o.getTitulo(), o.getAutor(), o.getAno(), o.getCategoria()});
            }
        });
    }
}
