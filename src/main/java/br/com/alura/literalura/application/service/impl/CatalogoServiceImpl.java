package br.com.alura.literalura.application.service.impl;

import br.com.alura.literalura.application.service.CatalogoService;
import br.com.alura.literalura.domain.model.Autor;
import br.com.alura.literalura.domain.model.Livro;
import br.com.alura.literalura.domain.model.valueobjects.Idioma;
import br.com.alura.literalura.domain.model.valueobjects.PeriodoVida;
import br.com.alura.literalura.domain.service.AutorService;
import br.com.alura.literalura.domain.service.LivroService;
import br.com.alura.literalura.infrastructure.api.GutendexApiClient;
import br.com.alura.literalura.infrastructure.api.dto.AutorResponse;
import br.com.alura.literalura.infrastructure.api.dto.LivroResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CatalogoServiceImpl implements CatalogoService {

    private final GutendexApiClient apiClient;
    private final LivroService livroService;
    private final AutorService autorService;

    public CatalogoServiceImpl(GutendexApiClient apiClient,
                               LivroService livroService,
                               AutorService autorService) {
        this.apiClient = apiClient;
        this.livroService = livroService;
        this.autorService = autorService;
    }

    @Override
    @Transactional
    public Optional<Livro> buscarESalvarLivro(String titulo) {
        return apiClient.buscarLivroPorTitulo(titulo)
                .map(this::converterParaLivro)
                .map(livro -> {
                    Autor autor = livro.getAutor();
                    autorService.salvarAutor(autor);
                    return livroService.salvarLivro(livro);
                });
    }

    private Livro converterParaLivro(LivroResponse livroResponse) {
        AutorResponse autorResponse = livroResponse.getAutores().get(0);
        PeriodoVida periodoVida = new PeriodoVida(
                autorResponse.getAnoNascimento(),
                autorResponse.getAnoFalecimento()
        );
        Autor autor = new Autor(autorResponse.getNome(), periodoVida);
        Idioma idioma = new Idioma(livroResponse.getIdiomas().get(0));
        return new Livro(
                livroResponse.getTitulo(),
                idioma,
                livroResponse.getDownloads(),
                autor
        );
    }

    @Override
    @Transactional
    public Livro salvarLivro(Livro livro) {
        Autor autorSalvo = autorService.salvarAutor(livro.getAutor());
        livro.setAutor(autorSalvo);
        return livroService.salvarLivro(livro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Livro> listarLivrosRegistrados() {
        return livroService.listarTodos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Autor> listarAutores() {
        return autorService.listarTodos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Autor> buscarAutoresVivosNoAno(int ano) {
        return autorService.buscarAutoresVivosNoAno(ano);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Livro> buscarLivrosPorIdioma(String idiomaStr) {
        Idioma idioma = new Idioma(idiomaStr);
        return livroService.buscarPorIdioma(idioma);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> obterEstatisticasIdioma() {
        return livroService.listarTodos().stream()
                .collect(Collectors.groupingBy(
                        livro -> livro.getIdioma().getCodigo(),
                        Collectors.counting()
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Livro> listarTop10Livros() {
        return livroService.listarTop10PorDownloads();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Autor> buscarAutoresPorNome(String nome) {
        return autorService.listarTodos().stream()
                .filter(autor -> autor.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }
}