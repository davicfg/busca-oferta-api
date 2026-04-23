package alexandre_davi_miguel.busca_oferta_api.model;

import alexandre_davi_miguel.busca_oferta_api.model.enums.CategoriaProduto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaProduto categoria;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Preco> precos;

}