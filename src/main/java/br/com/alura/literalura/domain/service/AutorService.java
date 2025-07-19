package br.com.alura.literalura.domain.service;

import br.com.alura.literalura.domain.model.Autor;

import java.util.List;
import java.util.Optional;

public interface AutorService {
    Autor salvarAutor(Autor autor);
    Optional<Autor> buscarPorNome(String nome);
    List<Autor> listarTodos();
    List<Autor> buscarAutoresVivosNoAno(int ano);
}