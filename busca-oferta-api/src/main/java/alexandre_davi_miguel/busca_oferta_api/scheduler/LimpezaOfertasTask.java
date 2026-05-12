package alexandre_davi_miguel.busca_oferta_api.scheduler;

import alexandre_davi_miguel.busca_oferta_api.service.PrecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LimpezaOfertasTask {

    private final PrecoService precoService;

    
    @Scheduled(cron = "0 0 0 * * *") 
    public void executarLimpezaDiaria() {
        System.out.println("Iniciando tarefa agendada: Removendo ofertas expiradas...");
        precoService.removerPrecosExpirados();
        System.out.println("Tarefa concluída com sucesso.");
    }
}