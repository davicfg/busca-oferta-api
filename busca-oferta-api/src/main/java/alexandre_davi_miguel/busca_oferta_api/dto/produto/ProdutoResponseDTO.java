package alexandre_davi_miguel.busca_oferta_api.dto.produto;

import alexandre_davi_miguel.busca_oferta_api.model.Produto;
import alexandre_davi_miguel.busca_oferta_api.model.enums.CategoriaProduto;

public record ProdutoResponseDTO(Long id, String nome, CategoriaProduto categoria) {
    public ProdutoResponseDTO(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getCategoria());
    }
}