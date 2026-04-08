package alexandre_davi_miguel.busca_oferta_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import alexandre_davi_miguel.busca_oferta_api.model.Loja;

public interface LojaRepository extends JpaRepository<Loja, Long> {
}
