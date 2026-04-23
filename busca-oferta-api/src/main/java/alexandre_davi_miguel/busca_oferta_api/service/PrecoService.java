package alexandre_davi_miguel.busca_oferta_api.service;

import alexandre_davi_miguel.busca_oferta_api.exception.ResourceNotFoundException;
import alexandre_davi_miguel.busca_oferta_api.model.Supermercado;
import alexandre_davi_miguel.busca_oferta_api.repository.SupermercadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import alexandre_davi_miguel.busca_oferta_api.dto.preco.PrecoRequestDTO;
import alexandre_davi_miguel.busca_oferta_api.dto.preco.PrecoResponseDTO;
import alexandre_davi_miguel.busca_oferta_api.model.Preco;
import alexandre_davi_miguel.busca_oferta_api.model.Produto;
import alexandre_davi_miguel.busca_oferta_api.repository.PrecoRepository;
import alexandre_davi_miguel.busca_oferta_api.repository.ProdutoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrecoService {

    private final PrecoRepository precoRepository;
    private final ProdutoRepository produtoRepository;
    private final SupermercadoRepository supermercadoRepository;

    public PrecoResponseDTO salvar(PrecoRequestDTO dto) {
        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + dto.produtoId()));

        Supermercado supermercado = supermercadoRepository.findById(dto.supermercadoId())
                .orElseThrow(() -> new ResourceNotFoundException("Supermercado não encontrado com o ID: " + dto.supermercadoId()));

        Preco preco = Preco.builder()
                .produto(produto)
                .supermercado(supermercado)
                .valor(dto.valor())
                .dataInicio(dto.dataInicio())
                .dataFim(dto.dataFim())
                .build();

        Preco precoSalvo = precoRepository.save(preco);
        return new PrecoResponseDTO(precoSalvo);
    }

    public PrecoResponseDTO buscarPorId(Long id) {
        Preco preco = precoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Preço não encontrado com o ID: " + id));
        return new PrecoResponseDTO(preco);
    }

    public List<PrecoResponseDTO> listarPorProduto(Long produtoId) {
        List<Preco> precos = precoRepository.findByProdutoId(produtoId);
        return precos.stream().map(PrecoResponseDTO::new).collect(Collectors.toList());
    }

    public List<PrecoResponseDTO> listarTodos() {
        List<Preco> precos = precoRepository.findAll();
        return precos.stream().map(PrecoResponseDTO::new).collect(Collectors.toList());
    }
}
