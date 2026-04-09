package alexandre_davi_miguel.busca_oferta_api.repository;

import alexandre_davi_miguel.busca_oferta_api.model.Preco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrecoRepository extends JpaRepository<Preco, Long> {
    List<Preco> findByProdutoId(Long produtoId);
}
