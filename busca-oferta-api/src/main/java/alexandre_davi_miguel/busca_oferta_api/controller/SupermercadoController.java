package alexandre_davi_miguel.busca_oferta_api.controller;

import alexandre_davi_miguel.busca_oferta_api.dto.supermercado.SupermercadoDTO;
import alexandre_davi_miguel.busca_oferta_api.service.SupermercadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/supermercados")
@RequiredArgsConstructor
public class SupermercadoController {

    private final SupermercadoService supermercadoService;

    @GetMapping
    public ResponseEntity<List<SupermercadoDTO>> listar() {
        return ResponseEntity.ok(supermercadoService.listarTodos());
    }
}