package alexandre_davi_miguel.busca_oferta_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import alexandre_davi_miguel.busca_oferta_api.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByWhatsappJid(String whatsappJid);
    
    boolean existsByEmail(String email);
}