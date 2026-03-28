
import java.util.ArrayList;
import java.util.List;

public class Paciente {
    private String nome;
    private String cpf;
    private String telefone;
    private String genero;
    private int idade;


    private List<Consulta> consultas;

    public Paciente() {
        definirValoresPadroes();
        this.consultas = new ArrayList<>();
    }

    public Paciente(String nome, String cpf, String telefone, String genero, int idade) {
        setNome(nome);
        setCpf(cpf);
        setTelefone(telefone);
        setGenero(genero);
        setIdade(idade);
        this.consultas = new ArrayList<>();
    }

    private void definirValoresPadroes() {
        this.nome = "Não informado";
        this.cpf = "000.000.000-00";
        this.telefone = "(00) 00000-0000";
        this.genero = "Não especificado";
        this.idade = 0;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                throw new IllegalArgumentException("Nome inválido.");
            }
            this.nome = nome;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos");
            this.nome = "Não informado";
        }
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) {
        try {
            if (cpf == null || !cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
                throw new IllegalArgumentException("Formato de CPF inválido.");
            }
            this.cpf = cpf;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos");
            this.cpf = "000.000.000-00";
        }
    }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) {
        try {
            if (telefone == null || telefone.trim().isEmpty()) {
                throw new IllegalArgumentException("Telefone inválido.");
            }
            this.telefone = telefone;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos");
            this.telefone = "(00) 00000-0000";
        }
    }

    public String getGenero() { return genero; }
    public void setGenero(String genero) {
        try {
            if (genero == null || genero.trim().isEmpty()) {
                throw new IllegalArgumentException("Gênero inválido.");
            }
            this.genero = genero;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos");
            this.genero = "Não especificado";
        }
    }

    public int getIdade() { return idade; }
    public void setIdade(int idade) {
        try {
            if (idade < 0) {
                throw new IllegalArgumentException("Idade não pode ser negativa.");
            }
            this.idade = idade;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos");
            this.idade = 0;
        }
    }

    public void cadastrar() { System.out.println("Paciente " + this.nome + " cadastrado com sucesso."); }
    public void consultar() { System.out.println("Consultando dados do paciente " + this.nome + "..."); }

    public void mostrar() {
        System.out.println("\n--- Dados do Paciente ---");
        System.out.println("Nome: " + getNome());
        System.out.println("CPF: " + getCpf());
        System.out.println("Telefone: " + getTelefone());
        System.out.println("Gênero: " + getGenero());
        System.out.println("Idade: " + getIdade());
        System.out.println("-------------------------");
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