# API Cursos Online

> **Este é o ponto de partida do projeto do grupo.** Apague este bloco antes de entregar.
>
> **O que falta fazer:**
> 1. Faça a API simples e implemente as regras de negócio.
> 2. Termine este README: troque tudo o que está entre `{{ }}` e apague o que está marcado com *(exemplo)*. Mantenha as seções e a ordem.
> 3. Abra o repositório no GitHub, leia o README como quem nunca viu o projeto e preencha `docs/autoavaliacao.md`.
>
> Na primeira vez que a API sobe, antes de existir tabela e rota, o Flyway avisa `No migrations found` e o Swagger UI mostra `No operations defined in spec!`. É o esperado.
>
> Os exemplos marcados com *(exemplo)* são de uma biblioteca. O projeto completo dela está em `Aula 10-11/exemplo-biblioteca`.

Um sistema para uma instituição de ensino e cursos técnicos criem cursos onlines em que alunos que moram muito longe, caso tenha acesso a internet, podem estudar e realizar o curso a distância.  

Projeto Final da disciplina de Backend (Engenharia de Software, SATC). **Etapa 1: arquitetura do backend.**

## Índice

- [Integrantes](#integrantes)
- [O problema](#o-problema)
- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
- [Entidades](#entidades)
- [Rotas](#rotas)
- [Como executar](#como-executar)
- [Situação do projeto](#situação-do-projeto)
- [Documentação](#documentação)

## Integrantes

| Nome | GitHub |
|---|---|
| Laurence Floriano Luciano | [@{{usuario}}](https://github.com/LaurenceFluciano) |
| Antonio Henrique Citadin Benedet | [@{{usuario}}](https://github.com/TonhoProgram) |
| Igor Schug Zizinio | [@{{usuario}}](https://github.com/igorzizinio) |

## O problema

**Tema sorteado:** Educação e Aprendizagem

Esse software resolve o problema de permitir que a instituição de ensino técnico diminui seus gastos, aumente o número de alunos e aumenta seu reconhecimento na região.
No ponto de vista do estudante, a possibilidade de poder estudar mesmo morando longe. Principalmente aqueles que moram em regiões como ribeirinho de amazonas desde que tenham acesso a internet.

**Quem usa o sistema:**

| Quem | O que faz no sistema |
| Alunos | Se matricula em cursos online para estudar. |
| Coordenadores da Instituição de Ensino | Gerencia os cursos e as matriculas. |
| Professores | Responsáveis por lecionar nas aulas |

**Regras de negócio.** No mínimo 3, criadas pelo grupo a partir do problema: o que o sistema precisa garantir ou impedir. Precisam atender à necessidade do tema e estar implementadas no código. O que só confere formato (campo obrigatório, número positivo) é validação do DTO, não regra de negócio.

| # | Regra | Onde está no código | Rota que usa |
|---|---|---|---|
| R1 | Um aluno não pode se matricular em uma turma que já esta cursando.  | TurmaService | POST /turmas/{idTurma}/alunos/{idAluno} |
| R2 | Um aluno não pode se matricular em uma turma cujo período (matutino, vespertino, noturno e integral) já estaja ocupado por outra turma que ele participa. | TurmaService | POST /turma/{idTurma}/alunos/{idAluno} |
| R3 | Um aluno não pode se matricular em uma turma em caxo de exceder o número de vagas | TurmaService | POST /turmas/{idTurma}/alunos/{idAluno} |
| R4 | Um professor não pode ser aluno da turma que esta dando aula | TurmaService | POST /turmas/ |

**Fora do escopo:** Ele não será um SaaS que abrange diversas instuitições diferentes. Será uma solução para apenas uma instituição.

## Tecnologias

| O quê | Qual |
|---|---|
| Linguagem | Java 25 |
| Framework | Spring Boot 3.5 (Web, Validation, Data JPA) |
| Banco de dados | PostgreSQL 16, em Docker |
| Migrations | Flyway |
| Documentação da API | springdoc-openapi (Swagger UI) |

## Arquitetura

A API é organizada em camadas. Uma requisição entra pelo Controller, passa pelo Service, que aplica as regras de negócio, e chega ao banco pelo Repository; a resposta faz o caminho de volta. O Mapper converte DTO em entidade e entidade em DTO.

(*deve ser completado apenas no final da implementação da api*)

**Mapa da estrutura.** Uma linha por entidade, todas as do modelo. Em cada célula, a classe e a situação: **pronta**, **implementada** (já tem lógica), **em parte** (só uma parte das rotas) ou **montada** (criada, ligada às outras, ainda sem lógica).

| Entidade | Migration | Model | Repository | DTOs | Mapper | Service | Controller |
|---|---|---|---|---|---|---|---|
| Alunos | `V1__criar_alunos.sql`, incompleta | `Aluno`, incompleto | `AlunoRepository`, incompleto | `AlunoRequestDTO`, `AlunoResponseDTO`, incompleto | `AlunoMapper`, incompleto | `AlunoService`, incompleto | `AlunoController`, incompleto |
| Professores | `V2__criar_professores.sql`, incompleta | `Professor`, incompleto | `ProfessorRepository`, incompleto | `ProfessorRequestDTO`, `ProfessorResponseDTO`, incompleto | `ProfessorMapper`, incompleto | `ProfessorService`, incompleto | `ProfessorController`, incompleto |
| Cursos | `V3__criar_cursos.sql`, incompleta | `Curso`, incompleto | `CursoRepository`, incompleto | `CursoRequestDTO`, `CursoResponseDTO`, incompleto | `CursoMapper`, incompleto | `CursoService`, incompleto | `CursoController`, incompleto |

| ... | ... | ... | ... | ... | ... | ... | ... |

## Entidades

(*deve ser completado apenas no final da implementação da api e documentacao*)

| Entidade | O que representa | Como se relaciona |
|---|---|---|
| Pessoa | Uma pessoa da instituição de ensino ou possível cliente potencial | Aparece nas especializações de aluno e professor |

Atributos, tipos e o diagrama das tabelas estão em [docs/modelo-de-dominio.md](docs/modelo-de-dominio.md).

## Rotas

### As que já funcionam

(*deve ser completado apenas no final da implementação da api*)

| Verbo | Caminho | O que faz | Regra | Sucesso | Erros |
|---|---|---|---|---|---|
| `POST` *(exemplo)* | `/livros` | Cadastra um livro | Nenhuma | `201` | `400` |
| `POST` *(exemplo)* | `/emprestimos` | Registra um empréstimo | R1 e R2 | `201` | `400`, `404` |
| ... | ... | ... | ... | ... | ... |

Exemplo de requisição *(exemplo: troque por uma rota do grupo)*:

```http
POST /livros
Content-Type: application/json

{
  "titulo": "Dom Casmurro",
  "isbn": "978-85-359-0277-5"
}
```

Resposta, `201 Created`:

```json
{
  "id": 1,
  "titulo": "Dom Casmurro",
  "isbn": "978-85-359-0277-5",
  "disponivel": true
}
```

### Erros

Todo erro sai no mesmo formato. Este é o `400` de uma regra de negócio violada *(exemplo)*:

```json
{
  "status": 400,
  "erro": "Bad Request",
  "mensagem": "R2: o livro já está emprestado",
  "caminho": "/emprestimos",
  "timestamp": "2026-10-06T19:30:00",
  "campos": []
}
```

Um campo inválido também devolve `400`, com a lista `campos` dizendo qual campo corrigir. Um id que não existe devolve `404`.

### As planejadas

Estão no contrato, mas ainda não respondem.

| Recurso | O que vai fazer |
|---|---|
| ... | ... |

O contrato completo, com todas as rotas e os DTOs, está em [docs/contrato-da-api.md](docs/contrato-da-api.md).

## Como executar

Pré-requisitos: JDK 25, Docker Desktop aberto e Git.

```bash
git clone https://github.com/{{usuario}}/{{repositorio}}.git
cd {{repositorio}}

docker compose up -d          # sobe o PostgreSQL na porta 5437
./mvnw spring-boot:run        # Windows: .\mvnw.cmd spring-boot:run
```

Com a API no ar:

| Endereço | O que é |
|---|---|
| <http://localhost:8080/swagger-ui.html> | Swagger UI: as rotas, com **Try it out** para enviar requisições |
| <http://localhost:8080/v3/api-docs> | O contrato OpenAPI em JSON |

Para testar as rotas, use o Swagger UI ou o **Postman**. No Postman, importe todas as rotas de uma vez: **Import** → **Link** → `http://localhost:8080/v3/api-docs`.

Para parar: `Ctrl+C` na API e `docker compose stop` no banco. Para apagar o banco e rodar as migrations do zero: `docker compose down -v` e suba de novo.

## Situação do projeto

| Parte | Situação |
|---|---|
| Desenho | {{ex.: completo: problema e regras neste README, modelo e contrato em docs/}} |
| Estrutura | {{ex.: as 3 entidades têm migration, entidade, Repository, DTOs, Mapper, Service e Controller}} |
| API e regras | {{ex.: as rotas de livros funcionam; R1, R2 e R3 estão implementadas}} |

**Ainda não funciona:** {{as rotas planejadas que ainda não respondem}}

## Documentação

| Documento | O que tem |
|---|---|
| [Modelo de domínio](docs/modelo-de-dominio.md) | Entidades, relacionamentos, diagrama das tabelas, migrations |
| [Contrato da API](docs/contrato-da-api.md) | Todas as rotas, os DTOs, exemplos de JSON e erros |
| [Autoavaliação](docs/autoavaliacao.md) | Os critérios da revisão do código, marcados pelo grupo |
