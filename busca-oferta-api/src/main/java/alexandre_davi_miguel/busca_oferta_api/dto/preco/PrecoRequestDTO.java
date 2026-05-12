package alexandre_davi_miguel.busca_oferta_api.dto.preco;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PrecoRequestDTO(
        @NotNull
        Long produtoId,
        @NotNull
        Long supermercadoId,
        @NotNull
        @Positive
        BigDecimal valor,
        @NotNull
        LocalDate dataInicio,
        @NotNull
        LocalDate dataFim
) {}
