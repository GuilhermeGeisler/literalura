package br.com.alura.literalura.ui;

import br.com.alura.literalura.application.service.CatalogoService;
import br.com.alura.literalura.domain.model.Autor;
import br.com.alura.literalura.domain.model.Livro;
import br.com.alura.literalura.domain.model.valueobjects.Idioma;
import br.com.alura.literalura.domain.model.valueobjects.PeriodoVida;
import br.com.alura.literalura.infrastructure.api.GutendexApiClient;
import br.com.alura.literalura.infrastructure.api.dto.AutorResponse;
import br.com.alura.literalura.infrastructure.api.dto.LivroResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

@Component
public class ConsoleUI implements CommandLineRunner {

    private final CatalogoService catalogoService;
    private final GutendexApiClient apiClient;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(CatalogoService catalogoService, GutendexApiClient apiClient) {
        this.catalogoService = catalogoService;
        this.apiClient = apiClient;
    }

    @Override
    public void run(String... args) {
        exibirMenu();
    }

    private void exibirMenu() {
        while (true) {
            limparConsole();
            exibirCabecalho();

            System.out.println("1. Buscar livro pelo título");
            System.out.println("2. Listar livros registrados");
            System.out.println("3. Listar autores");
            System.out.println("4. Buscar autores vivos em determinado ano");
            System.out.println("5. Listar livros por idioma");
            System.out.println("6. Exibir estatísticas");
            System.out.println("7. Top 10 livros mais baixados");
            System.out.println("8. Buscar autor por nome");
            System.out.println("9. Adicionar livro manualmente");
            System.out.println("0. Sair");

            int opcao = lerInteiro("\nEscolha uma opção: ");

            switch (opcao) {
                case 1 -> buscarLivroPorTitulo();
                case 2 -> listarLivrosRegistrados();
                case 3 -> listarAutores();
                case 4 -> buscarAutoresPorAno();
                case 5 -> listarLivrosPorIdioma();
                case 6 -> exibirEstatisticas();
                case 7 -> exibirTop10();
                case 8 -> buscarAutorPorNome();
                case 9 -> adicionarLivroManualmente();
                case 0 -> {
                    System.out.println("\nObrigado por usar o LiterAlura! Até mais!\n");
                    return;
                }
                default -> System.out.println("\nOpção inválida! Tente novamente.");
            }

            aguardarEnter();
        }
    }

    private void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void exibirCabecalho() {
        System.out.println("""
                \n\u001B[36m
                ██╗     ██╗████████╗███████╗██████╗  █████╗ ██╗     ██╗   ██╗██████╗  █████╗ 
                ██║     ██║╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██║     ██║   ██║██╔══██╗██╔══██╗
                ██║     ██║   ██║   █████╗  ██████╔╝███████║██║     ██║   ██║██████╔╝███████║
                ██║     ██║   ██║   ██╔══╝  ██╔══██╗██╔══██║██║     ██║   ██║██╔══██╗██╔══██║
                ███████╗██║   ██║   ███████╗██║  ██║██║  ██║███████╗╚██████╔╝██║  ██║██║  ██║
                ╚══════╝╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝
                \u001B[0m
                """);
    }

