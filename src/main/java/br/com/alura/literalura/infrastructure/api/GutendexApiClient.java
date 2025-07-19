package br.com.alura.literalura.infrastructure.api;

import br.com.alura.literalura.infrastructure.api.dto.LivroResponse;

import java.util.Optional;

public interface GutendexApiClient {
    Optional<LivroResponse> buscarLivroPorTitulo(String titulo);
}