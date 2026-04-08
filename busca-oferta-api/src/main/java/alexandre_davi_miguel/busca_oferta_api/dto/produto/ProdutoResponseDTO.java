package alexandre_davi_miguel.busca_oferta_api.dto.produto;

import alexandre_davi_miguel.busca_oferta_api.model.Produto;

public record ProdutoResponseDTO(Long id, String nome, String categoria) {
    public ProdutoResponseDTO(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getCategoria());
    }
}