    private void buscarLivroPorTitulo() {
        System.out.print("Digite o título do livro (ou 'id:NUMERO' para buscar por ID): ");
        String titulo = scanner.nextLine();

        if (titulo.isBlank()) {
            System.out.println("\n\u001B[31mTítulo não pode ser vazio!\u001B[0m");
            return;
        }

        System.out.println("Processando busca por: " + titulo);

        // Busca o livro na API
        Optional<LivroResponse> livroResponseOpt = apiClient.buscarLivroPorTitulo(titulo);

        if (livroResponseOpt.isPresent()) {
            LivroResponse livroResponse = livroResponseOpt.get();

            // Converter para o modelo de domínio
            Livro livro = converterParaLivro(livroResponse);

            System.out.println("\n\u001B[32mLivro encontrado:\u001B[0m");
            System.out.println("Título: " + livro.getTitulo());
            System.out.println("Autor: " + livro.getAutor().getNome());
            System.out.println("Idioma: " + livro.getIdioma().getCodigo());
            System.out.println("Downloads: " + livro.getDownloads());

            System.out.print("\nDeseja salvar este livro? (S/N): ");
            String confirmacao = scanner.nextLine();
            if (confirmacao.equalsIgnoreCase("S")) {
                try {
                    Livro livroSalvo = catalogoService.salvarLivro(livro);
                    System.out.println("\n\u001B[32mLivro salvo com sucesso!\u001B[0m");
                    System.out.println("ID: " + livroSalvo.getId());
                } catch (Exception e) {
                    System.out.println("\n\u001B[31mErro ao salvar livro: " + e.getMessage() + "\u001B[0m");
                }
            }
        } else {
            System.out.println("\n\u001B[31mNenhum resultado encontrado para: " + titulo + "\u001B[0m");
            System.out.println("Sugestões:");
            System.out.println("1. Verifique a ortografia");
            System.out.println("2. Tente o título original em inglês");
            System.out.println("3. Use palavras-chave mais genéricas");
            System.out.println("4. Busque por ID: 'id:123'");
            System.out.println("5. Adicione manualmente (opção 9)");
        }
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

    private void listarLivrosRegistrados() {
        List<Livro> livros = catalogoService.listarLivrosRegistrados();
        if (livros.isEmpty()) {
            System.out.println("\nNenhum livro registrado no banco de dados.");
            return;
        }

        System.out.println("\n\u001B[34mLivros registrados:\u001B[0m");
        livros.forEach(livro -> System.out.println(" - " + livro));
    }

    private void listarAutores() {
        List<Autor> autores = catalogoService.listarAutores();
        if (autores.isEmpty()) {
            System.out.println("\nNenhum autor registrado no banco de dados.");
            return;
        }

        System.out.println("\n\u001B[34mAutores registrados:\u001B[0m");
        autores.forEach(autor -> System.out.println(" - " + autor));
    }

    private void buscarAutoresPorAno() {
        int ano = lerInteiro("Digite o ano: ");
        List<Autor> autores = catalogoService.buscarAutoresVivosNoAno(ano);

        if (autores.isEmpty()) {
            System.out.println("\nNenhum autor vivo encontrado no ano " + ano + ".");
            return;
        }

        System.out.println("\n\u001B[34mAutores vivos em " + ano + ":\u001B[0m");
        autores.forEach(autor -> System.out.println(" - " + autor));
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
            \nIdiomas disponíveis:
            PT - Português
            EN - Inglês
            ES - Espanhol
            FR - Francês
            DE - Alemão
            IT - Italiano
            """);

        System.out.print("Digite o código do idioma: ");
        String idioma = scanner.nextLine().toUpperCase();
        List<Livro> livros = catalogoService.buscarLivrosPorIdioma(idioma);

        if (livros.isEmpty()) {
            System.out.println("\nNenhum livro encontrado no idioma " + idioma + ".");
            return;
        }

        System.out.println("\n\u001B[34mLivros em " + idioma + ":\u001B[0m");
        livros.forEach(livro -> System.out.println(" - " + livro));
    }

    private void exibirEstatisticas() {
        Map<String, Long> estatisticas = catalogoService.obterEstatisticasIdioma();

        if (estatisticas.isEmpty()) {
            System.out.println("\nNão há dados estatísticos disponíveis.");
            return;
        }

        System.out.println("\n\u001B[35mEstatísticas por idioma:\u001B[0m");
        estatisticas.forEach((idioma, quantidade) ->
                System.out.printf(" - %s: %d livro(s)%n", idioma, quantidade));
    }

    private void exibirTop10() {
        List<Livro> top10 = catalogoService.listarTop10Livros();

        if (top10.isEmpty()) {
            System.out.println("\nNão há livros registrados.");
            return;
        }

        System.out.println("\n\u001B[35mTop 10 livros mais baixados:\u001B[0m");
        for (int i = 0; i < top10.size(); i++) {
            Livro livro = top10.get(i);
            System.out.printf("%2d. %s - %s (%d downloads)%n",
                    i + 1, livro.getTitulo(), livro.getAutor().getNome(), livro.getDownloads());
        }
    }

    private void buscarAutorPorNome() {
        System.out.print("Digite o nome do autor: ");
        String nome = scanner.nextLine();
        List<Autor> autores = catalogoService.buscarAutoresPorNome(nome);

        if (autores.isEmpty()) {
            System.out.println("\nNenhum autor encontrado com esse nome.");
            return;
        }

        System.out.println("\n\u001B[34mAutores encontrados:\u001B[0m");
        autores.forEach(autor -> {
            System.out.println(" - " + autor);
            System.out.println("   Livros:");
            autor.getLivros().forEach(livro -> System.out.println("      - " + livro.getTitulo()));
        });
    }

    private void adicionarLivroManualmente() {
        System.out.println("\n--- Adicionar Livro Manualmente ---");

        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Autor: ");
        String nomeAutor = scanner.nextLine();

        System.out.print("Ano Nascimento Autor: ");
        int anoNascimento = lerInteiro("");

        System.out.print("Ano Falecimento Autor (0 se ainda vivo): ");
        int anoFalecimentoInput = lerInteiro("");
        Integer anoFalecimento = anoFalecimentoInput == 0 ? null : anoFalecimentoInput;

        System.out.print("Idioma (ex: PT, EN): ");
        String codigoIdioma = scanner.nextLine();

        System.out.print("Número de Downloads: ");
        int downloads = lerInteiro("");

        PeriodoVida periodo = new PeriodoVida(anoNascimento, anoFalecimento);
        Autor autor = new Autor(nomeAutor, periodo);
        Idioma idioma = new Idioma(codigoIdioma);

        Livro livro = new Livro(titulo, idioma, downloads, autor);

        try {
            Livro salvo = catalogoService.salvarLivro(livro);
            System.out.println("\n\u001B[32mLivro salvo com sucesso! ID: " + salvo.getId() + "\u001B[0m");
        } catch (Exception e) {
            System.out.println("\n\u001B[31mErro ao salvar: " + e.getMessage() + "\u001B[0m");
        }
    }

    private int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Por favor, digite um número válido: ");
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer
        return valor;
    }

    private void aguardarEnter() {
        System.out.println("\nPressione Enter para continuar...");
        scanner.nextLine();
    }
}