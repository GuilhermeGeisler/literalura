package br.com.alura.literalura.domain.service;

import br.com.alura.literalura.domain.model.Livro;
import br.com.alura.literalura.domain.model.Autor;
import br.com.alura.literalura.domain.model.valueobjects.Idioma;

import java.util.List;

public interface LivroService {
    Livro salvarLivro(Livro livro);
    List<Livro> listarTodos();
    List<Livro> buscarPorIdioma(Idioma idioma);
    List<Livro> listarTop10PorDownloads();
    long contarPorIdioma(Idioma idioma);
    boolean existePorTituloEAutor(String titulo, Autor autor);
}