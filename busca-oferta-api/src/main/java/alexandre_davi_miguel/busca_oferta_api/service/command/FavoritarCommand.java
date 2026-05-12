package alexandre_davi_miguel.busca_oferta_api.service.command;

import alexandre_davi_miguel.busca_oferta_api.service.EvolutionApiService;
import alexandre_davi_miguel.busca_oferta_api.service.FavoritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoritarCommand implements WhatsAppCommand {

    private final FavoritoService favoritoService;
    private final EvolutionApiService evolutionApiService;

    @Override
    public String getCommandString() {
        return "/favoritar";
    }

    @Override
    public void execute(String remoteJid, String messageText) {
        String[] parts = messageText.split("\\s+");
        
        if (parts.length < 2) {
            evolutionApiService.sendText(remoteJid, "❌ Por favor, informe o ID da oferta.\nExemplo: */favoritar 12*");
            return;
        }

        try {
            Long precoId = Long.parseLong(parts[1]);
            favoritoService.adicionarFavoritoPorWhatsapp(remoteJid, precoId);
            evolutionApiService.sendText(remoteJid, "⭐ *Sucesso!* Produto adicionado aos seus favoritos. Você será avisado quando houver novas ofertas similares!");
        } catch (NumberFormatException e) {
            evolutionApiService.sendText(remoteJid, "❌ ID inválido. Use apenas números.");
        } catch (Exception e) {
            evolutionApiService.sendText(remoteJid, "❌ Erro: " + e.getMessage());
        }
    }
}
