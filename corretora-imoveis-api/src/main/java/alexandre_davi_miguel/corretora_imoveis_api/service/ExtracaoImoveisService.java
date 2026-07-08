package alexandre_davi_miguel.corretora_imoveis_api.service;

import alexandre_davi_miguel.busca_oferta_api.framework.PipelineExtracaoGenerico;
import alexandre_davi_miguel.busca_oferta_api.framework.FonteDeDocumentos;
import alexandre_davi_miguel.corretora_imoveis_api.dto.ImovelExtraidoDTO;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ExtracaoImoveisService extends PipelineExtracaoGenerico<ImovelExtraidoDTO[]> {

    private final FontePlantasImoveis fontePlantasImoveis;

    public ExtracaoImoveisService(FontePlantasImoveis fontePlantasImoveis) {
        this.fontePlantasImoveis = fontePlantasImoveis;
    }

    // MÉTODO QUE A INSTÂNCIA CHAMA PARA INICIAR TUDO
    public List<ImovelExtraidoDTO> processarLotePendente() throws Exception {
        System.out.println("Corretora de Imóveis: Solicitando ao framework a análise do portfólio...");
        
        // 1. O Framework assume o controlo, lê os PDFs e faz a chamada à IA
        Map<Path, ImovelExtraidoDTO[]> resultadosBrutos = super.executarPipelineExtracao();
        
        // 2. A Instância recupera o controlo apenas para formatar os dados finais
        List<ImovelExtraidoDTO> todosOsImoveis = new ArrayList<>();
        
        for (Map.Entry<Path, ImovelExtraidoDTO[]> entry : resultadosBrutos.entrySet()) {
            ImovelExtraidoDTO[] imoveisDesteArquivo = entry.getValue();
            if (imoveisDesteArquivo != null) {
                todosOsImoveis.addAll(Arrays.asList(imoveisDesteArquivo));
            }
        }
        
        return todosOsImoveis;
    }

    // --- PONTOS FLEXÍVEIS EXIGIDOS PELO NÚCLEO ---

    @Override
    protected FonteDeDocumentos getFonteDeDocumentos() {
        return this.fontePlantasImoveis;
    }

    @Override
    protected String getPromptInstrucao() {
        return "Analise os documentos em anexo sobre imóveis disponíveis. " +
               "Extraia os dados num array JSON onde cada objeto contenha rigorosamente as seguintes chaves: " +
               "codigoReferencia (String), titulo (String), tipo (String, ex: Apartamento, Casa, Lote), " +
               "preco (Number) e caracteristicas (Array de Strings). Ignore imóveis sem preço.";
    }

    @Override
    protected Class<ImovelExtraidoDTO[]> getClasseRetornoDTO() {
        return ImovelExtraidoDTO[].class; 
    }
}