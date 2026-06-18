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

public class ReservaView extends JPanel {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final ReservaDAO dao = new ReservaDAO();
    private final LeitorDAO leitorDAO = new LeitorDAO();
    private final ObraDAO obraDAO = new ObraDAO();

    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"#", "Leitor", "Obra", "Reserva", "Validade", "Status"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabela = new JTable(modelo);
    private final List<Reserva> linhas = new ArrayList<>();

    private final JComboBox<Leitor> cbLeitor = new JComboBox<>();
    private final JComboBox<Obra>   cbObra   = new JComboBox<>();
    private final JTextField tfValidade = new JTextField(LocalDate.now().plusDays(3).format(FMT));

    public ReservaView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(form(), BorderLayout.EAST);

        cbLeitor.setRenderer(Renderers.combo(o -> ((Leitor) o).getNome()));
        cbObra.setRenderer(Renderers.combo(o -> ((Obra) o).getTitulo()));

        recarregar();
    }

    private JPanel form() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setPreferredSize(new Dimension(300, 0));
        p.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));
        LeitorView.addCampo(p, "Leitor", cbLeitor);
        LeitorView.addCampo(p, "Obra", cbObra);
        LeitorView.addCampo(p, "Validade (yyyy-MM-dd)", tfValidade);

        JButton bReservar = new JButton("Reservar");
        JButton bCancelar = new JButton("Cancelar selecionada");
        bReservar.setAlignmentX(Component.LEFT_ALIGNMENT);
        bCancelar.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(bReservar);
        p.add(Box.createVerticalStrut(6));
        p.add(bCancelar);

        bReservar.addActionListener(e -> Ui.runCatching(this::reservar));
        bCancelar.addActionListener(e -> Ui.runCatching(this::cancelar));
        return p;
    }

    private Reserva selecionada() {
        int i = tabela.getSelectedRow();
        return (i < 0 || i >= linhas.size()) ? null : linhas.get(i);
    }

    private void reservar() {
        Leitor l = (Leitor) cbLeitor.getSelectedItem();
        Obra o = (Obra) cbObra.getSelectedItem();
        if (l == null) throw new IllegalArgumentException("Selecione o leitor.");
        if (o == null) throw new IllegalArgumentException("Selecione a obra.");
        LocalDate validade;
        try { validade = LocalDate.parse(tfValidade.getText().trim(), FMT); }
        catch (Exception ex) { throw new IllegalArgumentException("Validade inválida (use yyyy-MM-dd)."); }

        Reserva r = new Reserva();
        r.setLeitor(l); r.setObra(o);
        r.setDataReserva(LocalDate.now());
        r.setDataValidade(validade);
        dao.inserir(r);
        Ui.info("Reserva criada.");
        recarregar();
    }

    private void cancelar() {
        Reserva sel = selecionada();
        if (sel == null) throw new IllegalArgumentException("Selecione uma reserva.");
        dao.cancelar(sel.getId());
        Ui.info("Reserva cancelada.");
        recarregar();
    }

    public void recarregar() {
        Ui.runCatching(() -> {
            cbLeitor.removeAllItems();
            for (Leitor l : leitorDAO.listarTodos()) cbLeitor.addItem(l);
            cbLeitor.setSelectedIndex(-1);

            cbObra.removeAllItems();
            for (Obra o : obraDAO.listarTodos()) cbObra.addItem(o);
            cbObra.setSelectedIndex(-1);

            linhas.clear();
            linhas.addAll(dao.listarTodos());
            modelo.setRowCount(0);
            for (Reserva r : linhas) {
                modelo.addRow(new Object[]{
                        r.getId(),
                        r.getLeitor().getNome(),
                        r.getObra().getTitulo(),
                        r.getDataReserva(),
                        r.getDataValidade(),
                        r.getStatus()
                });
            }
        });
    }
}
