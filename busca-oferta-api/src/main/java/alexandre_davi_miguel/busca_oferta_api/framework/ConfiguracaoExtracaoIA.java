package alexandre_davi_miguel.busca_oferta_api.framework;

import java.util.List;

import alexandre_davi_miguel.busca_oferta_api.dto.encarte.ProdutoExtraidoDTO;

public interface ConfiguracaoExtracaoIA {
    List<ProdutoExtraidoDTO> processarLotePendente() throws Exception; 
}