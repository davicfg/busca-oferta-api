package alexandre_davi_miguel.busca_oferta_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import alexandre_davi_miguel.busca_oferta_api.dto.preco.PrecoRequestDTO;
import alexandre_davi_miguel.busca_oferta_api.dto.preco.PrecoResponseDTO;
import alexandre_davi_miguel.busca_oferta_api.service.PrecoService;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/precos")
@RequiredArgsConstructor
public class PrecoController {

    private final PrecoService precoService;

    @PostMapping
    public ResponseEntity<PrecoResponseDTO> criar(@RequestBody @Valid PrecoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(precoService.salvar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrecoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(precoService.buscarPorId(id));
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<PrecoResponseDTO>> listarPorProduto(@PathVariable Long produtoId) {
        return ResponseEntity.ok(precoService.listarPorProduto(produtoId));
    }

    @GetMapping
    public ResponseEntity<List<PrecoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(precoService.listarTodos());
    }
    
}
