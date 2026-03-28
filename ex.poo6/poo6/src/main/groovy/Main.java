import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("###--- SISTEMA DA CLÍNICA COM HERANÇA E POLIMORFISMO APLICADOO ---###");

        System.out.println("\n--- 1. Cadastrando Funcionários ---");
        Medico medico = new Medico("Dra. Ana Costa", "(11) 91234-5678", "senhaForte123", "123456/SP", "Cardiologista");
        Recepcionista recepcionista = new Recepcionista("Mariana Lima", "(11) 95555-4444", "recepcao@123", "444.555.666-77");

        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(medico);
        funcionarios.add(recepcionista);

        System.out.println("\n--- Exibindo todos os funcionários da clínica ---");
        for (Funcionario f : funcionarios) {
            f.mostrar();
        }
        System.out.println("\n--- 2. Iniciando Fluxo de Atendimento ---");
        Paciente paciente = recepcionista.cadastrarPaciente("Carlos Silva", "111.222.333-44", "(11) 98765-4321", "Masculino", 34);

        Agenda agendaDeHoje = new Agenda(LocalDate.now());

        Consulta consulta = recepcionista.marcarConsulta(agendaDeHoje, paciente, medico, LocalTime.of(14, 30), "Check-up anual");

        agendaDeHoje.consultar();

        System.out.println("\n--- 3. Realizando a Consulta ---");
        consulta.realizar("Paciente com quadro estável, sem queixas.");

        consulta.adicionarReceita("Vitamina D, 2000 UI, 1x ao dia.");
        consulta.adicionarExame("Hemograma completo e Colesterol total.");

        System.out.println("\n--- 4. Verificando Resultado Final da Consulta ---");
        consulta.mostrar();

        System.out.println("\n--- Detalhando os procedimentos da consulta ---");
        List<Procedimento> procedimentosDaConsulta = consulta.getProcedimentos();

        for (Procedimento p : procedimentosDaConsulta) {

            p.consultar();
        }
        System.out.println("\n###--- FLUXO FINALIZADO ---###");
    }
}