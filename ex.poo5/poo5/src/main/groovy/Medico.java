import java.util.ArrayList;
import java.util.List;

public class Medico {
    private String nome;
    private String crm;
    private String telefone;
    private String especialidade;
    private String senha;

    private List<Consulta> consultas; //

    public Medico() {
        this.nome = "Não informado";
        this.crm = "000000/BR";
        this.telefone = "(00) 00000-0000";
        this.especialidade = "Clínico Geral";
        this.senha = "******";
        this.consultas = new ArrayList<>();
    }

    public Medico(String nome, String crm, String telefone, String especialidade, String senha) {
        setNome(nome);
        setCrm(crm);
        setTelefone(telefone);
        setEspecialidade(especialidade);
        setSenha(senha);
        this.consultas = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public void setNome(String nome) {
        try {
            if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException();
            this.nome = nome;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos");
            this.nome = "Não informado";
        }
    }
    public String getCrm() { return crm; }
    public void setCrm(String crm) {
        try {
            if (crm == null || crm.trim().isEmpty()) throw new IllegalArgumentException();
            this.crm = crm;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos");
            this.crm = "000000/BR";
        }
    }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) {
        try {
            if (telefone == null || telefone.trim().isEmpty()) throw new IllegalArgumentException();
            this.telefone = telefone;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos");
            this.telefone = "(00) 00000-0000";
        }
    }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) {
        try {
            if (especialidade == null || especialidade.trim().isEmpty()) throw new IllegalArgumentException();
            this.especialidade = especialidade;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos");
            this.especialidade = "Clínico Geral";
        }
    }
    public String getSenha() { return senha; }
    public void setSenha(String senha) {
        try {
            if (senha == null || senha.length() < 6) throw new IllegalArgumentException();
            this.senha = senha;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos");
            this.senha = "******";
        }
    }

    public boolean acessar(String senhaDigitada) {
        if (this.senha.equals(senhaDigitada)) {
            System.out.println("Dr(a). " + this.nome + " acessou o sistema.");
            return true;
        }
        System.out.println("Acesso negado. Senha incorreta.");
        return false;
    }

    public void mostrar() {
        System.out.println("\n--- Dados do Médico ---");
        System.out.println("Nome: " + getNome());
        System.out.println("CRM: " + getCrm());
        System.out.println("Telefone: " + getTelefone());
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