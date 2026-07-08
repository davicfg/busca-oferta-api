package alexandre_davi_miguel.catalogo_pecas_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PecaExtraidaDTO {
    
    @NotBlank(message = "O código da peça é obrigatório")
    private String codigoPeca;
    
    @NotBlank(message = "O nome da peça é obrigatório")
    private String nome;
    
    @NotEmpty(message = "A IA deve listar pelo menos um veículo compatível")
    private List<String> veiculosCompativeis;
    
    @NotNull(message = "A IA não pode omitir o preço de custo")
    @Positive(message = "O preço deve ser maior que zero")
    private BigDecimal precoCusto;

    private String nomeArquivoOrigem;

}