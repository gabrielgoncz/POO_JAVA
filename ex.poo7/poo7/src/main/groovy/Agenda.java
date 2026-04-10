import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private LocalDate data;
    private List<Consulta> consultasDoDia;

    public Agenda(LocalDate data) {
        this.data = data;
        this.consultasDoDia = new ArrayList<>();
    }

    public void adicionarConsulta(Consulta novaConsulta) {
        if (novaConsulta == null) {
            System.out.println("Erro: Não é possível adicionar uma consulta nula.");
            return;
        }

        if (!novaConsulta.getData().equals(this.data)) {
            System.out.println("Atenção: A consulta (data " + novaConsulta.getData() +
                    ") não pertence à data desta agenda (" + this.data + "). Mesmo assim, foi adicionada.");
        }

        this.consultasDoDia.add(novaConsulta);
        System.out.println("Consulta para o paciente " + novaConsulta.getPaciente().getNome() +
                " adicionada na agenda do dia " + this.data);
    }

    public void consultar() {
        System.out.println("\n--- Consultando Agenda para o dia: " + getData() + " ---");
        if (consultasDoDia.isEmpty()) {
            System.out.println("Nenhuma consulta marcada para esta data.");
        } else {
            System.out.println("Total de " + consultasDoDia.size() + " consulta(s) agendada(s):");
            for (Consulta c : consultasDoDia) {
                System.out.println(" - Hora: " + c.getHora() + " | Paciente: " +
                        c.getPaciente().getNome() + " | Médico: " + c.getMedico().getNome());
            }
        }
        System.out.println("-------------------------------------------");
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        if (data != null) {
            this.data = data;
        }
    }

    public List<Consulta> getConsultasDoDia() {
        return consultasDoDia;
    }
}