package alexandre_davi_miguel.busca_oferta_api.framework;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class PipelineExtracaoGenerico<T> {

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    @Autowired
    private Validator validadorGenerico; 

    public Map<Path, T> executarPipelineExtracao() throws Exception {
        System.out.println("Framework: Iniciando motor de extração e validação...");
        Map<Path, T> mapaResultados = new HashMap<>();
        ChatClient chatClient = chatClientBuilder.build();
        
        List<Path> arquivosPendentes = getFonteDeDocumentos().listarDocumentosPendentes();
        var converter = new BeanOutputConverter<>(getClasseRetornoDTO());

        for (Path documentoPath : arquivosPendentes) {
            FileSystemResource resource = new FileSystemResource(documentoPath.toFile());

            T resultadoIA = chatClient.prompt()
                    .user(u -> u.text(getPromptInstrucao()).media(MediaType.APPLICATION_PDF, resource))
                    .call()
                    .entity(converter);

            if (resultadoIA != null) {
                Set<ConstraintViolation<T>> violacoes = validadorGenerico.validate(resultadoIA);
                
                if (violacoes.isEmpty()) {
                    mapaResultados.put(documentoPath, resultadoIA); // Dados 100% íntegros
                } else {
                    System.err.println("Framework ALERTA: IA gerou dados inválidos para " + documentoPath.getFileName());
                    for (ConstraintViolation<T> violacao : violacoes) {
                        System.err.println(" -> Erro no campo '" + violacao.getPropertyPath() + "': " + violacao.getMessage());
                    }
                }
            }

            getFonteDeDocumentos().moverParaProcessados(documentoPath);
        }

        return mapaResultados;
    }
    
    protected abstract FonteDeDocumentos getFonteDeDocumentos();
    protected abstract String getPromptInstrucao();
    protected abstract Class<T> getClasseRetornoDTO();
}