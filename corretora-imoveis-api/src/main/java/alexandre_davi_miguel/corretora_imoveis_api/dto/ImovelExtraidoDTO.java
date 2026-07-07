package alexandre_davi_miguel.corretora_imoveis_api.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ImovelExtraidoDTO {
    private String codigoReferencia;
    private String titulo;
    private String tipo;
    private BigDecimal preco;
    private List<String> caracteristicas;
}
