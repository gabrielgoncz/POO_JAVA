package br.com.biblioteca.model;

import java.time.LocalDate;

/** Reserva de uma Obra por um Leitor (ASSOCIAÇÃO). */
public class Reserva {
    public enum Status { ATIVA, ATENDIDA, CANCELADA, EXPIRADA }

    private Long id;
    private Leitor leitor;
    private Obra obra;
    private LocalDate dataReserva;
    private LocalDate dataValidade;
    private Status status = Status.ATIVA;

    public Reserva() {}

    public Reserva(Long id, Leitor leitor, Obra obra,
                   LocalDate dataReserva, LocalDate dataValidade, Status status) {
        this.id = id; this.leitor = leitor; this.obra = obra;
        this.dataReserva = dataReserva; this.dataValidade = dataValidade;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Leitor getLeitor() { return leitor; }
    public void setLeitor(Leitor leitor) { this.leitor = leitor; }
    public Obra getObra() { return obra; }
    public void setObra(Obra obra) { this.obra = obra; }
    public LocalDate getDataReserva() { return dataReserva; }
    public void setDataReserva(LocalDate d) { this.dataReserva = d; }
    public LocalDate getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDate d) { this.dataValidade = d; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
