import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {

        System.out.println("###--- INICIALIZAÇÃO DO SISTEMA DA CLÍNICA ---###");

        System.out.println("\n--- 1. Cadastrando Atores ---");
        Paciente paciente1 = new Paciente("Carlos Silva", "111.222.333-44", "(11) 98765-4321", "Masculino", 34);
        Paciente paciente2 = new Paciente("Juliana Reis", "888.777.999-11", "(21) 91111-2222", "Feminino", 28);

        Medico medico = new Medico("Dra. Ana Costa", "123456/SP", "(11) 91234-5678", "Cardiologista", "senhaForte123");

        Recepcionista recepcionista = new Recepcionista("Mariana Lima", "444.555.666-77", "(11) 95555-4444", "recepcao@123");

        paciente1.mostrar();
        medico.mostrar();
        recepcionista.mostrar();

        System.out.println("\n--- 2. Abrindo a Agenda do Dia ---");
        Agenda agendaDeHoje = new Agenda(LocalDate.now());
        agendaDeHoje.consultar();

        System.out.println("\n--- 3. Recepcionista Realizando Agendamentos ---");
        Consulta consulta1 = recepcionista.agendarConsulta(agendaDeHoje, paciente1, medico, LocalDate.now(), LocalTime.of(10, 30), "Check-up anual");
        Consulta consulta2 = recepcionista.agendarConsulta(agendaDeHoje, paciente2, medico, LocalDate.now(), LocalTime.of(11, 0), "Dor no peito");

        System.out.println("\n--- 4. Verificando a Agenda e as Listas (Agregação) ---");
        agendaDeHoje.consultar();

        System.out.println("Consultas na lista do Dr(a). " + medico.getNome() + ": " + medico.getConsultas().size());
        System.out.println("Consultas na lista de " + paciente1.getNome() + ": " + paciente1.getConsultas().size());
        System.out.println("Consultas na lista de " + paciente2.getNome() + ": " + paciente2.getConsultas().size());

        System.out.println("\n--- 5. Médico Realizando a Consulta de Carlos Silva ---");
        consulta1.realizar("Paciente apresenta pressão arterial estável, sem queixas.");

        System.out.println("\n--- Criando Receita e Exame (Composição) ---");
        Receita receita1 = consulta1.prescreverReceita("Vitamina C 500mg, 1x ao dia por 30 dias.");
        Exame exame1 = consulta1.solicitarExame("Exame de sangue completo (hemograma).");

        System.out.println("\n--- 6. Verificando o Resultado Final da Consulta ---");
        consulta1.mostrar();
        receita1.mostrar();
        exame1.mostrar();

        System.out.println("\n###--- FLUXO FINALIZADO ---###");
    }
}