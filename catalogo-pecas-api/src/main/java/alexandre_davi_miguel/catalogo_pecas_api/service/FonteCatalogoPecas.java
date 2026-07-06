package alexandre_davi_miguel.catalogo_pecas_api.service; 

import alexandre_davi_miguel.busca_oferta_api.framework.AbstractFonteDeDocumentos;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FonteCatalogoPecas extends AbstractFonteDeDocumentos {

    @Override
    protected Path getDiretorioPendentes() {
        return Paths.get("documentos/catalogos_pendentes");
    }

    @Override
    protected Path getDiretorioProcessados() {
        return Paths.get("documentos/catalogos_processados");
    }

    @Override
    protected List<String> getExtensoesValidas() {
        return List.of(".pdf", ".csv", ".xlsx");
    }
}
