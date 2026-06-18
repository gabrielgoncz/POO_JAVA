package br.com.biblioteca.model;

/** Cópia física de uma obra (AGREGAÇÃO em Obra, ASSOCIAÇÃO em Empréstimo). */
public class Copia {
    public enum Estado { DISPONIVEL, EMPRESTADA, RESERVADA, DANIFICADA }

    private Long id;
    private Obra obra;          // associação para a obra
    private String codigoTombo;
    private Estado estado;

    public Copia() {}

    public Copia(Long id, Obra obra, String codigoTombo, Estado estado) {
        this.id = id; this.obra = obra; this.codigoTombo = codigoTombo; this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Obra getObra() { return obra; }
    public void setObra(Obra obra) { this.obra = obra; }
    public String getCodigoTombo() { return codigoTombo; }
    public void setCodigoTombo(String codigoTombo) { this.codigoTombo = codigoTombo; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    @Override public String toString() {
        return codigoTombo + " — " + (obra != null ? obra.getTitulo() : "?") + " [" + estado + "]";
    }
}
