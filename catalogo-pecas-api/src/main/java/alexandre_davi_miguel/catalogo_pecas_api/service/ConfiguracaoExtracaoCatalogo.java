package alexandre_davi_miguel.catalogo_pecas_api.service; 

import alexandre_davi_miguel.busca_oferta_api.framework.ConfiguracaoExtracaoIA;
import alexandre_davi_miguel.busca_oferta_api.framework.FonteDeDocumentos;
import alexandre_davi_miguel.catalogo_pecas_api.dto.PecaExtraidaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfiguracaoExtracaoCatalogo implements ConfiguracaoExtracaoIA<PecaExtraidaDTO> {

    private final FonteDeDocumentos fonteDeDocumentos;
    private final MockGeminiClient clienteIA;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<PecaExtraidaDTO> processarLotePendente() throws Exception {
        String prompt = "Analise o seguinte catálogo técnico de fabricante ou tabela de preços. " +
                        "Extraia os dados no formato JSON contendo os seguintes campos obrigatórios: " +
                        "codigo da peça, nome, veículos compatíveis e preço de custo.";

        List<PecaExtraidaDTO> pecasExtraidas = new ArrayList<>();
        List<Path> documentosPendentes = fonteDeDocumentos.listarDocumentosPendentes();

        if (documentosPendentes.isEmpty()) {
            System.out.println("Nenhum documento pendente encontrado em documentos/catalogos_pendentes.");
            return pecasExtraidas;
        }

        for (Path documento : documentosPendentes) {
            System.out.println("Processando documento: " + documento.getFileName());

            String conteudoDocumento = Files.readString(documento);

            // Chamada (simulada) à IA para extrair os dados estruturados do documento
            String jsonResposta = clienteIA.extrairCatalogoEmJson(prompt, conteudoDocumento);

            List<PecaExtraidaDTO> itensDoDocumento = objectMapper.readValue(
                    jsonResposta,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, PecaExtraidaDTO.class)
            );

            pecasExtraidas.addAll(itensDoDocumento);

            fonteDeDocumentos.moverParaProcessados(documento);
        }

        return pecasExtraidas;
    }
}
