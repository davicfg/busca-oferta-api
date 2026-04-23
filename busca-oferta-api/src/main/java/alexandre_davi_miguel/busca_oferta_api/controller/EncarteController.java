package alexandre_davi_miguel.busca_oferta_api.controller;

import alexandre_davi_miguel.busca_oferta_api.dto.encarte.ProdutoExtraidoDTO;
import alexandre_davi_miguel.busca_oferta_api.service.ArquivoService;
import alexandre_davi_miguel.busca_oferta_api.service.EncarteIAService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/encartes")
@RequiredArgsConstructor
public class EncarteController {

    private final ArquivoService arquivoService;
    private final EncarteIAService encarteIAService;

    // Upload de PDFs
    @PostMapping("/upload")
    public ResponseEntity<String> receberPdf(@RequestParam("file") MultipartFile file, 
                                             @RequestParam("supermercadoId") Long supermercadoId) {
        try {
            arquivoService.salvarPdfPendente(file, supermercadoId);
            return ResponseEntity.ok("Encarte recebido e aguardando processamento.");
        } catch (Exception e) {
        	 e.printStackTrace(); 
             return ResponseEntity.internalServerError().body("Erro ao salvar arquivo: " + e.getMessage());
        }
    }
    
    // Envio dos PDFs para IA
    @PostMapping("/processar-lote")
    public ResponseEntity<List<ProdutoExtraidoDTO>> processarTudoDaPasta() {
        try {
            List<ProdutoExtraidoDTO> listaRevisao = encarteIAService.processarLotePendente();
            // Retorna para o Front-end para a pessoa revisar os preços na tela
            return ResponseEntity.ok(listaRevisao);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Salvar na planilha
    @PostMapping("/salvar-planilha")
    public ResponseEntity<String> salvarNaPlanilha(@RequestBody List<ProdutoExtraidoDTO> produtosRevisados) {
        try {
            arquivoService.gerarPlanilhaCsv(produtosRevisados);
            return ResponseEntity.ok("Planilha CSV gerada com sucesso!");
        } catch (Exception e) {
        	e.printStackTrace(); 
            return ResponseEntity.internalServerError().body("Erro ao gerar planilha: " + e.getMessage());
        }
    }
}