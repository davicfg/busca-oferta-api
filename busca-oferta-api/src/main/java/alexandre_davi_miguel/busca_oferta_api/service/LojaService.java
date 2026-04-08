package alexandre_davi_miguel.busca_oferta_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import alexandre_davi_miguel.busca_oferta_api.dto.loja.LojaRequestDTO;
import alexandre_davi_miguel.busca_oferta_api.dto.loja.LojaResponseDTO;
import alexandre_davi_miguel.busca_oferta_api.model.Loja;
import alexandre_davi_miguel.busca_oferta_api.repository.LojaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LojaService {

    private final LojaRepository lojaRepository;

    public LojaResponseDTO salvar(LojaRequestDTO dto) {
        Loja loja = Loja.builder()
                .nome(dto.nome())
                .urlBase(dto.urlBase())
                .build();
        
        Loja lojaSalva = lojaRepository.save(loja);
        return new LojaResponseDTO(lojaSalva);
    }

    public List<LojaResponseDTO> listarTodas() {
        return lojaRepository.findAll().stream()
                .map(LojaResponseDTO::new)
                .collect(Collectors.toList());
    }
}