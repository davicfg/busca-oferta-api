package alexandre_davi_miguel.busca_oferta_api.service.command;

public interface WhatsAppCommand {
    String getCommandString();
    void execute(String remoteJid, String messageText);
}
