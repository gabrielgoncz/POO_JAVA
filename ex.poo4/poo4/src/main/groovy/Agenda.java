import java.util.Date;

public class Agenda {
    private Date data;
    private String hora;
    private Medico medico;
    private Paciente paciente;

    public Agenda() {
        this.data = new Date(); // Data atual como padrão
        this.hora = "00:00";
        this.medico = new Medico(); // Objeto padrão
        this.paciente = new Paciente(); // Objeto padrão
    }

    public Agenda(Date data, String hora, Medico medico, Paciente paciente) {
        setData(data);
        setHora(hora);
        setMedico(medico);
        setPaciente(paciente);
    }

    public Date getData() { return data; }
    public void setData(Date data) {
        try {
            if (data == null) throw new IllegalArgumentException();
            this.data = data;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos para data da agenda.");
            this.data = new Date();
        }
    }

    public String getHora() { return hora; }
    public void setHora(String hora) {
        try {
            if (hora == null || !hora.matches("\\d{2}:\\d{2}")) throw new IllegalArgumentException();
            this.hora = hora;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos para hora da agenda.");
            this.hora = "00:00";
        }
    }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) {
        try {
            if (medico == null) throw new IllegalArgumentException();
            this.medico = medico;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos para médico da agenda.");
            this.medico = new Medico();
        }
    }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) {
        try {
            if (paciente == null) throw new IllegalArgumentException();
            this.paciente = paciente;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos para paciente da agenda.");
            this.paciente = new Paciente();
        }
    }

    public void consultar() { System.out.println("Consultando agenda..."); }

    public void mostrar() {
        System.out.println("\n--- Dados da Agenda ---");
        System.out.println("Data: " + getData());
        System.out.println("Hora: " + getHora());
        System.out.println("Médico: " + getMedico().getNome());
        System.out.println("Paciente: " + getPaciente().getNome());
        System.out.println("-----------------------");
    }
}