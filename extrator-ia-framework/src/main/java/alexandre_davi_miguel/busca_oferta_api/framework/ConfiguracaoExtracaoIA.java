package alexandre_davi_miguel.busca_oferta_api.framework;

import java.util.List;

public interface ConfiguracaoExtracaoIA<T> {
    List<T> processarLotePendente() throws Exception; 
}