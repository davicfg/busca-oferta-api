package alexandre_davi_miguel.busca_oferta_api.service.command;

import alexandre_davi_miguel.busca_oferta_api.dto.produto.ProdutoResponseDTO;
import alexandre_davi_miguel.busca_oferta_api.model.Usuario;
import alexandre_davi_miguel.busca_oferta_api.repository.UsuarioRepository;
import alexandre_davi_miguel.busca_oferta_api.service.EvolutionApiService;
import alexandre_davi_miguel.busca_oferta_api.service.FavoritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MeusFavoritosCommand implements WhatsAppCommand {

    private final FavoritoService favoritoService;
    private final UsuarioRepository usuarioRepository;
    private final EvolutionApiService evolutionApiService;

    @Override
    public String getCommandString() {
        return "/meusfavoritos";
    }

    @Override
    public void execute(String remoteJid, String messageText) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByWhatsappJid(remoteJid);
        
        if (usuarioOpt.isEmpty()) {
            evolutionApiService.sendText(remoteJid, "Você ainda não possui favoritos salvos. ⭐");
            return;
        }

        List<ProdutoResponseDTO> favoritos = favoritoService.listarFavoritos(usuarioOpt.get().getId());

        if (favoritos.isEmpty()) {
            evolutionApiService.sendText(remoteJid, "Sua lista de favoritos está vazia. 🛒");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("⭐ *Seus Produtos Favoritos*\n\n");
            favoritos.forEach(f -> {
                sb.append("✅ ").append(f.nome()).append(" (").append(f.categoria()).append(")\n");
            });
            sb.append("\n_O bot avisará você assim que novas ofertas destes produtos aparecerem!_");
            evolutionApiService.sendText(remoteJid, sb.toString());
        }
    }
}
