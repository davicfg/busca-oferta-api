package alexandre_davi_miguel.busca_oferta_api.framework;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractExtratorIA<T, R> implements ConfiguracaoExtracaoIA<T> {

    protected final ChatClient chatClient;
    protected final FonteDeDocumentos fonteDeDocumentos;

    // O Framework constrói o ChatClient dinamicamente usando o Builder injetado do Spring AI
    protected AbstractExtratorIA(ChatClient.Builder builder, FonteDeDocumentos fonteDeDocumentos) {
        this.chatClient = builder.build();
        this.fonteDeDocumentos = fonteDeDocumentos;
    }

    // Ganchos (Hooks) que as APIs instanciadas preencherão
    protected abstract String getInstrucaoPrompt();
    protected abstract Class<R> getClasseRespostaIA();
    protected abstract MediaType getMediaType();
    
    // Método flexível para processamentos adicionais (como extrair IDs do nome do arquivo)
    protected abstract List<T> mapearEProcessarResultado(Path arquivoPath, R resultadoIA);

    @Override
    public List<T> processarLotePendente() throws Exception {
        List<Path> arquivosPendentes = fonteDeDocumentos.listarDocumentosPendentes();
        List<T> todosOsItensExtraidos = new ArrayList<>();

        var converter = new BeanOutputConverter<>(getClasseRespostaIA());

        for (Path arquivoPath : arquivosPendentes) {
            FileSystemResource resource = new FileSystemResource(arquivoPath.toFile());

            // Chamada unificada e padronizada do Spring AI gerenciada pelo Framework
            R resultadoDesteArquivo = chatClient.prompt()
                    .user(u -> u.text(getInstrucaoPrompt()).media(getMediaType(), resource))
                    .call()
                    .entity(converter);

            if (resultadoDesteArquivo != null) {
                List<T> itensMapeados = mapearEProcessarResultado(arquivoPath, resultadoDesteArquivo);
                if (itensMapeados != null) {
                    todosOsItensExtraidos.addAll(itensMapeados);
                }
            }

            // O framework garante a movimentação do arquivo após o término
            fonteDeDocumentos.moverParaProcessados(arquivoPath);
        }

        return todosOsItensExtraidos;
    }
}