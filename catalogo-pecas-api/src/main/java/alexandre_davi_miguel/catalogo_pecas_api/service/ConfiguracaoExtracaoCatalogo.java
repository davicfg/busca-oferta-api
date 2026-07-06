package alexandre_davi_miguel.catalogo_pecas_api.service; 

import alexandre_davi_miguel.busca_oferta_api.framework.AbstractExtratorIA;
import alexandre_davi_miguel.busca_oferta_api.framework.FonteDeDocumentos;
import alexandre_davi_miguel.catalogo_pecas_api.dto.PecaExtraidaDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConfiguracaoExtracaoCatalogo extends AbstractExtratorIA<PecaExtraidaDTO> {

    private final PecaAutomotivaService pecaService;

    public ConfiguracaoExtracaoCatalogo(FonteDeDocumentos fonte, PecaAutomotivaService pecaService) {
        super(fonte);
        this.pecaService = pecaService;
    }

    @Override
    protected String getPromptEspecífico() {
        return "Analise o seguinte catálogo técnico de fabricante. " +
               "Extraia os dados no formato JSON contendo: " +
               "codigoPeca, nome, veiculosCompativeis e precoCusto.";
    }

    @Override
    protected Class<PecaExtraidaDTO> getTipoDTO() {
        return PecaExtraidaDTO.class;
    }

    @Override
    protected void salvarLote(List<PecaExtraidaDTO> itensExtraidos) {
        for (PecaExtraidaDTO dto : itensExtraidos) {
            pecaService.salvar(dto);
        }
    }
}
