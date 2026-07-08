package alexandre_davi_miguel.busca_oferta_api.service;

import org.springframework.stereotype.Service;
import alexandre_davi_miguel.busca_oferta_api.dto.encarte.EncarteExtracaoDTO;
import alexandre_davi_miguel.busca_oferta_api.dto.encarte.ProdutoExtraidoDTO;
import alexandre_davi_miguel.busca_oferta_api.framework.PipelineExtracaoGenerico;
import alexandre_davi_miguel.busca_oferta_api.model.Produto;
import alexandre_davi_miguel.busca_oferta_api.model.enums.CategoriaProduto;
import alexandre_davi_miguel.busca_oferta_api.repository.ProdutoRepository;
import alexandre_davi_miguel.busca_oferta_api.framework.FonteDeDocumentos;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EncarteIAService extends PipelineExtracaoGenerico<EncarteExtracaoDTO> {

    private final ArquivoService arquivoService;
    private final ProdutoRepository produtoRepository;

    public EncarteIAService(ArquivoService arquivoService, ProdutoRepository produtoRepository) {
        this.arquivoService = arquivoService;
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoExtraidoDTO> processarLotePendente() throws Exception {
        
        Map<Path, EncarteExtracaoDTO> resultadosBrutos = super.executarPipelineExtracao();
        List<ProdutoExtraidoDTO> listaRevisao = new ArrayList<>();
        
        for (Map.Entry<Path, EncarteExtracaoDTO> entry : resultadosBrutos.entrySet()) {
            Path pdfPath = entry.getKey();
            EncarteExtracaoDTO dtoIA = entry.getValue();
            
            // Pega o rastro do arquivo
            String nomeArquivo = pdfPath.getFileName().toString();
            Long supermercadoId = Long.parseLong(nomeArquivo.split("_")[0]);
            
            if (dtoIA != null && dtoIA.itens() != null) {
                for (ProdutoExtraidoDTO item : dtoIA.itens()) {
                    // Repassa o nome do arquivo para o DTO final
                    listaRevisao.add(new ProdutoExtraidoDTO(
                            item.nome(), item.marca(), item.medida(), 
                            item.precoUnitario(), supermercadoId, nomeArquivo
                    ));
                }
            }
        }
        return listaRevisao; 
    }
    
    public void salvarProdutosNoBanco(List<ProdutoExtraidoDTO> dtos) {
        for (ProdutoExtraidoDTO dto : dtos) {
            Produto novo = new Produto();
            
            novo.setNome(dto.nome());
            novo.setCategoria(CategoriaProduto.ALIMENTOS);
            
            novo.setNomeArquivoOrigem(dto.nomeArquivoOrigem());
            
            produtoRepository.save(novo);
        }
    }

    // --- IMPLEMENTAÇÃO DOS CONTRATOS DO FRAMEWORK ---
    
    @Override
    protected FonteDeDocumentos getFonteDeDocumentos() {
        return this.arquivoService;
    }

    @Override
    protected String getPromptInstrucao() {
        return "Analise este encarte de supermercado. Extraia o nome dos produtos, " +
               "marcas, unidades de medida e preços.";
    }

    @Override
    protected Class<EncarteExtracaoDTO> getClasseRetornoDTO() {
        return EncarteExtracaoDTO.class;
    }
}