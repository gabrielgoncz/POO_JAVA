import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Paciente {
    private String nome;
    private String cpf;
    private String telefone;
    private String genero;
    private int idade;
    private final List<Consulta> consultas;

    public Paciente(String nome, String cpf, String telefone, String genero, int idade) {
        this.setNome(nome);
        this.setCpf(cpf);
        this.setTelefone(telefone);
        this.setGenero(genero);
        this.setIdade(idade);
        this.consultas = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Nome inválido. Valor padrão 'Não informado' será usado.");
            this.nome = "Não informado";
        } else {
            this.nome = nome;
        }
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            System.out.println("Formato de CPF inválido. Valor padrão '000...' será usado.");
            this.cpf = "000.000.000-00";
        } else {
            this.cpf = cpf;
        }
    }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            System.out.println("Telefone inválido. Valor padrão será usado.");
            this.telefone = "(00) 00000-0000";
        } else {
            this.telefone = telefone;
        }
    }

    public String getGenero() { return genero; }
    public void setGenero(String genero) {
        if (genero == null || genero.trim().isEmpty()) {
            System.out.println("Gênero inválido. Valor padrão será usado.");
            this.genero = "Não especificado";
        } else {
            this.genero = genero;
        }
    }

    public int getIdade() { return idade; }
    public void setIdade(int idade) {
        if (idade < 0) {
            System.out.println("Idade não pode ser negativa. Valor padrão '0' será usado.");
            this.idade = 0;
        } else {
            this.idade = idade;
        }
    }

    public void cadastrar() {
        System.out.println("Paciente " + this.nome + " cadastrado com sucesso.");
    }

    public void mostrar() {
        System.out.println("\n--- Dados do Paciente ---");
        System.out.println("Nome: " + getNome());
        System.out.println("CPF: " + getCpf());
        System.out.println("Telefone: " + getTelefone());
        System.out.println("Gênero: " + getGenero());
        System.out.println("Idade: " + getIdade());
        if (!consultas.isEmpty()) {
            System.out.println("Número de consultas no histórico: " + consultas.size());
        }
        System.out.println("-------------------------");
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