package br.com.biblioteca.app;

import br.com.biblioteca.ui.*;

import javax.swing.*;


public class MainApp {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(MainApp::criarJanela);
    }

    private static void criarJanela() {
        JFrame frame = new JFrame("Sistema de Controle de Biblioteca");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 640);
        frame.setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Leitores", new LeitorView());
        tabs.addTab("Funcionários", new FuncionarioView());
        tabs.addTab("Obras", new ObraView());
        tabs.addTab("Cópias", new CopiaView());
        tabs.addTab("Empréstimos", new EmprestimoView());
        tabs.addTab("Reservas", new ReservaView());

        frame.setContentPane(tabs);
        frame.setVisible(true);
    }
}
