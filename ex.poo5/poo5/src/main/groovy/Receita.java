import java.time.LocalDate;

public class Receita {
    private final Consulta consulta;

    private LocalDate data;
    private String descritivo;

    public Receita(Consulta consulta, LocalDate data, String descritivo) {
        if (consulta == null) {
            throw new IllegalArgumentException("Erro: Receita deve estar obrigatoriamente associada a uma consulta.");
        }
        this.consulta = consulta;
        setData(data);
        setDescritivo(descritivo);
    }

    public Consulta getConsulta() { return consulta; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) {
        try {
            if (data == null) throw new IllegalArgumentException();
            this.data = data;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Data atual definida como padrão para a receita.");
            this.data = LocalDate.now();
        }
    }

    public String getDescritivo() { return descritivo; }
    public void setDescritivo(String descritivo) {
        try {
            if (descritivo == null || descritivo.trim().isEmpty()) throw new IllegalArgumentException();
            this.descritivo = descritivo;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Descrição padrão definida para a receita.");
            this.descritivo = "Nenhuma prescrição.";
        }
    }

    public void prescrever() {
        System.out.println("Receita prescrita para o paciente: " + this.consulta.getPaciente().getNome());
    }

    public void consultar() {
        this.mostrar();
    }

    public void mostrar() {
        System.out.println("\n--- Dados da Receita ---");
        System.out.println("Paciente: " + getConsulta().getPaciente().getNome());
        System.out.println("Prescrito pelo(a) Dr(a): " + getConsulta().getMedico().getNome());
        System.out.println("Data da Prescrição: " + getData());
        System.out.println("Descritivo: " + getDescritivo());
        System.out.println("------------------------");
    }
}