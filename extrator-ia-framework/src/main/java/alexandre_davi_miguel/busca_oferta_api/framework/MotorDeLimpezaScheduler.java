package alexandre_davi_miguel.busca_oferta_api.framework;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MotorDeLimpezaScheduler {

    // O Spring Boot vai injetar dinamicamente todas as implementações que encontrar
    private final List<PoliticaLimpezaStrategy> politicasDeLimpeza;

    @Scheduled(cron = "0 0 0 * * *") // O relógio pertence ao framework (Ponto Fixo)
    public void executarLimpezaDiaria() {
        System.out.println("Framework: Iniciando tarefas agendadas de limpeza...");
        
        // O framework roda a limpeza sem saber se está apagando Produto, Peça ou Imóvel
        for (PoliticaLimpezaStrategy politica : politicasDeLimpeza) {
            politica.executarLimpeza(); 
        }
        
        System.out.println("Framework: Tarefas concluídas.");
    }
}