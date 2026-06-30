package alexandre_davi_miguel.busca_oferta_api.framework;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FonteDeDocumentos {
 List<Path> listarDocumentosPendentes() throws IOException;
 void moverParaProcessados(Path documento)  throws IOException ;
}