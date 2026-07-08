package alexandre_davi_miguel.busca_oferta_api.framework;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class EntidadeAlvoBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campos de Auditoria impostos pelo Framework
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataProcessamentoIA;

    @Column(nullable = false)
    private String nomeArquivoOrigem;

    // Método concreto: O Hibernate chama isto automaticamente antes de fazer o INSERT no banco
    @PrePersist
    public void preencherAuditoria() {
        if (this.dataProcessamentoIA == null) {
            this.dataProcessamentoIA = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDataProcessamentoIA() { return dataProcessamentoIA; }
    public String getNomeArquivoOrigem() { return nomeArquivoOrigem; }
    public void setNomeArquivoOrigem(String nomeArquivoOrigem) { this.nomeArquivoOrigem = nomeArquivoOrigem; }
}