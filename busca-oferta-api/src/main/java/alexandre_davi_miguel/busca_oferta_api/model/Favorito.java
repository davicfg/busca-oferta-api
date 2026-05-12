package alexandre_davi_miguel.busca_oferta_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.GenerationType; 
import jakarta.persistence.JoinColumn;     
import java.time.LocalDateTime;            

@Entity
@Table(name = "favoritos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Favorito {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    private LocalDateTime dataAdicao = LocalDateTime.now();
}
