package alexandre_davi_miguel.busca_oferta_api.dto.evolution;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EvolutionWebhookDTO(
    String event,
    String instance,
    Object data,
    String sender
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record WebhookData(
        WebhookKey key,
        String pushName,
        WebhookMessage message,
        String messageType
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record WebhookKey(
        String remoteJid,
        boolean fromMe,
        String id,
        String participant
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record WebhookMessage(
        String conversation,
        ExtendedTextMessage extendedTextMessage
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ExtendedTextMessage(
        String text
    ) {}
}
