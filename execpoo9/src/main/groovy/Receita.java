import java.time.LocalDate;

public class Receita extends Procedimento {

    public Receita(LocalDate data, String descritivo) {
        super(data, descritivo);
    }

    @Override
    public String gerarDocumento() {
        return "--- RECEITA MÉDICA ---\n" +
                "Data: " + getData() + "\n" +
                "Medicamento(s): " + getDescritivo() + "\n" +
                "Uso: Conforme orientação médica.";
    }
}