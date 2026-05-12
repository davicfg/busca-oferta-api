package alexandre_davi_miguel.busca_oferta_api.service;

import alexandre_davi_miguel.busca_oferta_api.dto.evolution.EvolutionSendTextRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class EvolutionApiService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${evolution.api.url}")
    private String evolutionUrl;

    @Value("${evolution.api.instance}")
    private String instance;

    @Value("${evolution.api.apikey}")
    private String apikey;

    public void sendText(String remoteJid, String text) {
        String resolvedJid = resolveRealJid(remoteJid);
        String url = String.format("%s/message/sendText/%s", evolutionUrl, instance);

        log.debug("Enviando resposta. Original: {} | Resolvido: {}", remoteJid, resolvedJid);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", apikey);

        EvolutionSendTextRequestDTO request = new EvolutionSendTextRequestDTO(resolvedJid, text);
        HttpEntity<EvolutionSendTextRequestDTO> entity = new HttpEntity<>(request, headers);

        try {
            restTemplate.postForEntity(url, entity, String.class);
            log.info("Mensagem enviada com sucesso para {}", resolvedJid);
        } catch (Exception e) {
            log.error("Erro ao enviar mensagem para {}: {}", resolvedJid, e.getMessage());
            if (e instanceof org.springframework.web.client.HttpClientErrorException) {
                log.error("Corpo do erro da Evolution API: {}", ((org.springframework.web.client.HttpClientErrorException) e).getResponseBodyAsString());
            }
        }
    }

    private String resolveRealJid(String jid) {
        if (jid == null || !jid.endsWith("@lid")) {
            return jid;
        }

        log.info("🔍 Detectado LID: {}. Tentando resolver JID real via GET...", jid);
        // Endpoint correto para busca de perfil na Evolution v2
        String url = String.format("%s/chat/fetchProfile/%s?number=%s", evolutionUrl, instance, jid);
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", apikey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            var response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, entity, java.util.Map.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                java.util.Map body = response.getBody();
                // O retorno costuma vir dentro de um campo 'jid' ou similar
                if (body.containsKey("jid")) {
                    String realJid = (String) body.get("jid");
                    log.info("✅ LID {} resolvido para {}", jid, realJid);
                    return realJid;
                }
            }
        } catch (Exception e) {
            log.warn("❌ Falha ao resolver LID {} via API: {}. Tentando conversão manual...", jid, e.getMessage());
        }

        return jid.split("@")[0] + "@s.whatsapp.net";
    }
}
