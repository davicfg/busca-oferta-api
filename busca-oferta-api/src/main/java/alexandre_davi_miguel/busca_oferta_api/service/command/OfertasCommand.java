package alexandre_davi_miguel.busca_oferta_api.service.command;

import alexandre_davi_miguel.busca_oferta_api.model.Preco;
import alexandre_davi_miguel.busca_oferta_api.repository.PrecoRepository;
import alexandre_davi_miguel.busca_oferta_api.service.EvolutionApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import alexandre_davi_miguel.busca_oferta_api.model.enums.CategoriaProduto;
import alexandre_davi_miguel.busca_oferta_api.model.Preco;
import alexandre_davi_miguel.busca_oferta_api.repository.PrecoRepository;
import alexandre_davi_miguel.busca_oferta_api.service.EvolutionApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OfertasCommand implements WhatsAppCommand {

    private final PrecoRepository precoRepository;
    private final EvolutionApiService evolutionApiService;

    @Override
    public String getCommandString() {
        return "/ofertas";
    }

    @Override
    public void execute(String remoteJid, String messageText) {
        String[] parts = messageText.split("\\s+");
        CategoriaProduto categoriaFiltro = null;
        
        if (parts.length > 1) {
            try {
                categoriaFiltro = CategoriaProduto.valueOf(parts[1].toUpperCase());
            } catch (IllegalArgumentException e) {
                // Se a categoria for inválida, apenas ignora o filtro e mostra tudo
            }
        }

        List<Preco> ofertas = precoRepository.filtrarOfertas(
                null, categoriaFiltro, null, null, null, null, 
                LocalDate.now(), 
                Sort.by(Sort.Direction.DESC, "dataInicio")
        );

        StringBuilder sb = new StringBuilder();
        sb.append("📢 *Ofertas Ativas do Dia");
        if (categoriaFiltro != null) {
            sb.append(" (").append(categoriaFiltro).append(")");
        }
        sb.append("*\n\n");

        if (ofertas.isEmpty()) {
            sb.append("Desculpe, não encontrei ofertas ativas no momento. 😕");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            ofertas.stream().limit(10).forEach(o -> {
                sb.append("🆔 *ID: ").append(o.getId()).append("*\n");
                sb.append("📦 *").append(o.getProduto().getNome()).append("*\n");
                sb.append("🏪 ").append(o.getSupermercado().getNome()).append("\n");
                sb.append("💰 R$ ").append(o.getValor()).append("\n");
                sb.append("📅 Validade: ").append(o.getDataFim().format(formatter)).append("\n");
                sb.append("-------------------\n");
            });
            sb.append("\n💡 Digite */favoritar ID* para salvar um produto!");
        }

        evolutionApiService.sendText(remoteJid, sb.toString());
    }
}
