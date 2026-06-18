package br.com.biblioteca.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * ASSOCIAÇÃO entre Leitor, Cópia e Funcionário responsável.
 * Demonstra POLIMORFISMO sobrescrevendo {@link #calcularMulta()} em
 * subclasses (regras diferentes por tipo de empréstimo).
 */
public class Emprestimo {
    public enum Status { ATIVO, DEVOLVIDO, ATRASADO }

    public static final BigDecimal MULTA_DIARIA_PADRAO = new BigDecimal("2.00");

    private Long id;
    private Leitor leitor;
    private Copia copia;
    private Funcionario funcionarioResponsavel;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevista;
    private LocalDate dataDevolucao;
    private BigDecimal multa = BigDecimal.ZERO;
    private Status status = Status.ATIVO;

    public Emprestimo() {}

    public Emprestimo(Long id, Leitor leitor, Copia copia, Funcionario func,
                      LocalDate dataEmp, LocalDate dataPrev, LocalDate dataDev,
                      BigDecimal multa, Status status) {
        this.id = id; this.leitor = leitor; this.copia = copia;
        this.funcionarioResponsavel = func;
        this.dataEmprestimo = dataEmp; this.dataPrevista = dataPrev;
        this.dataDevolucao = dataDev;
        this.multa = multa == null ? BigDecimal.ZERO : multa;
        this.status = status;
    }

    public BigDecimal calcularMulta() {
        LocalDate referencia = dataDevolucao != null ? dataDevolucao : LocalDate.now();
        long atraso = ChronoUnit.DAYS.between(dataPrevista, referencia);
        if (atraso <= 0) return BigDecimal.ZERO;
        return MULTA_DIARIA_PADRAO.multiply(BigDecimal.valueOf(atraso));
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Leitor getLeitor() { return leitor; }
    public void setLeitor(Leitor leitor) { this.leitor = leitor; }
    public Copia getCopia() { return copia; }
    public void setCopia(Copia copia) { this.copia = copia; }
    public Funcionario getFuncionarioResponsavel() { return funcionarioResponsavel; }
    public void setFuncionarioResponsavel(Funcionario f) { this.funcionarioResponsavel = f; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDate d) { this.dataEmprestimo = d; }
    public LocalDate getDataPrevista() { return dataPrevista; }
    public void setDataPrevista(LocalDate d) { this.dataPrevista = d; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDate d) { this.dataDevolucao = d; }
    public BigDecimal getMulta() { return multa; }
    public void setMulta(BigDecimal multa) { this.multa = multa; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
