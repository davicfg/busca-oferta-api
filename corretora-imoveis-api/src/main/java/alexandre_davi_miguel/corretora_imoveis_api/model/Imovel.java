package alexandre_davi_miguel.corretora_imoveis_api.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import alexandre_davi_miguel.busca_oferta_api.framework.EntidadeAlvoBase;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Imovel extends EntidadeAlvoBase {

    private String codigoReferencia;
    
    private String titulo;
    
    private String tipo; // Apartamento, Casa, Lote, etc.
    
    private BigDecimal preco;
    
    @ElementCollection
    private List<String> caracteristicas; // Quartos, Varanda, etc.
}
