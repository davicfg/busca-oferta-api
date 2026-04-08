package alexandre_davi_miguel.busca_oferta_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import alexandre_davi_miguel.busca_oferta_api.dto.oferta.OfertaRequestDTO;
import alexandre_davi_miguel.busca_oferta_api.dto.oferta.OfertaResponseDTO;
import alexandre_davi_miguel.busca_oferta_api.service.OfertaService;

import java.util.List;

@RestController
@RequestMapping("/ofertas")
@RequiredArgsConstructor
public class OfertaController {

    private final OfertaService ofertaService;

    @PostMapping
    public ResponseEntity<OfertaResponseDTO> criar(@RequestBody OfertaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ofertaService.salvar(dto));
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<OfertaResponseDTO>> listarPorProduto(@PathVariable Long produtoId) {
        return ResponseEntity.ok(ofertaService.listarPorProduto(produtoId));
    }
}