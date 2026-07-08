package alexandre_davi_miguel.busca_oferta_api.dto.encarte;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record EncarteExtracaoDTO(
    @Valid 
    @NotEmpty(message = "A IA falhou: O encarte não pode estar vazio") 
    List<ProdutoExtraidoDTO> itens
) {}