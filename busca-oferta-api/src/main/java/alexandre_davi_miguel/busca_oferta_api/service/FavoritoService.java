package alexandre_davi_miguel.busca_oferta_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

// Importe suas classes
import alexandre_davi_miguel.busca_oferta_api.model.*;
import alexandre_davi_miguel.busca_oferta_api.repository.*;
import alexandre_davi_miguel.busca_oferta_api.dto.produto.ProdutoResponseDTO;
import alexandre_davi_miguel.busca_oferta_api.exception.EntidadeNaoEncontradaException;
import alexandre_davi_miguel.busca_oferta_api.exception.QuebraUnicidadeException;

@Service
@RequiredArgsConstructor
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final UsuarioRepository usuarioRepository; 
    private final ProdutoRepository produtoRepository;
    private final PrecoRepository precoRepository;

    @Transactional
    public void adicionarFavoritoPorWhatsapp(String whatsappJid, Long precoId) {
        // Busca ou cria o usuário baseado no JID
        Usuario usuario = usuarioRepository.findByWhatsappJid(whatsappJid)
                .orElseGet(() -> {
                    Usuario novoUsuario = Usuario.builder()
                            .nome("Usuário WhatsApp")
                            .email(whatsappJid + "@whatsapp.com")
                            .whatsappJid(whatsappJid)
                            .senha("whatsapp_default") // Senha dummy
                            .build();
                    return usuarioRepository.save(novoUsuario);
                });

        // Busca a oferta (Preço) para pegar o Produto
        Preco preco = precoRepository.findById(precoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Oferta (ID: " + precoId + ") não encontrada."));

        adicionarFavorito(usuario.getId(), preco.getProduto().getId());
    }

    @Transactional
    public void adicionarFavorito(Long usuarioId, Long produtoId) {
        if (favoritoRepository.existsByUsuarioIdAndProdutoId(usuarioId, produtoId)) {
            throw new QuebraUnicidadeException("Produto já está nos favoritos.");
        }

        // Aqui buscamos a ENTIDADE, não o DTO
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));
        
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado"));

        Favorito favorito = Favorito.builder()
                .usuario(usuario)
                .produto(produto)
                .dataAdicao(LocalDateTime.now())
                .build();

        favoritoRepository.save(favorito);
    }

    public List<ProdutoResponseDTO> listarFavoritos(Long usuarioId) {
        return favoritoRepository.findByUsuarioId(usuarioId).stream()
                .map(f -> new ProdutoResponseDTO(f.getProduto()))
                .collect(Collectors.toList());
    }
}
 