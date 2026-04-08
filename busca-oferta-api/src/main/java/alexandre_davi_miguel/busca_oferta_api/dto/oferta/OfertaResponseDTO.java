package alexandre_davi_miguel.busca_oferta_api.dto.oferta;

import alexandre_davi_miguel.busca_oferta_api.model.Oferta;

public record OfertaResponseDTO(Long id, String nomeProduto, String nomeLoja, String urlDoProduto) {
    public OfertaResponseDTO(Oferta oferta) {
        this(
            oferta.getId(), 
            oferta.getProduto().getNome(), 
            oferta.getLoja().getNome(), 
            oferta.getUrlDoProduto()
        );
    }
}