package alexandre_davi_miguel.catalogo_pecas_api.controller;  

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import alexandre_davi_miguel.catalogo_pecas_api.dto.PecaExtraidaDTO;
import alexandre_davi_miguel.catalogo_pecas_api.service.PecaAutomotivaService;

import java.util.List;
import java.math.BigDecimal;

@RestController
@RequestMapping("/pecas")
@RequiredArgsConstructor
public class PecaAutomotivaController {

    private final PecaAutomotivaService pecaAutomotivaService;

    @GetMapping("/catalogo")
    public ResponseEntity<List<PecaExtraidaDTO>> consultarCatalogo(
            @RequestParam(required = false) String codigoPeca,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String veiculoCompativel,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax,
            @RequestParam(required = false) String ordemPreco) {
        
        return ResponseEntity.ok(pecaAutomotivaService.consultarCatalogo(
                codigoPeca, nome, veiculoCompativel, precoMin, precoMax, ordemPreco
        ));
    }

    @PostMapping
    public ResponseEntity<PecaExtraidaDTO> criar(@RequestBody PecaExtraidaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pecaAutomotivaService.salvar(dto));
    }

    @GetMapping
    public ResponseEntity<List<PecaExtraidaDTO>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String veiculoCompativel) {
        return ResponseEntity.ok(pecaAutomotivaService.listarTodos(nome, veiculoCompativel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PecaExtraidaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pecaAutomotivaService.buscarPorId(id));
    }
}