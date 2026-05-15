import java.time.LocalDate;

public class Exame extends Procedimento {

    public Exame(LocalDate data, String descritivo) {
        super(data, descritivo);
    }

    @Override
    public String gerarDocumento() {
        return "--- PEDIDO DE EXAME ---\n" +
                "Data: " + getData() + "\n" +
                "Exame Solicitado: " + getDescritivo();
    }
}