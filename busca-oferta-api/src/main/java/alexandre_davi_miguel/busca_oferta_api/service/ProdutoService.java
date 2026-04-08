package alexandre_davi_miguel.busca_oferta_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import alexandre_davi_miguel.busca_oferta_api.dto.produto.ProdutoRequestDTO;
import alexandre_davi_miguel.busca_oferta_api.dto.produto.ProdutoResponseDTO;
import alexandre_davi_miguel.busca_oferta_api.model.Produto;
import alexandre_davi_miguel.busca_oferta_api.repository.ProdutoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {
        Produto produto = Produto.builder()
                .nome(dto.nome())
                .categoria(dto.categoria())
                .build();
        
        Produto produtoSalvo = produtoRepository.save(produto);
        return new ProdutoResponseDTO(produtoSalvo);
    }

    public List<ProdutoResponseDTO> listarTodos(String categoria) {
        List<Produto> produtos;
        
        // UC05 - Filtragem de Produtos
        if (categoria != null && !categoria.isBlank()) {
            produtos = produtoRepository.findByCategoriaContainingIgnoreCase(categoria);
        } else {
            produtos = produtoRepository.findAll();
        }
        
        return produtos.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado")); // ToDo: Criar exceção customizada
        return new ProdutoResponseDTO(produto);
    }
}