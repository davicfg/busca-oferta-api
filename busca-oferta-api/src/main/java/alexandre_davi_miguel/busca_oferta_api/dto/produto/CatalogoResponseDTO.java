package alexandre_davi_miguel.busca_oferta_api.dto.produto;

import alexandre_davi_miguel.busca_oferta_api.model.Preco;
import alexandre_davi_miguel.busca_oferta_api.model.enums.CategoriaProduto;
import java.math.BigDecimal;

public record CatalogoResponseDTO(
    Long id,
    String nome,
    CategoriaProduto categoria,
    BigDecimal preco,
    String supermercado
) {
    public CatalogoResponseDTO(Preco precoEntity) {
        this(
            precoEntity.getProduto().getId(),
            precoEntity.getProduto().getNome(),
            precoEntity.getProduto().getCategoria(),
            precoEntity.getValor(),
            precoEntity.getSupermercado().getNome()
        );
    }
}
