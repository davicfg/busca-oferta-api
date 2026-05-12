package alexandre_davi_miguel.busca_oferta_api.repository;

import alexandre_davi_miguel.busca_oferta_api.model.Preco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

import alexandre_davi_miguel.busca_oferta_api.model.enums.CategoriaProduto;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;

import java.time.LocalDate;
import org.springframework.data.domain.Sort;

@Repository
public interface PrecoRepository extends JpaRepository<Preco, Long> {
    List<Preco> findByProdutoId(Long produtoId);

    List<Preco> findByProdutoIdOrderByDataInicioDesc(Long produtoId);

    @Modifying
    void deleteByDataFimBefore(LocalDate data);   

    @Query("SELECT p FROM Preco p WHERE " +
           "(:nome IS NULL OR LOWER(p.produto.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
           "(:categoria IS NULL OR p.produto.categoria = :categoria) AND " +
           "(:precoMin IS NULL OR p.valor >= :precoMin) AND " +
           "(:precoMax IS NULL OR p.valor <= :precoMax) AND " +
           "(:supermercado IS NULL OR LOWER(p.supermercado.nome) LIKE LOWER(CONCAT('%', :supermercado, '%'))) AND " +
           "(:endereco IS NULL OR LOWER(p.supermercado.endereco) LIKE LOWER(CONCAT('%', :endereco, '%'))) AND " +
           "(:dataReferencia IS NULL OR (p.dataInicio <= :dataReferencia AND p.dataFim >= :dataReferencia))")
    List<Preco> filtrarOfertas(
            @Param("nome") String nome,
            @Param("categoria") CategoriaProduto categoria,
            @Param("precoMin") BigDecimal precoMin,
            @Param("precoMax") BigDecimal precoMax,
            @Param("supermercado") String supermercado,
            @Param("endereco") String endereco,
            @Param("dataReferencia") LocalDate dataReferencia,
            Sort sort);
}
