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

    public Consulta(LocalDate data, LocalTime hora, Medico medico, Paciente paciente, String motivo) {
        if (data == null || hora == null || medico == null || paciente == null) {
            throw new IllegalArgumentException("Data, hora, médico e paciente não podem ser nulos.");
        }
        this.data = data;
        this.hora = hora;
        this.medico = medico;
        this.paciente = paciente;
        this.motivo = motivo;
        this.historico = "Consulta ainda não realizada.";
        this.procedimentos = new ArrayList<>();
    }

    public void marcar() {
        System.out.println("Consulta marcada para " + this.paciente.getNome() + " às " + this.hora + " com Dr(a). " + this.medico.getNome());
    }

    public void cancelar() {
        System.out.println("Consulta do paciente " + this.paciente.getNome() + " às " + this.hora + " foi cancelada.");
    }

    public void realizar(String historicoDaConsulta) {
        if (historicoDaConsulta == null || historicoDaConsulta.trim().isEmpty()) {
            this.historico = "Realizada, mas sem anotações no histórico.";
        } else {
            this.historico = historicoDaConsulta;
        }
        System.out.println("Consulta realizada. Histórico atualizado.");
    }

    public void adicionarProcedimento(Procedimento procedimento) {
        if (procedimento != null) {
            this.procedimentos.add(procedimento);
            System.out.println("Procedimento do tipo '" + procedimento.getClass().getSimpleName() +
                    "' adicionado à consulta.");
        }
    }

    public void mostrar() {
        System.out.println("\n--- Dados da Consulta ---");
        System.out.println("Data: " + getData() + " | Hora: " + getHora());
        System.out.println("Médico: " + getMedico().getNome());
        System.out.println("Paciente: " + getPaciente().getNome());
        System.out.println("Motivo: " + getMotivo());
        System.out.println("Histórico: " + getHistorico());

        if (procedimentos.isEmpty()) {
            System.out.println("Nenhum procedimento associado a esta consulta.");
        } else {
            System.out.println("Procedimentos (" + procedimentos.size() + "):");
            for (Procedimento p : procedimentos) {
                System.out.println("  - [" + p.getClass().getSimpleName() + "]: " + p.getDescritivo());
            }
        }
        System.out.println("-------------------------");
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
    public List<Procedimento> getProcedimentos() { return procedimentos; }
}