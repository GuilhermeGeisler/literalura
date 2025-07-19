package br.com.alura.literalura.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName, String criteria) {
        super(entityName + " não encontrado com critério: " + criteria);
    }
}