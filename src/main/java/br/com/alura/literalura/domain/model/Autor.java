package br.com.alura.literalura.domain.model;

import br.com.alura.literalura.domain.model.valueobjects.PeriodoVida;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Embedded
    private PeriodoVida periodoVida;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {}

    public Autor(String nome, PeriodoVida periodoVida) {
        this.nome = nome;
        this.periodoVida = periodoVida;
    }

    public void adicionarLivro(Livro livro) {
        livro.setAutor(this);
        this.livros.add(livro);
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public PeriodoVida getPeriodoVida() {
        return periodoVida;
    }

    public void setPeriodoVida(PeriodoVida periodoVida) {
        this.periodoVida = periodoVida;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        return nome +
                " (" + periodoVida.getAnoNascimento() +
                " - " + (periodoVida.getAnoFalecimento() != null ?
                periodoVida.getAnoFalecimento() : "Presente") + ")";
    }
}