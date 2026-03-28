import java.util.Date;

public class Receita {
    private Consulta consulta;
    private Date data;
    private String descritivo;

    public Receita() {
        this.consulta = new Consulta();
        this.data = new Date();
        this.descritivo = "Nenhuma prescrição.";
    }

    public Receita(Consulta consulta, Date data, String descritivo) {
        setConsulta(consulta);
        setData(data);
        setDescritivo(descritivo);
    }

    public Consulta getConsulta() { return consulta; }
    public void setConsulta(Consulta consulta) {
        try {
            if (consulta == null) throw new IllegalArgumentException();
            this.consulta = consulta;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos para consulta da receita.");
            this.consulta = new Consulta();
        }
    }

    public Date getData() { return data; }
    public void setData(Date data) {
        try {
            if (data == null) throw new IllegalArgumentException();
            this.data = data;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos para data da receita.");
            this.data = new Date();
        }
    }

    public String getDescritivo() { return descritivo; }
    public void setDescritivo(String descritivo) {
        try {
            if (descritivo == null || descritivo.trim().isEmpty()) throw new IllegalArgumentException();
            this.descritivo = descritivo;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos para descritivo da receita.");
            this.descritivo = "Nenhuma prescrição.";
        }
    }

    public void prescrever() { System.out.println("Receita prescrita."); }
    public void consultar() { System.out.println("Consultando receita."); }

    public void mostrar() {
        System.out.println("\n--- Dados da Receita ---");
        System.out.println("Paciente da Consulta: " + getConsulta().getPaciente().getNome());
        System.out.println("Data: " + getData());
        System.out.println("Descritivo: " + getDescritivo());
        System.out.println("------------------------");
    }
}