package br.com.alura.literalura.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Idioma {
    private String codigo;

    public Idioma() {}

    public Idioma(String codigo) {
        if (codigo == null || !codigo.matches("[a-zA-Z]{2}")) {
            throw new IllegalArgumentException("Código de idioma inválido");
        }
        this.codigo = codigo.toUpperCase();
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Idioma idioma = (Idioma) o;
        return Objects.equals(codigo, idioma.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return codigo;
    }
}