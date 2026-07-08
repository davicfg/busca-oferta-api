package alexandre_davi_miguel.busca_oferta_api.dto.encarte;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ProdutoExtraidoDTO(
    @NotBlank(message = "A IA deve fornecer o nome do produto") 
    String nome,
    
    @NotBlank(message = "A IA deve fornecer a marca") 
    String marca,
    
    @NotBlank(message = "A IA deve fornecer a unidade de medida") 
    String medida,
    
    @NotNull(message = "A IA deve extrair o preço") 
    @Positive(message = "O preço não pode ser zero ou negativo") 
    BigDecimal precoUnitario,
    
    Long supermercadoId,
    
    String nomeArquivoOrigem 
) {}