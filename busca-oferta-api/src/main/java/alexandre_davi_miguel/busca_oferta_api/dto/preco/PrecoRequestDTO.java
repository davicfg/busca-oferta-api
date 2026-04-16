package alexandre_davi_miguel.busca_oferta_api.dto.preco;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PrecoRequestDTO(
        Long produtoId,
        BigDecimal valor,
        LocalDate dataInicio,
        LocalDate dataFim
) {}
