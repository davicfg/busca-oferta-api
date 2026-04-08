package alexandre_davi_miguel.busca_oferta_api.dto.produto;

import alexandre_davi_miguel.busca_oferta_api.model.enums.CategoriaProduto;

public record ProdutoRequestDTO(String nome, CategoriaProduto categoria) {
}