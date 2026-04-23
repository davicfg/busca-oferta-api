package alexandre_davi_miguel.busca_oferta_api.repository;

import alexandre_davi_miguel.busca_oferta_api.model.AlertaPreco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlertaPrecoRepository extends JpaRepository<AlertaPreco, Long> {
    List<AlertaPreco> findByUsuarioId(Long usuarioId);
    List<AlertaPreco> findByProdutoIdAndAtivoTrue(Long produtoId);
}
