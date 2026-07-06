package alexandre_davi_miguel.busca_oferta_api.framework;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class EntidadeAlvoBase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "data_extracao", nullable = false, updatable = false)
    private LocalDateTime dataExtracao;
    
    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;

    // O framework se encarrega de registrar o momento exato da extração
    @PrePersist
    protected void onCreate() {
        this.dataExtracao = LocalDateTime.now();
    }
}
