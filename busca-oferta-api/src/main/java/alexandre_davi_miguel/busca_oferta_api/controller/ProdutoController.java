package alexandre_davi_miguel.busca_oferta_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import alexandre_davi_miguel.busca_oferta_api.dto.produto.ProdutoRequestDTO;
import alexandre_davi_miguel.busca_oferta_api.dto.produto.ProdutoResponseDTO;
import alexandre_davi_miguel.busca_oferta_api.service.ProdutoService;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@RequestBody ProdutoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.salvar(dto));
    }

    // O "required = false" faz com que seja possivel usar o Get para listar
    // todos produtos, ou caso seja passado um argumento, utilizar esse
    // argumento na busca
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listar(@RequestParam(required = false) String categoria) {
        return ResponseEntity.ok(produtoService.listarTodos(categoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }
}