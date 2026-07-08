package alexandre_davi_miguel.corretora_imoveis_api.dto;

import lombok.Data;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class ImovelExtraidoDTO {
	@NotBlank(message = "O código de referência é obrigatório")
    private String codigoReferencia;
    
    @NotBlank(message = "O título não pode ser vazio")
    private String titulo;
    
    @NotNull(message = "O preço deve ser informado pela IA")
    @Positive(message = "O preço não pode ser negativo")
    private Double preco;
    
    private String tipo;
    private List<String> caracteristicas;
}
