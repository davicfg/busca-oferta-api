package alexandre_davi_miguel.catalogo_pecas_api.service; 

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
public class FonteCatalogoPecas implements FonteDeDocumentos {

    private final Path diretorioCatalogos = Paths.get("documentos/catalogos_pendentes");
    private final Path diretorioProcessados = Paths.get("documentos/catalogos_processados");

    @Override
    public List<Path> listarDocumentosPendentes() throws IOException {
        try (Stream<Path> paths = Files.walk(diretorioCatalogos)) {
            return paths.filter(Files::isRegularFile)
                        .filter(p -> p.toString().endsWith(".pdf") || p.toString().endsWith(".csv"))
                        .collect(Collectors.toList());
        }
    }

    @Override
    public void moverParaProcessados(Path documento) throws IOException {
        Path destino = diretorioProcessados.resolve(documento.getFileName());
        Files.move(documento, destino);
    }
}
