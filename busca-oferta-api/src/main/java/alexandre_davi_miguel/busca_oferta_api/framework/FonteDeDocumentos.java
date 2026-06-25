package alexandre_davi_miguel.busca_oferta_api.framework;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FonteDeDocumentos {
 List<Path> listarPdfsPendentes() throws IOException;
 void moverParaProcessados(Path documento)  throws IOException ;
}