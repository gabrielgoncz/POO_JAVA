import java.util.Date;

public class Main {
    public static void main(String[] args) {
        System.out.println("###-- CRIANDO OBJETOS COM DADOS VÁLIDOS --###");

        Paciente pacienteValido = new Paciente("Carlos Silva", "111.222.333-44", "(11) 98765-4321", "Masculino", 34);
        Medico medicoValido = new Medico("Dra. Ana Costa", "123456/SP", "(11) 91234-5678", "Cardiologista", "senhaForte123");
        Recepcionista recepcionistaValida = new Recepcionista("Mariana Lima", "444.555.666-77", "(11) 95555-4444", "recepcao@123");

        pacienteValido.mostrar();
        medicoValido.mostrar();
        recepcionistaValida.mostrar();

        Consulta consultaValida = new Consulta(new Date(), "14:30", medicoValido, pacienteValido, "Check-up anual", "Paciente saudável.");
        consultaValida.mostrar();

        System.out.println("\n\n###--- CRIANDO OBJETOS COM DADOS INVÁLIDOS PARA TESTAR EXCEÇÕES ---###");

        Paciente pacienteInvalido = new Paciente("Lucas", "12345", "", "Masculino", -5);
        pacienteInvalido.mostrar(); // O objeto será criado com os valores padrão para os campos inválidos.
    }
}