package br.com.alura.literalura.infrastructure.db.converter;

import br.com.alura.literalura.domain.model.valueobjects.Idioma;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class IdiomaConverter implements AttributeConverter<Idioma, String> {
    @Override
    public String convertToDatabaseColumn(Idioma idioma) {
        if (idioma == null) {
            return null;
        }
        return idioma.getCodigo();
    }

    @Override
    public Idioma convertToEntityAttribute(String codigo) {
        if (codigo == null) {
            return null;
        }
        return new Idioma(codigo);
    }
}