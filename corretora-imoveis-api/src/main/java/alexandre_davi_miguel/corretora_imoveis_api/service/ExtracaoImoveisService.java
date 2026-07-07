package alexandre_davi_miguel.corretora_imoveis_api.service;

import org.springframework.stereotype.Service;

import alexandre_davi_miguel.corretora_imoveis_api.dto.ImovelExtraidoDTO;
import alexandre_davi_miguel.busca_oferta_api.framework.ConfiguracaoExtracaoIA;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExtracaoImoveisService implements ConfiguracaoExtracaoIA<ImovelExtraidoDTO> {

    public String obterPromptEsquema() {
        return "Analise os documentos em anexo sobre imóveis disponíveis. Extraia os dados no formato JSON contendo rigorosamente as seguintes chaves: codigoReferencia (String), titulo (String), tipo (String, ex: Apartamento, Casa, Lote), preco (Número Decimal) e caracteristicas (Array de Strings). Ignore imóveis sem preço.";
    }

    @Override
    public List<ImovelExtraidoDTO> processarLotePendente() throws Exception {
        System.out.println("Corretora de Imóveis: Enviando portfólio para a IA analisar...");
        
        // Simulação da chamada ao ChatClient
        
        List<ImovelExtraidoDTO> loteExtraido = new ArrayList<>();
        return loteExtraido;
    }
}
