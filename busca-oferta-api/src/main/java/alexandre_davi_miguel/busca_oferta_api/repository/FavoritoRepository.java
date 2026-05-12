package alexandre_davi_miguel.busca_oferta_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import alexandre_davi_miguel.busca_oferta_api.model.Favorito;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    List<Favorito> findByUsuarioId(Long usuarioId);
    boolean existsByUsuarioIdAndProdutoId(Long usuarioId, Long produtoId);
    
    @Modifying
    @Transactional
    void deleteByUsuarioIdAndProdutoId(Long usuarioId, Long produtoId);
}
