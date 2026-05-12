package alexandre_davi_miguel.busca_oferta_api.controller;

import alexandre_davi_miguel.busca_oferta_api.dto.evolution.EvolutionWebhookDTO;
import alexandre_davi_miguel.busca_oferta_api.service.command.WhatsAppCommandDispatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/webhook/evolution")
@RequiredArgsConstructor
@Slf4j
public class EvolutionWebhookController {

    private final WhatsAppCommandDispatcher commandDispatcher;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<Void> receiveWebhook(@RequestBody EvolutionWebhookDTO payload) {
        log.debug("Recebido webhook da Evolution API: {}", payload.event());

        if ("messages.upsert".equals(payload.event()) && payload.data() != null) {
            List<EvolutionWebhookDTO.WebhookData> dataList = convertDataToList(payload.data());
            
            for (EvolutionWebhookDTO.WebhookData data : dataList) {
                if (data.key().fromMe()) continue; // Ignora mensagens enviadas pelo próprio bot

                // Em conversas privadas, o remoteJid é o número da pessoa.
                // Em grupos, o remoteJid é o ID do grupo e o participant é o número da pessoa.
                String remoteJid = data.key().remoteJid();
                
                // Se for uma mensagem de grupo ou se o participant estiver disponível, usamos ele para responder no privado (ou no grupo se preferir)
                // Para este bot, vamos responder sempre no local de origem (remoteJid)
                
                String text = null;
                if (data.message() != null) {
                    if (data.message().conversation() != null) {
                        text = data.message().conversation();
                    } else if (data.message().extendedTextMessage() != null) {
                        text = data.message().extendedTextMessage().text();
                    }
                }

                if (text != null && !data.key().fromMe()) {
                    commandDispatcher.dispatch(remoteJid, text);
                }
            }
        }

        return ResponseEntity.ok().build();
    }

    private List<EvolutionWebhookDTO.WebhookData> convertDataToList(Object data) {
        try {
            if (data instanceof List) {
                return objectMapper.convertValue(data, objectMapper.getTypeFactory().constructCollectionType(List.class, EvolutionWebhookDTO.WebhookData.class));
            } else {
                EvolutionWebhookDTO.WebhookData singleData = objectMapper.convertValue(data, EvolutionWebhookDTO.WebhookData.class);
                return Collections.singletonList(singleData);
            }
        } catch (Exception e) {
            log.error("Erro ao converter campo data do webhook: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
