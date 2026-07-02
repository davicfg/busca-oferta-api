package alexandre_davi_miguel.busca_oferta_api.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.http.MediaType;

import alexandre_davi_miguel.busca_oferta_api.dto.encarte.EncarteExtracaoDTO;
import alexandre_davi_miguel.busca_oferta_api.dto.encarte.ProdutoExtraidoDTO;
import alexandre_davi_miguel.busca_oferta_api.framework.ConfiguracaoExtracaoIA;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class EncarteIAService implements ConfiguracaoExtracaoIA{

    private final ChatClient chatClient;
    private final ArquivoService arquivoService;

    public EncarteIAService(ChatClient.Builder builder, ArquivoService arquivoService) {
        this.chatClient = builder.build();
        this.arquivoService = arquivoService;
    }

    @Override
    public List<ProdutoExtraidoDTO> processarLotePendente() throws Exception {
        List<Path> arquivosPendentes = arquivoService.listarDocumentosPendentes();
        List<ProdutoExtraidoDTO> todosOsProdutosExtraidos = new ArrayList<>();

        var converter = new BeanOutputConverter<>(EncarteExtracaoDTO.class);
        String instrucao = """
            Analise este encarte de supermercado. 
            Extraia o nome dos produtos, marcas, unidades de medida e preços.
            Retorne exclusivamente no formato JSON conforme o esquema fornecido.
            """;

        for (Path pdfPath : arquivosPendentes) {
            // Extrai o ID do supermercado do nome do arquivo (ex: "1_169000_encarte.pdf" -> ID 1)
            Long supermercadoId = Long.parseLong(pdfPath.getFileName().toString().split("_")[0]);
            
            FileSystemResource resource = new FileSystemResource(pdfPath.toFile());

            // Chamada para o Gemini ler o PDF atual
            EncarteExtracaoDTO resultadoDestePdf = chatClient.prompt()
                    .user(u -> u.text(instrucao).media(MediaType.APPLICATION_PDF, resource))
                    .call()
                    .entity(converter);

            // Adiciona o supermercadoId em cada produto extraído e joga na lista geral
            if (resultadoDestePdf != null && resultadoDestePdf.itens() != null) {
                for (ProdutoExtraidoDTO item : resultadoDestePdf.itens()) {
                    todosOsProdutosExtraidos.add(new ProdutoExtraidoDTO(
                            item.nome(), item.marca(), item.medida(), item.precoUnitario(), supermercadoId
                    ));
                }
            }

            // Move o arquivo para a pasta de processados para não ser lido de novo
            arquivoService.moverParaProcessados(pdfPath);
        }

        return todosOsProdutosExtraidos;
    }
}