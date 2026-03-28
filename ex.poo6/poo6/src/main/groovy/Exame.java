import java.time.LocalDate;

public class Exame extends Procedimento {
    private final Consulta consulta;
    public Exame(Consulta consulta, LocalDate data, String descritivo) {
        super(data, descritivo);

        if (consulta == null) {
            throw new IllegalArgumentException("Erro: Exame deve estar obrigatoriamente associado a uma consulta.");
        }
        this.consulta = consulta;
    }

    public Consulta getConsulta() { return consulta; }

    public void solicitar() {
        System.out.println("Exame solicitado: " + this.descritivo);
    }

    @Override
    public void consultar() {
        System.out.println("\n--- Detalhes do Exame Solicitado ---");
        super.consultar();
        System.out.println("Paciente: " + getConsulta().getPaciente().getNome());
        System.out.println("Solicitado pelo(a) Dr(a): " + getConsulta().getMedico().getNome());
        System.out.println("------------------------------------");
    }
}