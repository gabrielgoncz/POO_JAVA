import java.time.LocalDate;

public abstract class Procedimento {
    private LocalDate data;
    private String descritivo;

    public Procedimento(LocalDate data, String descritivo) {
        this.data = data;
        this.descritivo = descritivo;
    }

    public abstract String gerarDocumento();

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescritivo() {
        return descritivo;
    }

    public void setDescritivo(String descritivo) {
        this.descritivo = descritivo;
    }
}