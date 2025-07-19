package br.com.alura.literalura.domain.service.impl;

import br.com.alura.literalura.domain.model.Autor;
import br.com.alura.literalura.domain.model.Livro;
import br.com.alura.literalura.domain.model.valueobjects.Idioma;
import br.com.alura.literalura.domain.repository.LivroRepository;
import br.com.alura.literalura.domain.service.LivroService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LivroServiceImpl implements LivroService {

    private final LivroRepository livroRepository;

    public LivroServiceImpl(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @Override
    @Transactional
    public Livro salvarLivro(Livro livro) {
        if (livroRepository.existsByTituloAndAutor(livro.getTitulo(), livro.getAutor())) {
            throw new IllegalStateException("Livro já registrado");
        }
        return livroRepository.save(livro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Livro> buscarPorIdioma(Idioma idioma) {
        return livroRepository.findByIdioma(idioma);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Livro> listarTop10PorDownloads() {
        return livroRepository.findTop10ByOrderByDownloadsDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public long contarPorIdioma(Idioma idioma) {
        return livroRepository.countByIdioma(idioma);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorTituloEAutor(String titulo, Autor autor) {
        return livroRepository.existsByTituloAndAutor(titulo, autor);
    }
}