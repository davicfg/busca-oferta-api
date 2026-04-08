package alexandre_davi_miguel.busca_oferta_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import alexandre_davi_miguel.busca_oferta_api.dto.loja.LojaRequestDTO;
import alexandre_davi_miguel.busca_oferta_api.dto.loja.LojaResponseDTO;
import alexandre_davi_miguel.busca_oferta_api.service.LojaService;

import java.util.List;

@RestController
@RequestMapping("/lojas")
@RequiredArgsConstructor
public class LojaController {

    private final LojaService lojaService;

    @PostMapping
    public ResponseEntity<LojaResponseDTO> criar(@RequestBody LojaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lojaService.salvar(dto));
    }

    @GetMapping
    public ResponseEntity<List<LojaResponseDTO>> listar() {
        return ResponseEntity.ok(lojaService.listarTodas());
    }
}