package main.java.alexandre_davi_miguel.catalogo_pecas_api.service; 

import alexandre_davi_miguel.busca_oferta_api.framework.ConfiguracaoExtracaoIA;
import alexandre_davi_miguel.busca_oferta_api.instanciacao2.dto.PecaExtraidaDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConfiguracaoExtracaoCatalogo implements ConfiguracaoExtracaoIA<PecaExtraidaDTO> {

    @Override
    public List<PecaExtraidaDTO> processarLotePendente() throws Exception {
        String prompt = "Analise o seguinte catálogo técnico de fabricante ou tabela de preços. " +
                        "Extraia os dados no formato JSON contendo os seguintes campos obrigatórios: " +
                        "codigo da peça, nome, veículos compatíveis e preço de custo.";
        
        // Lógica de chamada ao ChatClient (Gemini/ChatGPT) utilizando o prompt acima
        // e convertendo a string JSON de resposta em List<PecaExtraidaDTO>
        return null; 
    }
}
