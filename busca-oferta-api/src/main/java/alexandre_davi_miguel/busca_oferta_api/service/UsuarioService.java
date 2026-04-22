package alexandre_davi_miguel.busca_oferta_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import alexandre_davi_miguel.busca_oferta_api.dto.usuario.UsuarioRequestDTO;
import alexandre_davi_miguel.busca_oferta_api.dto.usuario.UsuarioResponseDTO;
import alexandre_davi_miguel.busca_oferta_api.exception.EntidadeNaoEncontradaException;
import alexandre_davi_miguel.busca_oferta_api.exception.QuebraUnicidadeException;
import alexandre_davi_miguel.busca_oferta_api.model.Usuario;
import alexandre_davi_miguel.busca_oferta_api.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO salvar(UsuarioRequestDTO dto) {

        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new QuebraUnicidadeException("Já existe um usuário cadastrado com este e-mail."); // ToDo: Criar exceção customizada
        }

        Usuario usuario = Usuario.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(dto.senha()) 
                .build();
        
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return new UsuarioResponseDTO(usuarioSalvo);
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com o ID: " + id));
        return new UsuarioResponseDTO(usuario);
    }
}