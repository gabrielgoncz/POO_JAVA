import java.time.LocalDate;

public class Exame {
    private final Consulta consulta; // <-- MUDANÇA PRINCIPAL

    private LocalDate data;
    private String descritivo;

    public Exame(Consulta consulta, LocalDate data, String descritivo) {
        if (consulta == null) {
            throw new IllegalArgumentException("Erro: Exame deve estar obrigatoriamente associado a uma consulta.");
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
            System.out.println("Ocorreu uma exceção – Data atual definida como padrão para o exame.");
            this.data = LocalDate.now();
        }
    }

    public String getDescritivo() { return descritivo; }
    public void setDescritivo(String descritivo) {
        try {
            if (descritivo == null || descritivo.trim().isEmpty()) throw new IllegalArgumentException();
            this.descritivo = descritivo;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Descrição padrão definida para o exame.");
            this.descritivo = "Nenhum exame solicitado.";
        }
    }

    public void solicitar() {
        System.out.println("Exame solicitado: " + this.descritivo);
    }

    public void consultar() {
        this.mostrar();
    }

    public void mostrar() {
        System.out.println("\n--- Dados do Exame ---");
        System.out.println("Paciente: " + getConsulta().getPaciente().getNome());
        System.out.println("Solicitado pelo(a) Dr(a): " + getConsulta().getMedico().getNome());
        System.out.println("Data da Solicitação: " + getData());
        System.out.println("Descritivo: " + getDescritivo());
        System.out.println("----------------------");
    }
}