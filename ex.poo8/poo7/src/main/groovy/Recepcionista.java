import java.time.LocalDate;
import java.time.LocalTime;

public class Recepcionista extends Funcionario {
    private String cpf;

    public Recepcionista(String nome, String telefone, String senha, String cpf) {
        super(nome, telefone, senha);
        this.setCpf(cpf);
    }

    @Override
    protected void executarAcaoPosLogin() {
        System.out.println("Bem-vindo(a), " + getNome() + "! Acessando o sistema de agendamento e cadastro...");
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

    public Paciente cadastrarPaciente(String nome, String cpf, String telefone, String genero, int idade) {
        System.out.println("\nRecepcionista " + this.getNome() + " cadastrando novo paciente...");
        Paciente novoPaciente = new Paciente(nome, cpf, telefone, genero, idade);
        novoPaciente.cadastrar();
        return novoPaciente;
    }

    public Consulta marcarConsulta(Agenda agenda, Paciente paciente, Medico medico, LocalTime hora, String motivo) {
        System.out.println("\nRecepcionista " + this.getNome() + " está marcando uma nova consulta...");

        if (agenda.getData().isBefore(LocalDate.now())) {
            System.out.println("ERRO: Não é possível agendar consultas em datas passadas.");
            return null;
        }

        Consulta novaConsulta = new Consulta(agenda.getData(), hora, medico, paciente, motivo);
        agenda.adicionarConsulta(novaConsulta);
        novaConsulta.marcar();
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