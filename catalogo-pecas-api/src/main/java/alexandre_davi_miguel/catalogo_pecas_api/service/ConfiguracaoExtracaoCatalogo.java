package alexandre_davi_miguel.catalogo_pecas_api.service;

import alexandre_davi_miguel.busca_oferta_api.framework.PipelineExtracaoGenerico;
import alexandre_davi_miguel.busca_oferta_api.framework.FonteDeDocumentos;
import alexandre_davi_miguel.catalogo_pecas_api.dto.PecaExtraidaDTO;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ConfiguracaoExtracaoCatalogo extends PipelineExtracaoGenerico<PecaExtraidaDTO[]> {

    private final FonteCatalogoPecas fonteCatalogoPecas;

    public ConfiguracaoExtracaoCatalogo(FonteCatalogoPecas fonteCatalogoPecas) {
        this.fonteCatalogoPecas = fonteCatalogoPecas;
    }

    // MÉTODO QUE O CONTROLLER VAI CHAMAR
    public List<PecaExtraidaDTO> processarLotePendente() throws Exception {
        
        // O Framework garante que só arrays válidos cheguem aqui
        Map<Path, PecaExtraidaDTO[]> resultadosBrutos = super.executarPipelineExtracao();
        List<PecaExtraidaDTO> todasAsPecas = new ArrayList<>();
        
        for (Map.Entry<Path, PecaExtraidaDTO[]> entry : resultadosBrutos.entrySet()) {
            String nomeArquivo = entry.getKey().getFileName().toString();
            PecaExtraidaDTO[] pecasDesteArquivo = entry.getValue();
            
            if (pecasDesteArquivo != null) {
                for (PecaExtraidaDTO peca : pecasDesteArquivo) {
                    // Carimba o rastro do documento na peça antes de devolver
                    peca.setNomeArquivoOrigem(nomeArquivo); 
                    todasAsPecas.add(peca);
                }
            }
        }
        return todasAsPecas;
    }

    // --- IMPLEMENTAÇÃO DOS HOT-SPOTS EXIGIDOS PELO FRAMEWORK ---

    @Override
    protected FonteDeDocumentos getFonteDeDocumentos() {
        return this.fonteCatalogoPecas;
    }

    @Override
    protected String getPromptInstrucao() {
        return "Analise este catálogo técnico de autopeças. Extraia os dados num array JSON onde cada objeto " +
               "contenha obrigatoriamente as chaves: codigoPeca, nome, veiculosCompativeis e precoCusto.";
    }

    @Override
    protected Class<PecaExtraidaDTO[]> getClasseRetornoDTO() {
        return PecaExtraidaDTO[].class; 
    }
}