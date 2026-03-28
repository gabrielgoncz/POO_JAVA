import java.time.LocalDate;
import java.time.LocalTime;

public class Recepcionista extends Funcionario {
    private String cpf;

    public Recepcionista() {
        super("Não informado", "(00) 00000-0000", "******");
        this.cpf = "000.000.000-00";
    }

    public Recepcionista(String nome, String telefone, String senha, String cpf) {
        super(nome, telefone, senha);
        setCpf(cpf);
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) {
        try {
            if (cpf == null || !cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) throw new IllegalArgumentException();
            this.cpf = cpf;
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção – Valores padrões definidos para CPF.");
            this.cpf = "000.000.000-00";
        }
    }

    public Paciente cadastrarPaciente(String nome, String cpf, String telefone, String genero, int idade) {
        System.out.println("Recepcionista " + this.nome + " cadastrando novo paciente...");
        Paciente novoPaciente = new Paciente(nome, cpf, telefone, genero, idade);
        novoPaciente.cadastrar();
        return novoPaciente;
    }

    public Consulta marcarConsulta(Agenda agenda, Paciente paciente, Medico medico, LocalTime hora, String motivo) {
        System.out.println("\nRecepcionista " + this.nome + " está marcando uma nova consulta...");
        Consulta novaConsulta = new Consulta(agenda.getData(), hora, medico, paciente, motivo);
        agenda.adicionarConsulta(novaConsulta);
        return novaConsulta;
    }

    @Override
    public void mostrar() {
        System.out.println("\n--- Dados da Recepcionista ---");
        System.out.println("Nome: " + getNome());
        System.out.println("Telefone: " + getTelefone());
        System.out.println("CPF: " + getCpf());
        System.out.println("------------------------------");
    }
}