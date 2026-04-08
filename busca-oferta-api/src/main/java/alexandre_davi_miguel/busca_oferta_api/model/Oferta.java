package alexandre_davi_miguel.busca_oferta_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ofertas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String urlDoProduto;

    // Muitas ofertas podem pertencer a um mesmo produto
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    // Muitas ofertas podem pertencer a uma mesma loja
    @ManyToOne
    @JoinColumn(name = "loja_id", nullable = false)
    private Loja loja;
}