package alexandre_davi_miguel.busca_oferta_api.scheduler;

import alexandre_davi_miguel.busca_oferta_api.framework.PoliticaLimpezaStrategy;
import alexandre_davi_miguel.busca_oferta_api.service.PrecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LimpezaOfertasTask implements PoliticaLimpezaStrategy {

    private final PrecoService precoService; 

    @Override
    public void executarLimpeza() {
        System.out.println("BuscaOfertas: Removendo ofertas expiradas...");
        
        precoService.removerPrecosExpirados(); 
    }
}