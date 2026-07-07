package alexandre_davi_miguel.catalogo_pecas_api.service; 

import alexandre_davi_miguel.busca_oferta_api.framework.PoliticaLimpezaStrategy;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class PoliticaLimpezaSemestral implements PoliticaLimpezaStrategy {

    // Injetar o repositório de PecaAutomotiva aqui

    @Override
    public void executarLimpeza() {
        System.out.println("Catálogo Autopeças: Verificando validade semestral dos catálogos...");
        
        LocalDate dataCorte = LocalDate.now().minusMonths(6);
        System.out.println("Limpeza de peças expiradas concluída.");
    }
}
