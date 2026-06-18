package br.com.biblioteca.ui;

import br.com.biblioteca.dao.CopiaDAO;
import br.com.biblioteca.dao.ObraDAO;
import br.com.biblioteca.model.Copia;
import br.com.biblioteca.model.Obra;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CopiaView extends JPanel {
    private final CopiaDAO dao = new CopiaDAO();
    private final ObraDAO obraDAO = new ObraDAO();

    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Tombo", "Obra", "Estado"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabela = new JTable(modelo);
    private final List<Copia> linhas = new ArrayList<>();

    private final JComboBox<Obra> cbObra = new JComboBox<>();
    private final JTextField tfTombo = new JTextField();
    private final JComboBox<Copia.Estado> cbEstado = new JComboBox<>(Copia.Estado.values());

    public CopiaView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(form(), BorderLayout.EAST);

        cbObra.setRenderer(Renderers.combo(o -> ((Obra) o).getTitulo()));

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
        LeitorView.addCampo(p, "Obra", cbObra);
        LeitorView.addCampo(p, "Código (tombo)", tfTombo);
        LeitorView.addCampo(p, "Estado", cbEstado);
        cbEstado.setSelectedItem(Copia.Estado.DISPONIVEL);

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

    private Copia selecionado() {
        int i = tabela.getSelectedRow();
        return (i < 0 || i >= linhas.size()) ? null : linhas.get(i);
    }

    private void preencher() {
        Copia c = selecionado();
        if (c == null) { limpar(); return; }
        tfTombo.setText(c.getCodigoTombo());
        cbEstado.setSelectedItem(c.getEstado());
        for (int i = 0; i < cbObra.getItemCount(); i++) {
            Obra o = cbObra.getItemAt(i);
            if (o.getId().equals(c.getObra().getId())) { cbObra.setSelectedIndex(i); break; }
        }
    }
    private void limpar() {
        tfTombo.setText("");
        cbEstado.setSelectedItem(Copia.Estado.DISPONIVEL);
        cbObra.setSelectedIndex(-1);
    }

    private void salvar() {
        Obra obra = (Obra) cbObra.getSelectedItem();
        if (obra == null) throw new IllegalArgumentException("Selecione a obra.");
        if (tfTombo.getText().isBlank()) throw new IllegalArgumentException("Informe o tombo.");

        Copia sel = selecionado();
        if (sel == null) {
            dao.inserir(new Copia(null, obra, tfTombo.getText(), (Copia.Estado) cbEstado.getSelectedItem()));
            Ui.info("Cópia cadastrada.");
        } else {
            sel.setCodigoTombo(tfTombo.getText());
            sel.setEstado((Copia.Estado) cbEstado.getSelectedItem());
            dao.atualizar(sel);
            Ui.info("Cópia atualizada.");
        }
        recarregar();
    }

    private void remover() {
        Copia sel = selecionado();
        if (sel == null) throw new IllegalArgumentException("Selecione uma cópia.");
        dao.remover(sel.getId());
        recarregar(); limpar();
    }

    public void recarregar() {
        Ui.runCatching(() -> {
            cbObra.removeAllItems();
            for (Obra o : obraDAO.listarTodos()) cbObra.addItem(o);
            cbObra.setSelectedIndex(-1);

            linhas.clear();
            linhas.addAll(dao.listarTodos());
            modelo.setRowCount(0);
            for (Copia c : linhas) {
                modelo.addRow(new Object[]{
                        c.getCodigoTombo(),
                        c.getObra() == null ? "" : c.getObra().getTitulo(),
                        c.getEstado()});
            }
        });
    }
}
