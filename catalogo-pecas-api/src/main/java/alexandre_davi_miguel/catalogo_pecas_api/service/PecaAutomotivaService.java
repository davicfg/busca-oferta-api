package main.java.alexandre_davi_miguel.catalogo_pecas_api.service; 

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import alexandre_davi_miguel.busca_oferta_api.instanciacao2.dto.PecaAutomotivaRequestDTO;
import alexandre_davi_miguel.busca_oferta_api.instanciacao2.dto.PecaAutomotivaResponseDTO;
import alexandre_davi_miguel.busca_oferta_api.exception.EntidadeNaoEncontradaException;
import alexandre_davi_miguel.busca_oferta_api.instanciacao2.model.PecaAutomotiva;
import alexandre_davi_miguel.busca_oferta_api.instanciacao2.repository.PecaAutomotivaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PecaAutomotivaService {

    private final PecaAutomotivaRepository pecaRepository;

    /**
     * Validação centralizada antes de interagir com o banco de dados.
     */
    private void validarPeca(PecaAutomotivaRequestDTO dto) {
        if (dto.codigoPeca() == null || dto.codigoPeca().isBlank()) {
            throw new IllegalArgumentException("A validação falhou: O código da peça é obrigatório.");
        }
        if (dto.nome() == null || dto.nome().isBlank()) {
            throw new IllegalArgumentException("A validação falhou: O nome da peça não pode estar vazio.");
        }
        if (dto.precoCusto() == null || dto.precoCusto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("A validação falhou: O preço de custo deve ser maior que zero.");
        }
    }

    public PecaAutomotivaResponseDTO salvar(PecaAutomotivaRequestDTO dto) {
        // 1. Executa a checagem solicitada antes de prosseguir
        validarPeca(dto);

        // 2. Validação de regra de negócio: impede duplicação
        if (pecaRepository.existsByCodigoPeca(dto.codigoPeca())) {
            throw new IllegalArgumentException("Já existe uma peça cadastrada com o código: " + dto.codigoPeca());
        }

        // 3. Conversão e persistência
        PecaAutomotiva peca = new PecaAutomotiva();
        peca.setCodigoPeca(dto.codigoPeca());
        peca.setNome(dto.nome());
        peca.setVeiculosCompativeis(dto.veiculosCompativeis());
        peca.setPrecoCusto(dto.precoCusto());

        PecaAutomotiva pecaSalva = pecaRepository.save(peca);
        return new PecaAutomotivaResponseDTO(pecaSalva);
    }

    public List<PecaAutomotivaResponseDTO> consultarCatalogo(
            String codigoPeca, String nome, String veiculoCompativel, 
            BigDecimal precoMin, BigDecimal precoMax, String ordemPreco) {
        
        Sort sort = Sort.unsorted();
        if ("asc".equalsIgnoreCase(ordemPreco)) {
            sort = Sort.by(Sort.Direction.ASC, "precoCusto");
        } else if ("desc".equalsIgnoreCase(ordemPreco)) {
            sort = Sort.by(Sort.Direction.DESC, "precoCusto");
        }

        // O repositório precisará ter um método customizado com @Query 
        // ou Specification para lidar com esses filtros específicos.
        return pecaRepository.filtrarPecas(codigoPeca, nome, veiculoCompativel, precoMin, precoMax, sort)
                .stream()
                .map(PecaAutomotivaResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<PecaAutomotivaResponseDTO> listarTodos(String nome, String veiculoCompativel) {
        List<PecaAutomotiva> pecas;
        
        // Filtros simplificados sem depender da JPQL complexa do método consultarCatalogo
        if (nome != null && !nome.isBlank()) {
            pecas = pecaRepository.findByNomeContainingIgnoreCase(nome);
            if (veiculoCompativel != null && !veiculoCompativel.isBlank()) {
                pecas = pecas.stream()
                        .filter(p -> p.getVeiculosCompativeis() != null && 
                                     p.getVeiculosCompativeis().toLowerCase().contains(veiculoCompativel.toLowerCase()))
                        .collect(Collectors.toList());
            }
        } else if (veiculoCompativel != null && !veiculoCompativel.isBlank()) {
            pecas = pecaRepository.findByVeiculosCompativeisContainingIgnoreCase(veiculoCompativel);
        } else {
            pecas = pecaRepository.findAll();
        }
        
        return pecas.stream().map(PecaAutomotivaResponseDTO::new).collect(Collectors.toList());
    }

    public PecaAutomotivaResponseDTO buscarPorId(Long id) {
        PecaAutomotiva peca = pecaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Peça não encontrada com o ID: " + id));
        return new PecaAutomotivaResponseDTO(peca);
    }
}
