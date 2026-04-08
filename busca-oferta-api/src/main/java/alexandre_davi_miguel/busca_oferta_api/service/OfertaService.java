package alexandre_davi_miguel.busca_oferta_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import alexandre_davi_miguel.busca_oferta_api.dto.oferta.OfertaRequestDTO;
import alexandre_davi_miguel.busca_oferta_api.dto.oferta.OfertaResponseDTO;
import alexandre_davi_miguel.busca_oferta_api.model.Loja;
import alexandre_davi_miguel.busca_oferta_api.model.Oferta;
import alexandre_davi_miguel.busca_oferta_api.model.Produto;
import alexandre_davi_miguel.busca_oferta_api.repository.LojaRepository;
import alexandre_davi_miguel.busca_oferta_api.repository.OfertaRepository;
import alexandre_davi_miguel.busca_oferta_api.repository.ProdutoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfertaService {

    private final OfertaRepository ofertaRepository;
    private final ProdutoRepository produtoRepository;
    private final LojaRepository lojaRepository;

    public OfertaResponseDTO salvar(OfertaRequestDTO dto) {
        // Busca o Produto e a Loja no banco para fazer a relação
    	
        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado")); // ToDo: Criar exceção customizada
                
        Loja loja = lojaRepository.findById(dto.lojaId())
                .orElseThrow(() -> new RuntimeException("Loja não encontrada")); // ToDo: Criar exceção customizada

        Oferta oferta = Oferta.builder()
                .produto(produto)
                .loja(loja)
                .urlDoProduto(dto.urlDoProduto())
                .build();
        
        Oferta ofertaSalva = ofertaRepository.save(oferta);
        return new OfertaResponseDTO(ofertaSalva);
    }

    public List<OfertaResponseDTO> listarPorProduto(Long produtoId) {
        return ofertaRepository.findByProdutoId(produtoId).stream()
                .map(OfertaResponseDTO::new)
                .collect(Collectors.toList());
    }
}