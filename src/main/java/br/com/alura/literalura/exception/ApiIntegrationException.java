package br.com.alura.literalura.exception;

public class ApiIntegrationException extends RuntimeException {
    public ApiIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}