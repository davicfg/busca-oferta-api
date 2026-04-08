package alexandre_davi_miguel.busca_oferta_api.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import alexandre_davi_miguel.busca_oferta_api.model.Oferta;

import java.util.List;

public interface OfertaRepository extends JpaRepository<Oferta, Long> {
    List<Oferta> findByProdutoId(Long produtoId);
}