package alexandre_davi_miguel.busca_oferta_api.model;

import alexandre_davi_miguel.busca_oferta_api.framework.EntidadeAlvoBase;
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
public class Produto extends EntidadeAlvoBase {

    @Column(nullable = false)
    private String nome;

    private String descricao;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaProduto categoria;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Preco> precos;

}