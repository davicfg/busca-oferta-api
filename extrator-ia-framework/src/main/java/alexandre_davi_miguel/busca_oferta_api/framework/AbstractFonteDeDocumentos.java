package alexandre_davi_miguel.busca_oferta_api.framework;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractFonteDeDocumentos implements FonteDeDocumentos {

    // Contratos que as instâncias filhas (Varejo, Autopeças, Corretora) devem cumprir
    protected abstract Path getDiretorioPendentes();
    protected abstract Path getDiretorioProcessados();
    protected abstract List<String> getExtensoesValidas();

    @Override
    public List<Path> listarDocumentosPendentes() throws IOException {
        Path diretorio = getDiretorioPendentes();
        
        // Proteção do framework: cria a pasta se ela não existir
        if (!Files.exists(diretorio)) {
            Files.createDirectories(diretorio);
        }

        try (Stream<Path> paths = Files.walk(diretorio)) {
            return paths.filter(Files::isRegularFile)
                        .filter(this::isExtensaoValida)
                        .collect(Collectors.toList());
        }
    }

    @Override
    public void moverParaProcessados(Path documento) throws IOException {
        Path diretorioProcessados = getDiretorioProcessados();
        
        if (!Files.exists(diretorioProcessados)) {
            Files.createDirectories(diretorioProcessados);
        }

        Path destino = diretorioProcessados.resolve(documento.getFileName());
        Files.move(documento, destino);
    }

    private boolean isExtensaoValida(Path arquivo) {
        String nomeArquivo = arquivo.toString().toLowerCase();
        return getExtensoesValidas().stream()
                .anyMatch(ext -> nomeArquivo.endsWith(ext.toLowerCase()));
    }
}