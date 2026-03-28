import java.util.ArrayList;
import java.util.List;

public class Medico extends Funcionario {
    private String crm;
    private String especialidade;
    private List<Consulta> consultas;

    public Medico() {
        super("Não informado", "(00) 00000-0000", "******");
        this.crm = "000000/BR";
        this.especialidade = "Clínico Geral";
        this.consultas = new ArrayList<>();
    }

    public Medico(String nome, String telefone, String senha, String crm, String especialidade) {
        super(nome, telefone, senha);
        setCrm(crm);
        setEspecialidade(especialidade);
        this.consultas = new ArrayList<>();
    }

    public String getCrm() { return crm; }
    public void setCrm(String crm) {
        try {
            if (crm == null || crm.trim().isEmpty()) throw new IllegalArgumentException();
            this.crm = crm;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos para CRM.");
            this.crm = "000000/BR";
        }
    }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) {
        try {
            if (especialidade == null || especialidade.trim().isEmpty()) throw new IllegalArgumentException();
            this.especialidade = especialidade;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos para Especialidade.");
            this.especialidade = "Clínico Geral";
        }
    }

    @Override
    public void mostrar() {
        System.out.println("\n--- Dados do Médico ---");
        System.out.println("Nome: " + getNome());
        System.out.println("Telefone: " + getTelefone());
        System.out.println("CRM: " + getCrm());
        System.out.println("Especialidade: " + getEspecialidade());

        if (consultas != null && !consultas.isEmpty()) {
            System.out.println("Número de consultas associadas: " + consultas.size());
        }
        System.out.println("-----------------------");
    }

    public void adicionarConsulta(Consulta consulta) {
        if (consulta != null && !this.consultas.contains(consulta)) {
            this.consultas.add(consulta);
        }
    }

    public List<Consulta> getConsultas() {
        return this.consultas;
    }
}