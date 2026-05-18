package alexandre_davi_miguel.busca_oferta_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import alexandre_davi_miguel.busca_oferta_api.dto.produto.ProdutoResponseDTO;
import alexandre_davi_miguel.busca_oferta_api.service.FavoritoService;
import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioId}/favoritos")
@RequiredArgsConstructor

public class FavoritoController {
    
    private final FavoritoService favoritoService;

    @PostMapping("/{produtoId}")
    public ResponseEntity<Void> favoritar(@PathVariable Long usuarioId, @PathVariable Long produtoId) {
        favoritoService.adicionarFavorito(usuarioId, produtoId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @DeleteMapping("/{produtoId}")
    public ResponseEntity<Void> desfavoritar(@PathVariable Long usuarioId, @PathVariable Long produtoId) {
        favoritoService.removerFavorito(usuarioId, produtoId);
        return ResponseEntity.noContent().build(); 
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listar(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(favoritoService.listarFavoritos(usuarioId));
    }
}
