package br.com.biblioteca.ui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

/** Utilitários de renderização para combos. */
final class Renderers {
    private Renderers() {}

    /** Renderer que mostra texto extraído pelo mapeador. */
    @SuppressWarnings({"rawtypes", "unchecked"})
    static ListCellRenderer combo(Function<Object, String> mapper) {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                ((JLabel) c).setText(value == null ? "" : mapper.apply(value));
                return c;
            }
        };
    }
}
