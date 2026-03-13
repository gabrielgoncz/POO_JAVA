public class Main {
    public static void main(String[] args) {
        // 1. Testando Paciente (Construtor com parâmetros)
        Paciente p = new Paciente("Joao Silva", "123.456.789-00", "9999-0000", "Masculino", 30);

        // 2. Testando Medico (Construtor vazio + Setters)
        Medico m = new Medico();
        m.setNome("Dr. Carlos");
        m.setCrm("CRM12345");
        m.setTelefone("98888-1111");
        m.setEspecialidade("Cardiologia");
        m.setSenha("12345");

        // 3. Testando Agenda (Construtor com parâmetros puxando dados dos objetos acima)
        Agenda a = new Agenda("10/06/2026", "10:00", m.getNome(), p.getNome());

        // Executando o método mostrar de cada classe
        p.mostrar();
        m.mostrar();
        a.mostrar();
    }
}

class Paciente {
    private String nome, cpf, telefone, genero;
    private int idade;

    // Construtores
    public Paciente() {}
    public Paciente(String nome, String cpf, String telefone, String genero, int idade) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.genero = genero;
        this.idade = idade;
    }

    // Gets e Sets
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public void mostrar() {
        System.out.println("--- DADOS DO PACIENTE ---");
        System.out.println("Nome: " + nome + " | CPF: " + cpf + " | Idade: " + idade);
        System.out.println();
    }
}

class Medico {
    private String nome, crm, telefone, especialidade, senha;

    // Construtores
    public Medico() {}
    public Medico(String nome, String crm, String telefone, String especialidade, String senha) {
        this.nome = nome;
        this.crm = crm;
        this.telefone = telefone;
        this.especialidade = especialidade;
        this.senha = senha;
    }

    // Gets e Sets
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public void mostrar() {
        System.out.println("--- DADOS DO MÉDICO ---");
        System.out.println("Médico: " + nome + " | CRM: " + crm + " | Especialidade: " + especialidade);
        System.out.println();
    }
}

class Agenda {
    private String data, hora, medico, paciente;

    // Construtores
    public Agenda() {}
    public Agenda(String data, String hora, String medico, String paciente) {
        this.data = data;
        this.hora = hora;
        this.medico = medico;
        this.paciente = paciente;
    }

    // Gets e Sets
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
    public String getMedico() { return medico; }
    public void setMedico(String medico) { this.medico = medico; }
    public String getPaciente() { return paciente; }
    public void setPaciente(String paciente) { this.paciente = paciente; }

    public void mostrar() {
        System.out.println("--- DADOS DA AGENDA ---");
        System.out.println("Data: " + data + " | Hora: " + hora);
        System.out.println("Médico: " + medico + " | Paciente: " + paciente);
        System.out.println();
    }
}
