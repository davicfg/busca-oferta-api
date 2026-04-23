package alexandre_davi_miguel.busca_oferta_api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

import alexandre_davi_miguel.busca_oferta_api.exception.*; 

@RestControllerAdvice
public class ApiExceptionHandler {
    
    // 1 - Entidade não encontrada: Erro 404
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<String> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // 2 - Conflito de entidades: Erro 409 ou 400
    @ExceptionHandler(QuebraUnicidadeException.class)
    public ResponseEntity<String> handleQuebraUnicidade(QuebraUnicidadeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage()); 
    }

    // 3 - Erro genérico 
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Erro interno geral: " + ex.getMessage()); 
    }
}