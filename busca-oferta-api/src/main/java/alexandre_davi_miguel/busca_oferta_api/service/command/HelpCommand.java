package alexandre_davi_miguel.busca_oferta_api.service.command;

import alexandre_davi_miguel.busca_oferta_api.service.EvolutionApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HelpCommand implements WhatsAppCommand {

    private final EvolutionApiService evolutionApiService;

    @Override
    public String getCommandString() {
        return "/ajuda";
    }

    @Override
    public void execute(String remoteJid, String messageText) {
        String help = "👋 *Bem-vindo ao Busca Oferta!*\n\n" +
                "Aqui estão os comandos que você pode usar:\n\n" +
                "📋 */ofertas* - Lista as 10 melhores ofertas do dia.\n" +
                "🔍 */ofertas {CATEGORIA}* - Filtra ofertas.\n" +
                "   _(Ex: /ofertas bebidas, /ofertas alimentos)_\n\n" +
                "⭐ */favoritar {ID}* - Salva um produto nos favoritos usando o ID da oferta.\n" +
                "📂 */meusfavoritos* - Lista os produtos que você salvou.\n" +
                "❓ */ajuda* - Mostra esta mensagem.\n\n" +
                "✅ *Categorias:* alimentos, bebidas, limpeza, higiene_pessoal, eletrodomesticos, eletronicos.\n\n" +
                "_Dica: Nosso bot monitora os encartes e avisa você quando seus produtos favoritos baixarem de preço!_";
        
        evolutionApiService.sendText(remoteJid, help);
    }
}
