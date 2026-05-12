package alexandre_davi_miguel.busca_oferta_api.service.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WhatsAppCommandDispatcher {

    private final Map<String, WhatsAppCommand> commands = new HashMap<>();

    public WhatsAppCommandDispatcher(List<WhatsAppCommand> commandList) {
        commandList.forEach(cmd -> commands.put(cmd.getCommandString().toLowerCase(), cmd));
    }

    public void dispatch(String remoteJid, String messageText) {
        if (messageText == null || messageText.isBlank()) return;

        log.info("📢 [AUDITORIA] Mensagem recebida de: {} | Conteúdo: '{}'", remoteJid, messageText);

        String firstWord = messageText.split("\\s+")[0].toLowerCase();
        WhatsAppCommand command = commands.get(firstWord);

        if (command != null) {
            log.info("✅ [AUDITORIA] Comando identificado: {}. Enviando resposta para: {}", firstWord, remoteJid);
            command.execute(remoteJid, messageText);
        } else {
            log.debug("Nenhum comando encontrado para a mensagem: {}", messageText);
        }
    }
}
