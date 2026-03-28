import java.util.Date;

public class Consulta {
    private Date data;
    private String hora;
    private Medico medico;
    private Paciente paciente;
    private String motivo;
    private String historico;

    public Consulta() {
        this.data = new Date();
        this.hora = "00:00";
        this.medico = new Medico();
        this.paciente = new Paciente();
        this.motivo = "Não especificado";
        this.historico = "Nenhum";
    }

    public Consulta(Date data, String hora, Medico medico, Paciente paciente, String motivo, String historico) {
        setData(data);
        setHora(hora);
        setMedico(medico);
        setPaciente(paciente);
        setMotivo(motivo);
        setHistorico(historico);
    }

    public Date getData() { return data; }
    public void setData(Date data) {
        try {
            if (data == null) throw new IllegalArgumentException();
            this.data = data;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos para data da consulta.");
            this.data = new Date();
        }
    }

    public String getHora() { return hora; }
    public void setHora(String hora) {
        try {
            if (hora == null || !hora.matches("\\d{2}:\\d{2}")) throw new IllegalArgumentException();
            this.hora = hora;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos para hora da consulta.");
            this.hora = "00:00";
        }
    }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) {
        try {
            if (medico == null) throw new IllegalArgumentException();
            this.medico = medico;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos para médico da consulta.");
            this.medico = new Medico();
        }
    }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) {
        try {
            if (paciente == null) throw new IllegalArgumentException();
            this.paciente = paciente;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos para paciente da consulta.");
            this.paciente = new Paciente();
        }
    }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) {
        try {
            if (motivo == null || motivo.trim().isEmpty()) throw new IllegalArgumentException();
            this.motivo = motivo;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos para motivo da consulta.");
            this.motivo = "Não especificado";
        }
    }

    public String getHistorico() { return historico; }
    public void setHistorico(String historico) {
        try {
            if (historico == null) throw new IllegalArgumentException();
            this.historico = historico;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos para histórico da consulta.");
            this.historico = "Nenhum";
        }
    }

    public void marcar() { System.out.println("Consulta marcada."); }
    public void cancelar() { System.out.println("Consulta cancelada."); }
    public void consultar() { System.out.println("Consultando dados da consulta."); }
    public void realizar() { System.out.println("Consulta realizada."); }
    public void atualizar() { System.out.println("Consulta atualizada."); }

    public void mostrar() {
        System.out.println("\n--- Dados da Consulta ---");
        System.out.println("Data: " + getData());
        System.out.println("Hora: " + getHora());
        System.out.println("Médico: " + getMedico().getNome());
        System.out.println("Paciente: " + getPaciente().getNome());
        System.out.println("Motivo: " + getMotivo());
        System.out.println("Histórico: " + getHistorico());
        System.out.println("-------------------------");
    }
}