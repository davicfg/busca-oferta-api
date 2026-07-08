package alexandre_davi_miguel.catalogo_pecas_api.repository;

import alexandre_davi_miguel.catalogo_pecas_api.model.PecaAutomotiva;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PecaAutomotivaRepository extends JpaRepository<PecaAutomotiva, Long> {

    Optional<PecaAutomotiva> findByCodigoPeca(String codigoPeca);
    List<PecaAutomotiva> findByNomeContainingIgnoreCase(String nome);
    List<PecaAutomotiva> findByVeiculosCompativeisContainingIgnoreCase(String veiculo);
    
    // CORREÇÃO ERRO A: Adicionado método de verificação exigido no Service
    boolean existsByCodigoPeca(String codigoPeca);

    // CORREÇÃO ERRO B: Adicionado método de filtragem customizada com JPQL para o catálogo
    // OBS: veiculosCompativeis é uma @ElementCollection, então não é possível aplicar LOWER()
    // diretamente sobre ela; é necessário fazer um LEFT JOIN com a coleção (join na cláusula FROM).
    @Query("SELECT DISTINCT p FROM PecaAutomotiva p LEFT JOIN p.veiculosCompativeis v WHERE " +
           "(:codigoPeca IS NULL OR p.codigoPeca = :codigoPeca) AND " +
           "(:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
           "(:veiculoCompativel IS NULL OR LOWER(v) LIKE LOWER(CONCAT('%', :veiculoCompativel, '%'))) AND " +
           "(:precoMin IS NULL OR p.precoCusto >= :precoMin) AND " +
           "(:precoMax IS NULL OR p.precoCusto <= :precoMax)")
    List<PecaAutomotiva> filtrarPecas(
            @Param("codigoPeca") String codigoPeca,
            @Param("nome") String nome,
            @Param("veiculoCompativel") String veiculoCompativel,
            @Param("precoMin") BigDecimal precoMin,
            @Param("precoMax") BigDecimal precoMax,
            Sort sort);
}
