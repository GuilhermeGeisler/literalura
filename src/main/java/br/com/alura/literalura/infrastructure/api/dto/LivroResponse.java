package br.com.alura.literalura.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LivroResponse {
    @JsonAlias("title")
    private String titulo;
    @JsonAlias("authors")
    private List<AutorResponse> autores;
    @JsonAlias("languages")
    private List<String> idiomas;
    @JsonAlias("download_count")
    private Integer downloads;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<AutorResponse> getAutores() {
        return autores;
    }

    public void setAutores(List<AutorResponse> autores) {
        this.autores = autores;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }
}