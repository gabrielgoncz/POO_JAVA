import java.time.LocalDate;

public class Receita extends Procedimento {
    private final Consulta consulta;
    public Receita(Consulta consulta, LocalDate data, String descritivo) {
        super(data, descritivo);

        if (consulta == null) {
            throw new IllegalArgumentException("Erro: Receita deve estar obrigatoriamente associada a uma consulta.");
        }
        this.consulta = consulta;
    }

    public Consulta getConsulta() { return consulta; }

    public void prescrever() {
        System.out.println("Receita prescrita para o paciente: " + this.consulta.getPaciente().getNome());
    }

    @Override
    public void consultar() {
        System.out.println("\n--- Detalhes da Receita ---");
        super.consultar();
        System.out.println("Paciente: " + getConsulta().getPaciente().getNome());
        System.out.println("Prescrito pelo(a) Dr(a): " + getConsulta().getMedico().getNome());
        System.out.println("---------------------------");
    }
}