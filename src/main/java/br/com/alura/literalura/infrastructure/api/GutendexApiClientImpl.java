package br.com.alura.literalura.infrastructure.api;

import br.com.alura.literalura.exception.ApiIntegrationException;
import br.com.alura.literalura.infrastructure.api.dto.LivroResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Component
public class GutendexApiClientImpl implements GutendexApiClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String apiUrl;

    public GutendexApiClientImpl(RestTemplate restTemplate, ObjectMapper objectMapper,
                                 @Value("${gutendex.api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.apiUrl = apiUrl;
    }

    @Override
    public Optional<LivroResponse> buscarLivroPorTitulo(String titulo) {
        try {
            // Se o usuário digitou "id:123", busca por ID
            if (titulo.toLowerCase().startsWith("id:")) {
                String id = titulo.substring(3).trim();
                return buscarPorId(Long.parseLong(id));
            }

            // Normalizar a entrada do usuário
            String query = titulo.trim().toLowerCase();

            // Construir URL com parâmetros de busca
            String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                    .queryParam("search", query)
                    .toUriString();

            System.out.println("Buscando livro na URL: " + url);

            String json = restTemplate.getForObject(url, String.class);

            if (json == null || json.isEmpty()) {
                return buscarLivroAproximado(titulo);
            }

            JsonNode root = objectMapper.readTree(json);
            JsonNode results = root.path("results");

            if (results.isEmpty() || !results.isArray() || results.size() == 0) {
                return buscarLivroAproximado(titulo);
            }

            // Tentar encontrar correspondência exata ou parcial
            for (JsonNode bookNode : results) {
                String bookTitle = bookNode.path("title").asText("").toLowerCase();
                if (bookTitle.contains(query)) {
                    return Optional.of(objectMapper.treeToValue(bookNode, LivroResponse.class));
                }
            }

            // Se não encontrou, pega o primeiro resultado
            return Optional.of(objectMapper.treeToValue(results.get(0), LivroResponse.class));

        } catch (Exception e) {
            throw new ApiIntegrationException("Erro na integração com a API Gutendex", e);
        }
    }

    private Optional<LivroResponse> buscarLivroAproximado(String titulo) {
        try {
            // Se o título tiver espaços, pega a primeira palavra
            String[] palavras = titulo.trim().split("\\s+");
            if (palavras.length == 0) return Optional.empty();

            String primeiraPalavra = palavras[0];

            // Construir URL com parâmetros de busca
            String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                    .queryParam("search", primeiraPalavra)
                    .toUriString();

            System.out.println("Buscando aproximação na URL: " + url);

            String json = restTemplate.getForObject(url, String.class);

            if (json == null || json.isEmpty()) {
                return Optional.empty();
            }

            JsonNode root = objectMapper.readTree(json);
            JsonNode results = root.path("results");

            if (results.isEmpty() || !results.isArray() || results.size() == 0) {
                return Optional.empty();
            }

            // Retorna o primeiro resultado da busca aproximada
            return Optional.of(objectMapper.treeToValue(results.get(0), LivroResponse.class));

        } catch (Exception e) {
            System.err.println("Falha na busca aproximada: " + e.getMessage());
            return Optional.empty();
        }
    }

    private Optional<LivroResponse> buscarPorId(Long id) {
        try {
            String url = apiUrl + "/" + id + "/";
            System.out.println("Buscando livro por ID: " + url);
            String json = restTemplate.getForObject(url, String.class);
            return Optional.of(objectMapper.readValue(json, LivroResponse.class));
        } catch (Exception e) {
            System.err.println("Livro não encontrado por ID: " + e.getMessage());
            return Optional.empty();
        }
    }
}