package alexandre_davi_miguel.busca_oferta_api.dto.usuario;

import alexandre_davi_miguel.busca_oferta_api.model.Usuario;

public record UsuarioResponseDTO(Long id, String nome, String email) {
    public UsuarioResponseDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
