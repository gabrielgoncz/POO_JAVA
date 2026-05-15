import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("###--- SISTEMA DA CLÍNICA COM INTERFACES E POLIMORFISMO ---###");
        System.out.println("Data de hoje: " + LocalDate.now());

        // 1. CONTRATANDO FUNCIONÁRIOS
        Recepcionista recepcionista = new Recepcionista("Carlos Pereira", "5555-4444", "senha_rec", "987.654.321-11");
        Medico medico = new Medico("Dra. Ana Souza", "9999-8888", "senha_med", "12345-SP", "Cardiologia");

        // 2. USANDO A INTERFACE IDisplay
        System.out.println("\n--- 1. Exibindo todos os cadastros (com IDisplay) ---");
        List<IDisplay> cadastros = new ArrayList<>();
        cadastros.add(medico);
        cadastros.add(recepcionista);
        
        // 3. RECEPCIONISTA EM AÇÃO
        System.out.println("\n--- 2. Recepcionista inicia o atendimento ---");
        recepcionista.acessar("senha_rec"); // Login
        Paciente paciente = recepcionista.cadastrarPaciente(
            "João da Silva", "123.456.789-00", "8888-7777", "Masculino", 45
        );
        
        cadastros.add(paciente); // Paciente também é IDisplay!

        // 4. AGENDAMENTO
        Agenda agendaDeAmanha = new Agenda(LocalDate.now().plusDays(1));
        Consulta consulta = recepcionista.marcarConsulta(
            agendaDeAmanha, paciente, medico, LocalTime.of(10, 0), "Avaliação cardíaca"
        );
        
        agendaDeAmanha.consultar();

        // 5. NO DIA DA CONSULTA
        System.out.println("\n--- 3. Médico realiza a consulta ---");
        medico.acessar("senha_med"); // Login
        consulta.realizar("Paciente reporta cansaço. Pressão arterial 13/8. Exames solicitados.");

        Exame eletrocardiograma = new Exame(consulta.getData(), "Eletrocardiograma (ECG)");
        Receita medContinuo = new Receita(consulta.getData(), "AAS 100mg (uso contínuo)");

        consulta.adicionarProcedimento(eletrocardiograma);
        consulta.adicionarProcedimento(medContinuo);

        // 6. IMPRIMINDO TODOS OS DOCUMENTOS (com IGeradorDocumento)
        System.out.println("\n--- 4. Gerando todos os documentos da consulta (com IGeradorDocumento) ---");
        
        List<IGeradorDocumento> documentos = new ArrayList<>();
        documentos.add(consulta); // A própria consulta é um documento (Resumo)
        documentos.add(eletrocardiograma); // O exame
        documentos.add(medContinuo); // A receita

        for (IGeradorDocumento doc : documentos) {
            System.out.println(doc.gerarDocumento());
            System.out.println("-------------------------------------");
        }
        
        System.out.println("\n--- Exibindo todos os cadastros do sistema ---");
        for (IDisplay item : cadastros) {
            item.mostrar();
        }
        
        System.out.println("\n###--- FLUXO FINALIZADO ---###");
    }
}