package alexandre_davi_miguel.busca_oferta_api.repository;


import alexandre_davi_miguel.busca_oferta_api.model.Produto;
import alexandre_davi_miguel.busca_oferta_api.model.enums.CategoriaProduto;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	List<Produto> findByCategoria(CategoriaProduto categoria);
}