package alexandre_davi_miguel.busca_oferta_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import alexandre_davi_miguel.busca_oferta_api.dto.produto.ProdutoRequestDTO;
import alexandre_davi_miguel.busca_oferta_api.dto.produto.ProdutoResponseDTO;
import alexandre_davi_miguel.busca_oferta_api.exception.EntidadeNaoEncontradaException;
import alexandre_davi_miguel.busca_oferta_api.model.Produto;
import alexandre_davi_miguel.busca_oferta_api.model.enums.CategoriaProduto;
import alexandre_davi_miguel.busca_oferta_api.repository.ProdutoRepository;

import java.util.List;
import java.util.stream.Collectors;

import alexandre_davi_miguel.busca_oferta_api.dto.produto.CatalogoResponseDTO;
import alexandre_davi_miguel.busca_oferta_api.repository.PrecoRepository;
import java.math.BigDecimal;

import java.time.LocalDate;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final PrecoRepository precoRepository;

    public List<CatalogoResponseDTO> consultarCatalogo(
            String nome, CategoriaProduto categoria, 
            BigDecimal precoMin, BigDecimal precoMax, 
            String supermercado, String endereco,
            Boolean somenteAtivos, String ordemPreco) {
        
        LocalDate dataReferencia = (somenteAtivos != null && somenteAtivos) ? LocalDate.now() : null;
        
        Sort sort = Sort.unsorted();
        if ("asc".equalsIgnoreCase(ordemPreco)) {
            sort = Sort.by(Sort.Direction.ASC, "valor");
        } else if ("desc".equalsIgnoreCase(ordemPreco)) {
            sort = Sort.by(Sort.Direction.DESC, "valor");
        }

        return precoRepository.filtrarOfertas(nome, categoria, precoMin, precoMax, supermercado, endereco, dataReferencia, sort)
                .stream()
                .map(CatalogoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {
        Produto produto = Produto.builder()
                .nome(dto.nome())
                .categoria(dto.categoria())
                .build();
        
        Produto produtoSalvo = produtoRepository.save(produto);
        return new ProdutoResponseDTO(produtoSalvo);
    }

    public List<ProdutoResponseDTO> listarTodos(CategoriaProduto categoria, String nome) {
        List<Produto> produtos;
        
        if (nome != null && !nome.isBlank()) {
            produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
            // Optionally filter the result by category if both are provided
            if (categoria != null) {
                produtos = produtos.stream()
                        .filter(p -> p.getCategoria() == categoria)
                        .collect(Collectors.toList());
            }
        } else if (categoria != null) {
            produtos = produtoRepository.findByCategoria(categoria);
        } else {
            produtos = produtoRepository.findAll();
        }
        
        return produtos.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado com o ID: " + id));
        return new ProdutoResponseDTO(produto);
    }
}