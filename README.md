![image](https://user-images.githubusercontent.com/29494780/189982427-46da069f-bb06-49ed-a277-6b59b15d131b.png)
# Desafio Pessoa Desenvolvedora Back-end - Projeto School

## Sobre

Esta é uma API que tem o objetivo de gerenciar usuários, cursos, matrículas, aulas e vídeos. São utilizadas as tecnologias:

- Java 11
- Maven 3+
- Spring Boot
- Spring Web
- Bean Validation
- Spring Data JPA
- [Flyway](https://www.baeldung.com/database-migrations-with-flyway)
- [H2](https://www.baeldung.com/spring-boot-h2-database), o BD relacional em memória

## Orientações
1. Suba o projeto no seu github e deixe o repositório público (Seus commits serão avaliados)
2. Abra o projeto na IDE da sua preferência.
3. Execute os testes automatizados.
4. Suba a aplicação e explore a API com uma ferramenta como [cURL](https://curl.se/), [Insomnia](https://insomnia.rest/), [Postman](https://www.postman.com/).
5. Os seguintes endpoints estão implementados:

- `GET /users/{username}` obtém os detalhes de um usuário
- `POST /users` adiciona um novo usuário
- `GET /courses` lista os cursos já cadastrado
- `POST /courses` adiciona um novo curso
- `GET /courses/{code}` obtém os detalhes de um curso

## Avaliação
Avaliaremos seu desafio de código com base em alguns atributos de qualidade do sistema. Alguns consideramos obrigatórios, como a correção, e serão avaliados em uma abordagem binária (funciona/segue ou não). Estes são todos os atributos de qualidade que esperamos que você aborde:

- Correção: Seu código deve seguir todos os requisitos obrigatórios apresentados nas próximas sessões;
- Testabilidade: quão bem testado é o seu código e quão fácil é adicionar novos testes ao seu código;
- Manutenibilidade: Como é fácil adicionar recursos extras ao seu código;
- Separação de preocupações: (https://en.wikipedia.org/wiki/Separation_of_concerns)

## Tarefas
Devem ser implementadas as seguintes tarefas.

### 1. Correção dos testes automatizados
- Nem todos os testes automatizados que já existem no projeto estão passando.
- Altere o código de produção para que todos os testes automatizados passem.

Observação: **NÃO** modifique os testes atuais do projeto!

### 2. Aulas e Videos
#### 2.1 Implementar aulas no curso

- Um curso pode conter **várias aulas**, e cada aula pode ter **mais de um vídeo**
- Implemente um endpoint no projeto que recebe um `POST` em `/courses/{code}/sections` para salvar uma aula.

```
POST /courses/{code}/sections
Content-Type: application/json
{
    "code": "flutter-cores-dinamicas",
    "title": "Flutter: Configurando cores dinâmicas",
    "authorUsername": "alex"
}
```
##### Regras
- O título, code e autor são obrigatórios, caso não exista, retorne uma mensagem de erro
- O título deve ter no mínimo 5 caracteres
- O campo code, deve ser único **por curso**
- Caso o curso ou autor não existam, retorne uma mensagem de erro
- Caso o autor não seja INSTRUCTOR, retorne uma mensagem de erro

#### 2.2 Implementar videos no curso

Implemente um `POST` em `/courses/{courseCode}/sections/{sectionCode}`
```
POST /courses/{courseCode}/sections/{sectionCode}
Content-Type: application/json
{
    "video": "https://www.youtube.com/watch?v=gI4-vj0WpKM"
}
```

##### Regras
- O video é obrigatório, caso esteja em branco, retorne uma mensagem de erro
- Caso o vídeo já esteja cadastrado nessa aula, retorne uma mensagem de erro

### 3. Implementar a matrícula de usuário

- Uma Matrícula associa um usuário a um curso, em uma data específica.
- Um usuário não pode matricular-se mais de uma vez em um curso.
- Implemente um endpoint no projeto que recebe um `POST` em `/courses/{courseCode}/enroll`.
- O `username` do usuário a ser matriculado deve estar em um JSON no corpo da requisição.
- O _status code_ de retorno deve ser [201 Created](https://httpstatusdogs.com/201-created).
- Não é necessário ter um corpo na resposta nem um cabeçalho `Location` por enquanto.
- Caso o usuário já esteja matriculado no curso, deve ser retornado um _status code_ [400 Bad Request](https://httpstatusdogs.com/400-bad-request).
- Não deixe de implementar validações. Pense em campos que devem ser obrigatórios e outros cenários de validação.
- Implemente testes automatizados para essa funcionalidade. Baseie-se nos já implementados.
- Seja consistente com o estilo de código que já existe no projeto.

__Por exemplo__, para matricular o usuário `alex` no curso de código `java-1`, deve ser feito o seguinte:

```
POST /courses/java-1/enroll
Content-Type: application/json

{"username": "alex"}
```


### 4. Implementar relatório de vídeos por aula

- Entre os cursos com matrículas, retorne as aulas com o total de videos cadastrados em ordem decrescente.
- Implemente um endpoint `GET` no projeto que retorne os dados em `/sectionByVideosReport`.
  __Exemplo__, ao chamar o recurso, o retorno deve ser no seguinte formato:

```
GET /sectionByVideosReport
[
    {
        "courseName": "Titúlo do curso",
        "sectionTitle": "Nome da aula",
        "authorName": "Nome do autor",
        "totalVideos": 10
    },
    {
       "courseName": "Titúlo do curso",
       "sectionTitle": "Nome da aula",
       "authorName": "Nome do autor",
       "totalVideos": 8
    }
]
```

Caso não tenha matrículas, deve ser retornado um _status code_ [204 No Content](https://httpstatusdogs.com/204-no-content). Implemente testes automatizados para essa funcionalidade. Baseie-se nos já implementados.


## Alguns pontos importantes:

- Favoreça o uso do spring data
- Alguns exemplos de chamadas usando cURL estão em [exemplos-curl.md](exemplos-curl.md).
- Caso tenha dificuldade, use como referência os posts sobre _Relationships_ do blog do Vlad Mihalcea:  https://vladmihalcea.com/tutorials/hibernate/
- Observação: **NÃO** é preciso implementar listagem, edição ou remoção de matrículas.


## Bônus (não obrigatório)

1. Altere o endpoint `/courses/{code}` para retornar as aulas e vídeos cadastrados no exercício 2.
2. Amplie os testes automatizados tanto para a funcionalidade que você implementou como para as que já estavam implementadas no projeto.
