package br.com.alura.literalura.domain.service.impl;

import br.com.alura.literalura.domain.model.Autor;
import br.com.alura.literalura.domain.repository.AutorRepository;
import br.com.alura.literalura.domain.service.AutorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AutorServiceImpl implements AutorService {

    private final AutorRepository autorRepository;

    public AutorServiceImpl(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Override
    @Transactional
    public Autor salvarAutor(Autor autor) {
        return autorRepository.findByNome(autor.getNome())
                .orElseGet(() -> autorRepository.save(autor));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Autor> buscarPorNome(String nome) {
        return autorRepository.findByNome(nome);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Autor> buscarAutoresVivosNoAno(int ano) {
        return autorRepository.findAutoresVivosNoAno(ano);
    }
}