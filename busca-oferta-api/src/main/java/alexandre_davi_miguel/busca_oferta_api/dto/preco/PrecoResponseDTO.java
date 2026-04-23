package alexandre_davi_miguel.busca_oferta_api.dto.preco;

import alexandre_davi_miguel.busca_oferta_api.model.Preco;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PrecoResponseDTO(
        Long id,
        Long produtoId,
        String nomeProduto,
        BigDecimal valor,
        LocalDate dataInicio,
        LocalDate dataFim
) {
    public PrecoResponseDTO(Preco preco) {
        this(
                preco.getId(),
                preco.getProduto().getId(),
                preco.getProduto().getNome(),
                preco.getValor(),
                preco.getDataInicio(),
                preco.getDataFim()
        );
    }
}
