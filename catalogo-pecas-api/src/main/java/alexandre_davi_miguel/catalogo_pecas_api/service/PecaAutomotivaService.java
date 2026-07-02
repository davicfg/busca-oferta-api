package alexandre_davi_miguel.catalogo_pecas_api.service; 

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import alexandre_davi_miguel.catalogo_pecas_api.dto.PecaExtraidaDTO;
import alexandre_davi_miguel.catalogo_pecas_api.exception.BusinessException;
import alexandre_davi_miguel.catalogo_pecas_api.model.PecaAutomotiva;
import alexandre_davi_miguel.catalogo_pecas_api.repository.PecaAutomotivaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PecaAutomotivaService {

    private final PecaAutomotivaRepository pecaRepository;

    private void validarPeca(PecaExtraidaDTO dto) {
        if (dto.getCodigoPeca() == null || dto.getCodigoPeca().isBlank()) {
            throw new IllegalArgumentException("A validação falhou: O código da peça é obrigatório.");
        }
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new IllegalArgumentException("A validação falhou: O nome da peça não pode estar vazio.");
        }
        if (dto.getPrecoCusto() == null || dto.getPrecoCusto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("A validação falhou: O preço de custo deve ser maior que zero.");
        }
    }

    // Método auxiliar para converter a Entidade no seu DTO único
    private PecaExtraidaDTO converterParaDTO(PecaAutomotiva peca) {
        PecaExtraidaDTO dto = new PecaExtraidaDTO();
        dto.setCodigoPeca(peca.getCodigoPeca());
        dto.setNome(peca.getNome());
        dto.setVeiculosCompativeis(peca.getVeiculosCompativeis());
        dto.setPrecoCusto(peca.getPrecoCusto());
        return dto;
    }

    public PecaExtraidaDTO salvar(PecaExtraidaDTO dto) {
        validarPeca(dto);

        if (pecaRepository.existsByCodigoPeca(dto.getCodigoPeca())) {
            throw new BusinessException("Já existe uma peça cadastrada com o código: " + dto.getCodigoPeca());
        }

        PecaAutomotiva peca = new PecaAutomotiva();
        peca.setCodigoPeca(dto.getCodigoPeca());
        peca.setNome(dto.getNome());
        peca.setVeiculosCompativeis(dto.getVeiculosCompativeis());
        peca.setPrecoCusto(dto.getPrecoCusto());

        PecaAutomotiva pecaSalva = pecaRepository.save(peca);
        return converterParaDTO(pecaSalva);
    }

    public List<PecaExtraidaDTO> consultarCatalogo(
            String codigoPeca, String nome, String veiculoCompativel, 
            BigDecimal precoMin, BigDecimal precoMax, String ordemPreco) {
        
        Sort sort = Sort.unsorted();
        if ("asc".equalsIgnoreCase(ordemPreco)) {
            sort = Sort.by(Sort.Direction.ASC, "precoCusto");
        } else if ("desc".equalsIgnoreCase(ordemPreco)) {
            sort = Sort.by(Sort.Direction.DESC, "precoCusto");
        }

        return pecaRepository.filtrarPecas(codigoPeca, nome, veiculoCompativel, precoMin, precoMax, sort)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public List<PecaExtraidaDTO> listarTodos(String nome, String veiculoCompativel) {
        List<PecaAutomotiva> pecas;
        
        if (nome != null && !nome.isBlank()) {
            pecas = pecaRepository.findByNomeContainingIgnoreCase(nome);
            if (veiculoCompativel != null && !veiculoCompativel.isBlank()) {
                // Como agora é uma List<String>, verificamos se a lista contém o veículo
                pecas = pecas.stream()
                        .filter(p -> p.getVeiculosCompativeis() != null && 
                                p.getVeiculosCompativeis().stream()
                                 .anyMatch(v -> v.toLowerCase().contains(veiculoCompativel.toLowerCase())))
                        .collect(Collectors.toList());
            }
        } else if (veiculoCompativel != null && !veiculoCompativel.isBlank()) {
            // Se o JPA não suportar busca direta em coleção nativamente na interface, 
            // este método customizado precisará ser ajustado no Repository, 
            // mas manteremos a assinatura que você criou.
            pecas = pecaRepository.findByVeiculosCompativeisContainingIgnoreCase(veiculoCompativel);
        } else {
            pecas = pecaRepository.findAll();
        }
        
        return pecas.stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    public PecaExtraidaDTO buscarPorId(Long id) {
        PecaAutomotiva peca = pecaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Peça não encontrada com o ID: " + id));
        return converterParaDTO(peca);
    }
}
