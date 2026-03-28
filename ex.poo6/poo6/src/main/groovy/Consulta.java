import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Consulta {
    private LocalDate data;
    private LocalTime hora;
    private String motivo;
    private String historico;
    private Medico medico;
    private Paciente paciente;
    private List<Procedimento> procedimentos;

    public Consulta() {
        this.data = LocalDate.now();
        this.hora = LocalTime.now();
        this.medico = new Medico();
        this.paciente = new Paciente();
        this.motivo = "Não especificado";
        this.historico = "Nenhum";
        this.procedimentos = new ArrayList<>();
    }

    public Consulta(LocalDate data, LocalTime hora, Medico medico, Paciente paciente, String motivo) {
        this.data = data;
        this.hora = hora;
        this.medico = medico;
        this.paciente = paciente;
        this.motivo = motivo;
        this.historico = "Ainda não realizado";
        this.procedimentos = new ArrayList<>();

        if (medico != null) {
            medico.adicionarConsulta(this);
        }
        if (paciente != null) {
            paciente.adicionarConsulta(this);
        }
    }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getHistorico() { return historico; }

    public void adicionarReceita(String descritivo) {
        Receita novaReceita = new Receita(this, LocalDate.now(), descritivo);
        this.procedimentos.add(novaReceita);
        System.out.println("Receita adicionada para o paciente " + this.paciente.getNome());
        novaReceita.prescrever();
    }

    public void adicionarExame(String descritivo) {
        Exame novoExame = new Exame(this, LocalDate.now(), descritivo);
        this.procedimentos.add(novoExame);
        System.out.println("Exame solicitado para o paciente " + this.paciente.getNome());
        novoExame.solicitar();
    }

    public void marcar() { System.out.println("Consulta marcada para " + this.paciente.getNome() + " às " + this.hora); }
    public void cancelar() { System.out.println("Consulta do paciente " + this.paciente.getNome() + " cancelada."); }
    public void realizar(String historicoDaConsulta) { this.historico = historicoDaConsulta; }

    public void mostrar() {
        System.out.println("\n--- Dados da Consulta ---");
        System.out.println("Data: " + getData() + " | Hora: " + getHora());
        System.out.println("Médico: " + (medico != null ? getMedico().getNome() : "N/D"));
        System.out.println("Paciente: " + (paciente != null ? getPaciente().getNome() : "N/D"));
        System.out.println("Motivo: " + getMotivo());
        System.out.println("Histórico: " + getHistorico());

        if (!procedimentos.isEmpty()) {
            System.out.println("Procedimentos realizados (" + procedimentos.size() + "):");
            for (Procedimento p : procedimentos) {

                System.out.println("  - [" + p.getClass().getSimpleName() + "]: " + p.getDescritivo());
            }
        }
        System.out.println("-------------------------");
    }

    public List<Procedimento> getProcedimentos() {
        return procedimentos;
    }
}