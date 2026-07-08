package alexandre_davi_miguel.corretora_imoveis_api.service;

import alexandre_davi_miguel.busca_oferta_api.framework.FonteDeDocumentos;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FontePlantasImoveis implements FonteDeDocumentos {

    // Define as pastas onde os PDFs das plantas/folders dos imóveis serão colocados
    private final Path diretorioPendentes = Paths.get("imoveis/pendentes");
    private final Path diretorioProcessados = Paths.get("imoveis/processados");

    public FontePlantasImoveis() throws IOException {
        Files.createDirectories(diretorioPendentes);
        Files.createDirectories(diretorioProcessados);
    }

    @Override
    public List<Path> listarDocumentosPendentes() throws IOException {
        try (Stream<Path> paths = Files.walk(diretorioPendentes)) {
            return paths.filter(Files::isRegularFile)
                        .filter(p -> p.toString().endsWith(".pdf"))
                        .collect(Collectors.toList());
        }
    }

    @Override
    public void moverParaProcessados(Path documento) throws IOException {
        Path destino = diretorioProcessados.resolve(documento.getFileName());
        Files.move(documento, destino);
    }
}