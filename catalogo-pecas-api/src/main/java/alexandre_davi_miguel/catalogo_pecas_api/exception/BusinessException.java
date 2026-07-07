package alexandre_davi_miguel.catalogo_pecas_api.exception;

public class BusinessException extends RuntimeException {
    public BusinessException (String mensagem) {
        super(mensagem);
    } 
}
