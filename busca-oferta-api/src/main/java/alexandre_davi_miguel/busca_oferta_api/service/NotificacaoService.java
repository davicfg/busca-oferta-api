package alexandre_davi_miguel.busca_oferta_api.service;

import alexandre_davi_miguel.busca_oferta_api.model.Favorito;
import alexandre_davi_miguel.busca_oferta_api.model.Preco;
import alexandre_davi_miguel.busca_oferta_api.repository.FavoritoRepository;
import alexandre_davi_miguel.busca_oferta_api.repository.PrecoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificacaoService {

    private final FavoritoRepository favoritoRepository;
    private final PrecoRepository precoRepository;
    private final EvolutionApiService evolutionApiService;

    // Roda todos os dias às 09:00 da manhã
    @Scheduled(cron = "0 0 9 * * *")
    public void enviarAlertasDiarios() {
        log.info("🚀 Iniciando envio de alertas diários de favoritos...");
        
        List<Favorito> todosFavoritos = favoritoRepository.findAll();
        
        for (Favorito fav : todosFavoritos) {
            if (fav.getUsuario().getWhatsappJid() == null) continue;

            // Busca ofertas ativas para o produto favorito usando o nome exato
            List<Preco> ofertasAtivas = precoRepository.filtrarOfertas(
                    fav.getProduto().getNome(), null, null, null, null, null, 
                    LocalDate.now(), null
            );

            if (!ofertasAtivas.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                sb.append("🔔 *Alerta de Oferta!* 🔔\n\n");
                sb.append("Olá, percebemos que um dos seus produtos favoritos está em oferta hoje!\n\n");
                
                Preco o = ofertasAtivas.get(0); // Pega a melhor/primeira oferta
                sb.append("📦 *").append(o.getProduto().getNome()).append("*\n");
                sb.append("🏪 ").append(o.getSupermercado().getNome()).append("\n");
                sb.append("💰 R$ ").append(o.getValor()).append("\n");
                sb.append("📅 Validade: ").append(o.getDataFim()).append("\n\n");
                
                sb.append("_Corra para aproveitar!_ 🏃‍♂️🛒");

                evolutionApiService.sendText(fav.getUsuario().getWhatsappJid(), sb.toString());
                log.info("✅ Alerta enviado para {} sobre o produto {}", fav.getUsuario().getWhatsappJid(), fav.getProduto().getNome());
            }
        }
    }
}
