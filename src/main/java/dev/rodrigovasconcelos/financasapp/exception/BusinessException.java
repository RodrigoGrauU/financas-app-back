package dev.rodrigovasconcelos.financasapp.exception;

public class BusinessException extends RuntimeException {

    public BusinessException() {
        super();
    }

    public BusinessException(String mensagemException) {
        super(mensagemException);
    }
}
