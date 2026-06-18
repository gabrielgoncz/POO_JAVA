package br.com.biblioteca.ui;

import br.com.biblioteca.dao.*;
import br.com.biblioteca.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoView extends JPanel {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final EmprestimoDAO dao = new EmprestimoDAO();
    private final LeitorDAO leitorDAO = new LeitorDAO();
    private final FuncionarioDAO funcDAO = new FuncionarioDAO();
    private final ObraDAO obraDAO = new ObraDAO();
    private final CopiaDAO copiaDAO = new CopiaDAO();

    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"#", "Leitor", "Obra/Cópia", "Empréstimo", "Prevista", "Devolução", "Multa", "Status"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabela = new JTable(modelo);
    private final List<Emprestimo> linhas = new ArrayList<>();

    private final JComboBox<Leitor> cbLeitor = new JComboBox<>();
    private final JComboBox<Funcionario> cbFunc = new JComboBox<>();
    private final JComboBox<Obra> cbObra = new JComboBox<>();
    private final JComboBox<Copia> cbCopia = new JComboBox<>();
    private final JTextField tfPrevista = new JTextField(LocalDate.now().plusDays(7).format(FMT));

    public EmprestimoView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(form(), BorderLayout.EAST);

        cbLeitor.setRenderer(Renderers.combo(o -> ((Leitor) o).getNome() + " (" + ((Leitor) o).getMatricula() + ")"));
        cbFunc.setRenderer(Renderers.combo(o -> ((Funcionario) o).getNome()));
        cbObra.setRenderer(Renderers.combo(o -> ((Obra) o).getTitulo()));
        cbCopia.setRenderer(Renderers.combo(o -> ((Copia) o).getCodigoTombo() + " [" + ((Copia) o).getEstado() + "]"));

        cbObra.addActionListener(e -> Ui.runCatching(() -> {
            cbCopia.removeAllItems();
            Obra o = (Obra) cbObra.getSelectedItem();
            if (o != null) {
                for (Copia c : copiaDAO.listarDisponiveisPorObra(o.getId())) cbCopia.addItem(c);
            }
        }));

        recarregar();
    }

    private JPanel form() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setPreferredSize(new Dimension(320, 0));
        p.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));
        LeitorView.addCampo(p, "Leitor", cbLeitor);
        LeitorView.addCampo(p, "Funcionário responsável", cbFunc);
        LeitorView.addCampo(p, "Obra", cbObra);
        LeitorView.addCampo(p, "Cópia disponível", cbCopia);
        LeitorView.addCampo(p, "Data prevista (yyyy-MM-dd)", tfPrevista);

        JButton bEmprestar = new JButton("Registrar empréstimo");
        JButton bDevolver  = new JButton("Devolver selecionado");
        bEmprestar.setAlignmentX(Component.LEFT_ALIGNMENT);
        bDevolver.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(bEmprestar);
        p.add(Box.createVerticalStrut(6));
        p.add(bDevolver);

        bEmprestar.addActionListener(e -> Ui.runCatching(this::emprestar));
        bDevolver.addActionListener(e -> Ui.runCatching(this::devolver));
        return p;
    }

    private Emprestimo selecionado() {
        int i = tabela.getSelectedRow();
        return (i < 0 || i >= linhas.size()) ? null : linhas.get(i);
    }

    private void emprestar() {
        Leitor l = (Leitor) cbLeitor.getSelectedItem();
        Funcionario f = (Funcionario) cbFunc.getSelectedItem();
        Copia c = (Copia) cbCopia.getSelectedItem();
        LocalDate prev;
        try { prev = LocalDate.parse(tfPrevista.getText().trim(), FMT); }
        catch (Exception ex) { throw new IllegalArgumentException("Data prevista inválida (use yyyy-MM-dd)."); }

        if (l == null) throw new IllegalArgumentException("Selecione o leitor.");
        if (f == null) throw new IllegalArgumentException("Selecione o funcionário responsável.");
        if (c == null) throw new IllegalArgumentException("Selecione a cópia disponível.");
        if (prev.isBefore(LocalDate.now())) throw new IllegalArgumentException("Data prevista no passado.");

        Emprestimo e = new Emprestimo();
        e.setLeitor(l); e.setFuncionarioResponsavel(f); e.setCopia(c);
        e.setDataEmprestimo(LocalDate.now()); e.setDataPrevista(prev);
        dao.inserir(e);
        Ui.info("Empréstimo registrado.");
        recarregar();
    }

    private void devolver() {
        Emprestimo sel = selecionado();
        if (sel == null) throw new IllegalArgumentException("Selecione um empréstimo.");
        dao.devolver(sel.getId());
        Ui.info("Devolução registrada.");
        recarregar();
    }

    public void recarregar() {
        Ui.runCatching(() -> {
            cbLeitor.removeAllItems();
            for (Leitor l : leitorDAO.listarTodos()) cbLeitor.addItem(l);
            cbLeitor.setSelectedIndex(-1);

            cbFunc.removeAllItems();
            for (Funcionario f : funcDAO.listarTodos()) cbFunc.addItem(f);
            cbFunc.setSelectedIndex(-1);

            cbObra.removeAllItems();
            for (Obra o : obraDAO.listarTodos()) cbObra.addItem(o);
            cbObra.setSelectedIndex(-1);

            cbCopia.removeAllItems();

            linhas.clear();
            linhas.addAll(dao.listarTodos());
            modelo.setRowCount(0);
            for (Emprestimo e : linhas) {
                modelo.addRow(new Object[]{
                        e.getId(),
                        e.getLeitor().getNome(),
                        e.getCopia().getObra().getTitulo() + " (" + e.getCopia().getCodigoTombo() + ")",
                        e.getDataEmprestimo(),
                        e.getDataPrevista(),
                        e.getDataDevolucao(),
                        e.getMulta(),
                        e.getStatus()
                });
            }
        });
    }
}
