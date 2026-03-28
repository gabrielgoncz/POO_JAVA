// Importações necessárias para as associações e a nova API de Data/Hora
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

    private List<Receita> receitas;
    private List<Exame> exames;

    public Consulta() {
        this.data = LocalDate.now();
        this.hora = LocalTime.now();
        this.medico = new Medico(); // Objeto padrão
        this.paciente = new Paciente(); // Objeto padrão
        this.motivo = "Não especificado";
        this.historico = "Nenhum";
        this.receitas = new ArrayList<>();
        this.exames = new ArrayList<>();
    }

    public Consulta(LocalDate data, LocalTime hora, Medico medico, Paciente paciente, String motivo) {
        setData(data);
        setHora(hora);
        setMedico(medico);
        setPaciente(paciente);
        setMotivo(motivo);
        this.historico = "Ainda não realizado";

        this.receitas = new ArrayList<>();
        this.exames = new ArrayList<>();

        if (medico != null) {
            medico.adicionarConsulta(this);
        }
        if (paciente != null) {
            paciente.adicionarConsulta(this);
        }
    }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) {
        try {
            if (data == null) throw new IllegalArgumentException();
            this.data = data;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Data atual definida como padrão.");
            this.data = LocalDate.now();
        }
    }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) {
        try {
            if (hora == null) throw new IllegalArgumentException();
            this.hora = hora;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Hora atual definida como padrão.");
            this.hora = LocalTime.now();
        }
    }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) {
        try {
            if (medico == null) throw new IllegalArgumentException();
            this.medico = medico;
        } catch (Exception e) {
            this.medico = new Medico();
        }
    }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) {
        try {
            if (paciente == null) throw new IllegalArgumentException();
            this.paciente = paciente;
        } catch (Exception e) {
            this.paciente = new Paciente();
        }
    }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getHistorico() { return historico; }
    public void setHistorico(String historico) { this.historico = historico; }

    public Receita prescreverReceita(String descritivo) {
        Receita novaReceita = new Receita(this, LocalDate.now(), descritivo);
        this.receitas.add(novaReceita);
        System.out.println("Receita adicionada para o paciente " + this.paciente.getNome());
        return novaReceita;
    }

    public Exame solicitarExame(String descritivo) {
        Exame novoExame = new Exame(this, LocalDate.now(), descritivo);
        this.exames.add(novoExame);
        System.out.println("Exame solicitado para o paciente " + this.paciente.getNome());
        return novoExame;
    }

    public void marcar() { System.out.println("Consulta marcada para " + this.paciente.getNome() + " às " + this.hora); }
    public void cancelar() { System.out.println("Consulta do paciente " + this.paciente.getNome() + " cancelada."); }

    public void realizar(String historicoDaConsulta) {
        this.setHistorico(historicoDaConsulta);
        System.out.println("Consulta realizada. Histórico atualizado.");
    }

    public void atualizar() { System.out.println("Dados da consulta atualizados."); }
    public void consultar() { this.mostrar(); }

    public void mostrar() {
        System.out.println("\n--- Dados da Consulta ---");
        System.out.println("Data: " + getData());
        System.out.println("Hora: " + getHora());
        System.out.println("Médico: " + (medico != null ? getMedico().getNome() : "N/D"));
        System.out.println("Paciente: " + (paciente != null ? getPaciente().getNome() : "N/D"));
        System.out.println("Motivo: " + getMotivo());
        System.out.println("Histórico: " + getHistorico());

        if (!receitas.isEmpty()) {
            System.out.println("Receitas (" + receitas.size() + "):");
            for(Receita r : receitas) {
                System.out.println("  - " + r.getDescritivo());
            }
        }

        if (!exames.isEmpty()) {
            System.out.println("Exames Solicitados (" + exames.size() + "):");
            for(Exame e : exames) {
                System.out.println("  - " + e.getDescritivo());
            }
        }
        System.out.println("-------------------------");
    }

    public List<Receita> getReceitas() { return receitas; }
    public List<Exame> getExames() { return exames; }
}