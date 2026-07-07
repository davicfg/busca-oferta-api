package alexandre_davi_miguel.catalogo_pecas_api.model;  

import alexandre_davi_miguel.busca_oferta_api.framework.EntidadeAlvoBase;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class PecaAutomotiva extends EntidadeAlvoBase {
    
    private String codigoPeca;
    private String nome;
    private List<String> veiculosCompativeis; // Pode ser mapeado como List<String> ou @ElementCollection futuramente
    private BigDecimal precoCusto;
    
}
