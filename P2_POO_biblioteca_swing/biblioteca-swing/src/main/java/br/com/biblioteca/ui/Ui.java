package br.com.biblioteca.ui;

import br.com.biblioteca.exception.DAOException;
import br.com.biblioteca.exception.RegraNegocioException;

import javax.swing.JOptionPane;

/**
 * Helpers de UI — TRATAMENTO DE EXCEÇÕES centralizado AQUI,
 * o mais próximo possível da camada de apresentação (Swing).
 * As camadas de modelo/DAO apenas lançam DAOException / RegraNegocioException.
 */
public final class Ui {
    private Ui() {}

    public static void info(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void erro(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    /** Executa uma ação capturando exceções comuns e mostrando ao usuário. */
    public static void runCatching(Runnable acao) {
        try {
            acao.run();
        } catch (RegraNegocioException ex) {
            erro(ex.getMessage());
        } catch (DAOException ex) {
            ex.printStackTrace();
            erro("Erro de acesso a dados: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            erro("Dados inválidos: " + ex.getMessage());
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            erro("Erro inesperado: " + ex.getMessage());
        }
    }
}
