package alexandre_davi_miguel.busca_oferta_api.dto.supermercado;

import alexandre_davi_miguel.busca_oferta_api.model.Supermercado;

public record SupermercadoDTO(Long id, String nome) {

    public SupermercadoDTO(Supermercado supermercado) {
        this(supermercado.getId(), supermercado.getNome());
    }

}
