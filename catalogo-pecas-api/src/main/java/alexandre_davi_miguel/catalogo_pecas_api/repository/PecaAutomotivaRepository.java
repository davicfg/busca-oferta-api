package main.java.alexandre_davi_miguel.catalogo_pecas_api.repository;

import alexandre_davi_miguel.catalogo_pecas_api.model.PecaAutomotiva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PecaAutomotivaRepository extends JpaRepository<PecaAutomotiva, Long> {

    Optional<PecaAutomotiva> findByCodigoPeca(String codigoPeca);
    List<PecaAutomotiva> findByNomeContainingIgnoreCase(String nome);
    List<PecaAutomotiva> findByVeiculosCompativeisContainingIgnoreCase(String veiculo);
}
