package alexandre_davi_miguel.busca_oferta_api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import alexandre_davi_miguel.busca_oferta_api.dto.encarte.ProdutoExtraidoDTO;
import alexandre_davi_miguel.busca_oferta_api.framework.FonteDeDocumentos;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ArquivoService implements FonteDeDocumentos{

    private final Path pastaPendentes = Paths.get("uploads/pendentes");
    private final Path pastaProcessados = Paths.get("uploads/processados");
    private final Path pastaPlanilhas = Paths.get("uploads/planilhas");

    // Cria as pastas automaticamente ao iniciar o sistema, caso nao existam
    public ArquivoService() throws IOException {
        Files.createDirectories(pastaPendentes);
        Files.createDirectories(pastaProcessados);
        Files.createDirectories(pastaPlanilhas);
    }

    // Salva o arquivo com o ID do supermercado no nome para nao perder a referecia
    public void salvarPdfPendente(MultipartFile arquivo, Long supermercadoId) throws IOException {
        String nomeArquivo = supermercadoId + "_" + System.currentTimeMillis() + "_" + arquivo.getOriginalFilename();
        
        Path destino = pastaPendentes.toAbsolutePath().resolve(nomeArquivo);
        
        Files.copy(arquivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public List<Path> listarDocumentosPendentes() throws IOException {
        try (Stream<Path> caminhos = Files.list(pastaPendentes)) {
            return caminhos.filter(Files::isRegularFile).collect(Collectors.toList());
        }
    }

    @Override
    public void moverParaProcessados(Path arquivoOriginal) throws IOException {
        Path destino = pastaProcessados.resolve(arquivoOriginal.getFileName());
        Files.move(arquivoOriginal, destino, StandardCopyOption.REPLACE_EXISTING);
    }

    public void gerarPlanilhaCsv(List<ProdutoExtraidoDTO> produtos) throws IOException {
        String nomeArquivo = "ofertas_extraidas_" + System.currentTimeMillis() + ".csv";

        Path destino = pastaPlanilhas.toAbsolutePath().resolve(nomeArquivo);

        try (PrintWriter writer = new PrintWriter(new FileWriter(destino.toFile()))) {
            writer.println("Nome,Marca,Medida,Preco,ID_Supermercado"); // Cabecalho
            for (ProdutoExtraidoDTO p : produtos) {
                writer.printf("%s,%s,%s,%s,%s\n",
                        p.nome() != null ? p.nome() : "",
                        p.marca() != null ? p.marca() : "",
                        p.medida() != null ? p.medida() : "",
                        p.precoUnitario() != null ? p.precoUnitario() : 0.0,
                        p.supermercadoId() != null ? String.valueOf(p.supermercadoId()) : "");
            }
        }
    }
}