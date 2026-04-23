package alexandre_davi_miguel.busca_oferta_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import alexandre_davi_miguel.busca_oferta_api.dto.produto.ProdutoRequestDTO;
import alexandre_davi_miguel.busca_oferta_api.dto.produto.ProdutoResponseDTO;
import alexandre_davi_miguel.busca_oferta_api.model.enums.CategoriaProduto;
import alexandre_davi_miguel.busca_oferta_api.service.ProdutoService;

import java.util.List;

import alexandre_davi_miguel.busca_oferta_api.dto.produto.CatalogoResponseDTO;
import java.math.BigDecimal;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping("/catalogo")
    public ResponseEntity<List<CatalogoResponseDTO>> consultarCatalogo(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) CategoriaProduto categoria,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax,
            @RequestParam(required = false) String supermercado,
            @RequestParam(required = false) String endereco,
            @RequestParam(required = false, defaultValue = "false") Boolean somenteAtivos,
            @RequestParam(required = false) String ordemPreco) {
        return ResponseEntity.ok(produtoService.consultarCatalogo(nome, categoria, precoMin, precoMax, supermercado, endereco, somenteAtivos, ordemPreco));
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@RequestBody ProdutoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.salvar(dto));
    }

    // O "required = false" faz com que seja possivel usar o Get para listar
    // todos produtos, ou caso seja passado um argumento, utilizar esse
    // argumento na busca
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listar(
            @RequestParam(required = false) CategoriaProduto categoria,
            @RequestParam(required = false) String nome) {
        return ResponseEntity.ok(produtoService.listarTodos(categoria, nome));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }
}