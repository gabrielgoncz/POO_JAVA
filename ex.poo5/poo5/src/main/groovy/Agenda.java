import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Agenda {

    private LocalDate data;
    private List<Consulta> consultasDoDia;

    public Agenda() {
        this.data = LocalDate.now(); // Data atual como padrão
        this.consultasDoDia = new ArrayList<>();
    }

    public Agenda(LocalDate data) {
        setData(data);
        this.consultasDoDia = new ArrayList<>();
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        try {
            if (data == null) {
                throw new IllegalArgumentException("A data não pode ser nula.");
            }
            this.data = data;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Data atual definida como padrão.");
            this.data = LocalDate.now();
        }
    }

    public List<Consulta> getConsultasDoDia() {
        return consultasDoDia;
    }

    public void adicionarConsulta(Consulta novaConsulta) {
        try {
            if (novaConsulta == null) {
                throw new NullPointerException("Não é possível adicionar uma consulta nula.");
            }
            if (!novaConsulta.getData().equals(this.data)) {
                System.out.println("Atenção: A consulta não pertence a data dessa agenda.");
            }
            this.consultasDoDia.add(novaConsulta);
            System.out.println("Consulta com o paciente " + novaConsulta.getPaciente().getNome() + " adicionada a agenda do dia " + this.data);
        } catch (Exception e) {
            System.out.println("Erro ao adicionar consulta: " + e.getMessage());
        }
    }

    public void consultar() {
        System.out.println("\n--- Consultando Agenda para o dia: " + getData() + " ---");
        if (consultasDoDia.isEmpty()) {
            System.out.println("Nenhuma consulta marcada para esta data.");
        } else {
            System.out.println("Total de " + consultasDoDia.size() + " consulta(s) agendada(s):");
            for (Consulta c : consultasDoDia) {
                System.out.println(" - Hora: " + c.getHora() + " | Paciente: " + c.getPaciente().getNome() + " | Médico: " + c.getMedico().getNome());
            }
        }
        System.out.println("-------------------------------------------");
    }
}