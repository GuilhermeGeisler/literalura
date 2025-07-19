# 📚 Literalura  

## 📖 Sobre o Projeto  
Aplicação Java para explorar dados literários utilizando a API Gutendex. Permite buscar, visualizar e salvar informações sobre livros, autores e idiomas em um banco de dados local com arquitetura modular.  

**Recursos Principais:**  
- 🔍 Busca de livros por título na API Gutendex  
- 💾 Armazenamento local de dados literários  
- 📊 Listagem de autores vivos em determinado ano  
- 🌐 Filtragem de livros por idioma  
- 🧩 Arquitetura em camadas (domain, application, infrastructure)  

---

## 🚀 Funcionalidades  

| **Operações Básicas**           | **Recursos Técnicos**             |  
|----------------------------------|------------------------------------|  
| ✅ Busca de livros por API       | 🧠 Value Objects (Idioma, PeriodoVida) |  
| ✅ Persistência em banco de dados| 🛡️ Tratamento customizado de exceções |  
| ✅ Consulta de autores por período| 🔄 Conversores de dados personalizados |  
| ✅ Interface de terminal amigável| 📦 Injeção de dependências com Spring |  

---

## 🛠️ Tecnologias Utilizadas  
<div align="center">  
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 17">  
  <img src="https://img.shields.io/badge/Spring_Boot-3.2-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot">  
  <img src="https://img.shields.io/badge/Hibernate-6.4-59666C?style=for-the-badge&logo=hibernate&logoColor=white" alt="Hibernate">  
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL">  
</div>  

---

## 📂 Estrutura do Projeto  
```
src/main/java/br/com/alura/literalura
├── LiterAluraApplication.java # Ponto de entrada
├── config/
│ ├── ObjectMapperConfig.java # Configuração JSON
│ └── RestTemplateConfig.java # Configuração HTTP
├── domain/
│ ├── model/
│ │ ├── Autor.java # Entidade Autor
│ │ ├── Livro.java # Entidade Livro
│ │ └── valueobjects/
│ │ ├── Idioma.java # VO para idiomas
│ │ └── PeriodoVida.java # VO para período de vida
│ ├── repository/ # Repositórios JPA
│ │ ├── AutorRepository.java
│ │ └── LivroRepository.java
│ └── service/ # Serviços de domínio
│ ├── AutorService.java
│ ├── LivroService.java
│ └── impl/
│ ├── AutorServiceImpl.java
│ └── LivroServiceImpl.java
├── application/
│ └── service/ # Serviços de aplicação
│ ├── CatalogoService.java
│ └── impl/
│ └── CatalogoServiceImpl.java
├── infrastructure/
│ ├── api/ # Integração com API externa
│ │ ├── GutendexApiClient.java
│ │ ├── GutendexApiClientImpl.java
│ │ └── dto/ # DTOs da API
│ │ ├── AutorResponse.java
│ │ └── LivroResponse.java
│ └── db/
│ └── converter/ # Conversores JPA
│ └── IdiomaConverter.java
├── ui/ # Interface do usuário
│ └── ConsoleUI.java
└── exception/ # Exceções customizadas
├── ApiIntegrationException.java
└── EntityNotFoundException.java
```

---

## ⚙️ Como Executar

**Pré-requisitos:**
- Java 17+
- Maven 3.6+

1. **Clone o repositório:**  
```bash
git clone https://github.com/GuilhermeGeisler/literalura.git
```
2. **Navegue até o diretório:**
```bash
cd literalura
```
3. **Execute a aplicação:**
```bash
mvn spring-boot:run
```

---

## 🧠 Padrões de Projeto
- DDD (Domain-Driven Design): Separação em camadas

- Value Objects: Idioma, PeriodoVida

- Repository Pattern: Acesso a dados

- DTO Pattern: Transferência de dados externos

- Dependency Injection: Inversão de controle

---

## 🧑‍💻 Desenvolvedor

<table>
  <tr>
    <td align="center">
      <a href="https://www.linkedin.com/in/guilhermegeisler/">
        <img src="https://avatars.githubusercontent.com/u/53203780?s=400&u=9a85ac6d2d3c55a872ab0bafd1d38d8bd0da5cc4&v=4" width="100px;" alt="Foto do Guilherme Geisler"/><br>
        <sub>
          <b>Guilherme Geisler</b>
        </sub>
      </a>
    </td>
  </tr>
</table>

---

## 📧 Contato

Se tiver alguma dúvida, sugestão ou quiser entrar em contato, fique à vontade:  

- **LinkedIn**: [Guilherme Geisler](https://www.linkedin.com/in/guilhermegeisler/)  
- **Email**: [guilherme.sgeisler@gmail.com](mailto:guilherme.sgeisler@gmail.com)  

---

Feito com ❤️ por [Guilherme Geisler](https://www.linkedin.com/in/guilhermegeisler/).
