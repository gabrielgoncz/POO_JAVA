import java.time.LocalDate;
import java.time.LocalTime;

public class Recepcionista {
    private String nome;
    private String cpf;
    private String telefone;
    private String senha;

    public Recepcionista() {
        this.nome = "Não informado";
        this.cpf = "000.000.000-00";
        this.telefone = "(00) 00000-0000";
        this.senha = "******";
    }

    public Recepcionista(String nome, String cpf, String telefone, String senha) {
        setNome(nome);
        setCpf(cpf);
        setTelefone(telefone);
        setSenha(senha);
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
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) {
        try {
            if (cpf == null || !cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) throw new IllegalArgumentException();
            this.cpf = cpf;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos");
            this.cpf = "000.000.000-00";
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
            System.out.println("Recepcionista " + this.nome + " acessou o sistema.");
            return true;
        }
        System.out.println("Acesso negado. Senha incorreta.");
        return false;
    }

    public Consulta agendarConsulta(Agenda agenda, Paciente paciente, Medico medico, LocalDate data, LocalTime hora, String motivo) {
        System.out.println("\nRecepcionista " + this.nome + " está agendando uma nova consulta...");
        Consulta novaConsulta = new Consulta(data, hora, medico, paciente, motivo);
        agenda.adicionarConsulta(novaConsulta);
        novaConsulta.marcar();
        return novaConsulta;
    }

    public void mostrar() {
        System.out.println("\n--- Dados do Recepcionista ---");
        System.out.println("Nome: " + getNome());
        System.out.println("CPF: " + getCpf());
        System.out.println("Telefone: " + getTelefone());
        System.out.println("------------------------------");
    }
}