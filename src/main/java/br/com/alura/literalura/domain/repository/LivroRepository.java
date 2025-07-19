package br.com.alura.literalura.domain.repository;

import br.com.alura.literalura.domain.model.Autor;
import br.com.alura.literalura.domain.model.Livro;
import br.com.alura.literalura.domain.model.valueobjects.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    List<Livro> findByIdioma(Idioma idioma);
    Long countByIdioma(Idioma idioma);

    @Query("SELECT l FROM Livro l ORDER BY l.downloads DESC LIMIT 10")
    List<Livro> findTop10ByOrderByDownloadsDesc();

    @Query("SELECT l.idioma, COUNT(l) FROM Livro l GROUP BY l.idioma")
    List<Object[]> countLivrosPorIdioma();

    boolean existsByTituloAndAutor(String titulo, Autor autor);
}