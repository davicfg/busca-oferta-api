package alexandre_davi_miguel.corretora_imoveis_api.strategy;

import org.springframework.stereotype.Component;

import alexandre_davi_miguel.busca_oferta_api.framework.PoliticaLimpezaStrategy;

@Component
public class LimpezaImoveisStrategy implements PoliticaLimpezaStrategy {

    @Override
    public void executarLimpeza() {
        System.out.println("Corretora de Imóveis [Limpeza]: Iniciando exclusão de imóveis vendidos ou com contratos expirados.");
        
        // Lógica de deleção / inativação no banco de dados.
        // Ex: imovelRepository.deleteVendidoOuExpirado();
        
        System.out.println("Corretora de Imóveis [Limpeza]: Limpeza concluída.");
    }
}
