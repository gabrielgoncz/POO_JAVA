package br.com.biblioteca.model;

import java.math.BigDecimal;

/**
 * POLIMORFISMO: empréstimo a estudante usa multa reduzida.
 * Demonstra sobrescrita de comportamento na hierarquia de Empréstimo.
 */
public class EmprestimoEstudante extends Emprestimo {
    public static final BigDecimal MULTA_DIARIA_ESTUDANTE = new BigDecimal("1.00");

    @Override
    public BigDecimal calcularMulta() {
        BigDecimal padrao = super.calcularMulta();
        return padrao.multiply(MULTA_DIARIA_ESTUDANTE)
                     .divide(MULTA_DIARIA_PADRAO);
    }
}
