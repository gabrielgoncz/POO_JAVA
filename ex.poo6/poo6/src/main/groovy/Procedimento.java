import java.time.LocalDate;

public abstract class Procedimento {
    protected LocalDate data;
    protected String descritivo;
    public Procedimento(LocalDate data, String descritivo) {
        this.data = data;
        this.descritivo = descritivo;
    }

    public void consultar() {
        System.out.println("Data do Procedimento: " + this.data);
        System.out.println("Descrição: " + this.descritivo);
    }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public String getDescritivo() { return descritivo; }
    public void setDescritivo(String descritivo) { this.descritivo = descritivo; }
}