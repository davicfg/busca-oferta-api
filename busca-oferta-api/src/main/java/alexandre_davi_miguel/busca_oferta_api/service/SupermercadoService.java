package alexandre_davi_miguel.busca_oferta_api.service;

import alexandre_davi_miguel.busca_oferta_api.dto.supermercado.SupermercadoDTO;
import alexandre_davi_miguel.busca_oferta_api.model.Supermercado;
import alexandre_davi_miguel.busca_oferta_api.repository.SupermercadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupermercadoService {

    private final SupermercadoRepository supermercadoRepository;

    public List<SupermercadoDTO> listarTodos() {
        return supermercadoRepository.findAll().stream()
                .map(s -> new SupermercadoDTO(s.getId(), s.getNome()))
                .collect(Collectors.toList());
    }
}