package alexandre_davi_miguel.busca_oferta_api.dto.loja;

import alexandre_davi_miguel.busca_oferta_api.model.Loja;

public record LojaResponseDTO(Long id, String nome, String urlBase) {
    public LojaResponseDTO(Loja loja) {
        this(loja.getId(), loja.getNome(), loja.getUrlBase());
    }
}