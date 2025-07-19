package br.com.alura.literalura.application.service;

import br.com.alura.literalura.domain.model.Autor;
import br.com.alura.literalura.domain.model.Livro;
import br.com.alura.literalura.domain.model.valueobjects.Idioma;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CatalogoService {
    Optional<Livro> buscarESalvarLivro(String titulo);
    List<Livro> listarLivrosRegistrados();
    List<Autor> listarAutores();
    List<Autor> buscarAutoresVivosNoAno(int ano);
    List<Livro> buscarLivrosPorIdioma(String idioma);
    Map<String, Long> obterEstatisticasIdioma();
    List<Livro> listarTop10Livros();
    List<Autor> buscarAutoresPorNome(String nome);

    Livro salvarLivro(Livro livro);
}