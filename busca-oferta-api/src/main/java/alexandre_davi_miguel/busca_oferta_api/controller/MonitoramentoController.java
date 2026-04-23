package alexandre_davi_miguel.busca_oferta_api.controller;

import alexandre_davi_miguel.busca_oferta_api.dto.preco.PrecoResponseDTO;
import alexandre_davi_miguel.busca_oferta_api.service.PrecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/monitoramento")
@RequiredArgsConstructor
public class MonitoramentoController {

    private final PrecoService precoService;

    @GetMapping("/historico/{produtoId}")
    public ResponseEntity<List<PrecoResponseDTO>> obterHistorico(@PathVariable Long produtoId) {
        return ResponseEntity.ok(precoService.listarHistorico(produtoId));
    }

	// To Do:
	// Endpoint para receber URL a ser monitorada
	// Endpoint para listar produtos monitorados
}
