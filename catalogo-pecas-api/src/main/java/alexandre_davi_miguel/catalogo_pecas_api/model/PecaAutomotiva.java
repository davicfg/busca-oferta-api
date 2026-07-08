package alexandre_davi_miguel.catalogo_pecas_api.model;  

import alexandre_davi_miguel.busca_oferta_api.framework.EntidadeAlvoBase;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
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

    // Mapeado como @ElementCollection: cria uma tabela auxiliar (peca_veiculos_compativeis)
    // com uma linha por veículo compatível, associada ao id da peça.
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "peca_veiculos_compativeis", joinColumns = @JoinColumn(name = "peca_id"))
    @Column(name = "veiculo")
    private List<String> veiculosCompativeis;

    private BigDecimal precoCusto;
    
}
