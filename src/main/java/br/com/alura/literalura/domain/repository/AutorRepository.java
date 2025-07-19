package br.com.alura.literalura.domain.repository;

import br.com.alura.literalura.domain.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNome(String nome);

    @Query("SELECT a FROM Autor a WHERE :ano BETWEEN a.periodoVida.anoNascimento AND a.periodoVida.anoFalecimento OR (a.periodoVida.anoNascimento <= :ano AND a.periodoVida.anoFalecimento IS NULL)")
    List<Autor> findAutoresVivosNoAno(Integer ano);

    @Query("SELECT a FROM Autor a ORDER BY SIZE(a.livros) DESC")
    List<Autor> findAllOrderByQuantidadeLivros();
}