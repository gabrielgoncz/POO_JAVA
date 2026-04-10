import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Medico extends Funcionario {
    private String crm;
    private String especialidade;
    private final List<Consulta> consultas;

    public Medico(String nome, String telefone, String senha, String crm, String especialidade) {
        super(nome, telefone, senha);
        this.setCrm(crm);
        this.setEspecialidade(especialidade);
        this.consultas = new ArrayList<>();
    }

    @Override
    protected void executarAcaoPosLogin() {
        System.out.println("Bem-vindo(a), Dr(a). " + getNome() + "! Acessando o portal médico com CRM " + this.crm + "...");
    }

    public String getCrm() { return crm; }
    public void setCrm(String crm) {
        if (crm == null || crm.trim().isEmpty()) {
            System.out.println("CRM inválido. Definido valor padrão.");
            this.crm = "000000/BR";
        } else {
            this.crm = crm;
        }
    }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.trim().isEmpty()) {
            System.out.println("Especialidade inválida. Definido valor padrão.");
            this.especialidade = "Clínico Geral";
        } else {
            this.especialidade = especialidade;
        }
    }

    @Override
    public void mostrar() {
        System.out.println("\n--- Dados do Médico ---");
        System.out.println("Nome: Dr(a). " + getNome());
        System.out.println("Telefone: " + getTelefone());
        System.out.println("CRM: " + getCrm());
        System.out.println("Especialidade: " + getEspecialidade());

        if (!consultas.isEmpty()) {
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
        return Collections.unmodifiableList(this.consultas);
    }
}