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
| R1 | Um aluno não pode se matricular em uma turma que já esta cursando.  | MatriculaService | POST /matriculas |
| R2 | Um aluno não pode se matricular em uma turma em caso de exceder o número de vagas | MatriculaService | POST /matriculas |
| R3 | Um professor não pode ser aluno da turma que esta dando aula | MatriculaService | POST /matriculas/ |
| R4 | Um aluno não pode se matricular em turmas que estão em período de planejamento | MatriculaService | POST /matriculas |
| C1 | Um curso só pode ser publicado depois de ter pelo menos um módulo e uma matéria | CursoService | PUT /cursos/{id} |
| C2 | Um curso só pode ser arquivado depois de ter sido publicado | CursoService | PUT /cursos/{id} |

**Fora do escopo:**

- O sistema não valida choque de horários ou ocupação por período (matutino/vespertino/noturno), pois a aplicação não faz o controle de cronograma de horários das aulas.
- O sistema não irá bloquear a publicação ou abertura de um curso exigindo o cadastro do PPC (Projeto Pedagógico de Curso).
  
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
| Pessoa | `V1__criar_pessoa.sql`, pronta | `Pessoa`, pronta | `PessoaRepository`, pronta | *Não se aplica* | *Não se aplica* | *Não se aplica* | *Não se aplica* |
| Aluno | `V2__criar_aluno.sql`, pronta | `Aluno`, pronta | `AlunoRepository`, pronta | `AlunoRequestDTO`, `AlunoResponseDTO`, pronta | `AlunoMapper`, pronta | `AlunoService`, pronta | `AlunoController`, pronta |
| Professor | `V3__criar_professor.sql`, pronta | `Professor`, pronta | `ProfessorRepository`, pronta | `ProfessorRequestDTO`, `ProfessorResponseDTO`, pronta | `ProfessorMapper`, pronta | `ProfessorService`, pronta | `ProfessorController`, pronta |
| Curso | `V4__criar_curso.sql` e `V9__garantir_slug_unico_curso.sql`, prontas | `Curso`, pronta | `CursoRepository`, pronto | `CursoRequestDTO`, `AtualizarCursoRequestDTO`, `CursoCompletoResponseDTO` e `CursoResponseDTO`, prontos | `CursoMapper`, pronto | `CursoService`, pronto | `CursoController`, pronto |
| Modulo | `V5__criar_modulo.sql` e `V10__garantir_ordem_unica_modulo_por_curso.sql`, prontas | `Modulo`, pronto | `ModuloRepository`, pronto | `ModuloRequestDTO`, `AtualizarModuloDTO` e `ModuloResponse`, prontos | `ModuloMapper`, pronto | `ModuloService`, pronto | `ModuloController`, pronto |
| Materia | `V6__criar_materia.sql` e `V11__garantir_nome_unico_materia_por_modulo.sql`, prontas | `Materia`, pronta | `MateriaRepository`, pronto | `MateriaRequestDTO` e `MateriaResponse`, prontos | `MateriaMapper`, pronto | `MateriaService`, pronto | `MateriaController`, pronto |
| Turma | `V7__criar_turma.sql`, pronta | `Turma`, pronta | `TurmaRepository`, pronta | `TurmaRequestDTO`, `AtualizarTurmaRequestDTO` e `TurmaResponseDTO`, pronta | `TurmaMapper`, pronta | `TurmaService`, pronta | `TurmaController`, pronta |
| MatriculaAlunoTurma | `V8__criar_matricula_aluno_turma.sql`, pronta | `MatriculaAlunoTurma`, pronta | `MatriculaAlunoTurmaRepository`, pronta | `MatriculaAlunoTurmaRequestDTO` e `TurmaResponseDTO`, pronta | `MatriculaAlunoTurmaMapper`, pronta | `MatriculaAlunoTurmaService`, pronta | `MatriculaAlunoTurmaController`, pronta |


## Entidades


| Entidade | O que representa | Como se relaciona |
|---|---|---|
| **Pessoa** | Uma pessoa da instituição de ensino ou possível cliente potencial | Relaciona-se com `Aluno` (1:1) e `Professor` (1:1) |
| **Aluno** | Um estudante matriculado na instituição, representando o aluno ativo | Relaciona-se com `Pessoa` (1:1) e `MatriculaAlunoTurma` (1:N) |
| **Professor** | Uma pessoa da instituição de ensino que dá aula aos alunos | Relaciona-se com `Pessoa` (1:1) e `Turma` (1:N) |
| **Curso** | O curso disponível no catálogo da instituição | Relaciona-se com `Modulo` (1:N) |
| **Módulo** | Cada bloco estrutural ou disciplina que compõe o curso | Relaciona-se com `Curso` (N:1), `Materia` (1:N) e `Turma` (1:N) |
| **Matéria** | Conteúdos específicos e obrigatórios ensinados dentro de um módulo | Relaciona-se com `Modulo` (N:1) |
| **Turma** | A ocorrência prática de um módulo em um período letivo | Relaciona-se com `Modulo` (N:1), `Professor` (N:1) e `MatriculaAlunoTurma` (1:N) |
| **MatriculaAlunoTurma** | Tabela associativa que vincula o aluno a uma turma específica | Relaciona-se com `Aluno` (N:1) e `Turma` (N:1) |


Atributos, tipos e o diagrama das tabelas estão em [docs/modelo-de-dominio.md](docs/modelo-de-dominio.md).

## Rotas

### As que já funcionam


| Verbo | Caminho | O que faz | Regra | Sucesso | Erros |
|---|---|---|---|---|---|
| `GET` | `/cursos` | Lista cursos | Nenhuma | `200` | — |
| `GET` | `/cursos/{identificador}` | Busca curso por id ou slug, com módulos e matérias | Nenhuma | `200` | `404` |
| `POST` | `/cursos` | Cria curso em rascunho | — | `201` | `400` |
| `PUT` | `/cursos/{id}` | Atualiza curso ou muda status | C1, C2 | `200` | `400`, `404` |
| `DELETE` | `/cursos/{id}` | Remove curso sem módulos | Nenhuma | `204` | `400`, `404` |
| `POST` | `/modulos` | Adiciona módulo a um curso | — | `201` | `400` |
| `PUT` | `/modulos/{id}` | Atualiza módulo | — | `200` | `400`, `404` |
| `DELETE` | `/modulos/{id}` | Remove módulo sem matérias ou turmas | — | `204` | `400`, `404` |
| `POST` | `/materias` | Adiciona matéria a um módulo | — | `201` | `400` |
| `DELETE` | `/materias/{id}` | Remove matéria; não remove a última de curso publicado | — | `204` | `400`, `404` |

Exemplo de criação de curso:

```http
POST /cursos
Content-Type: application/json

{
  "titulo": "Desenvolvimento Backend",
  "slug": "desenvolvimento-backend",
  "status": "RASCUNHO"
}
```

Resposta, `201 Created`:

```json
{
  "id": 1,
  "titulo": "Desenvolvimento Backend",
  "subtitulo": null,
  "resumo": null,
  "slug": "desenvolvimento-backend",
  "status": "RASCUNHO"
}
```

### Erros

Todo erro sai no mesmo formato. Este é o `400` de uma regra de negócio violada:

```json
{
  "status": 400,
  "erro": "Bad Request",
  "mensagem": "R1: Um aluno não pode se matricular em uma turma que já esta cursando.",
  "caminho": "/matriculas",
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
| Desenho | completo: as regras de negócio, contratos e modelagem estão todas definidas nesse readme |
| Estrutura | Todas as entidades têm migration, entidades, Repository, DTOs, Mapper, Service e Controller. |
| API e regras | Todas as rotas documentadas funcionam; Todas as regras foram implementadas |

## Documentação

| Documento | O que tem |
|---|---|
| [Modelo de domínio](docs/modelo-de-dominio.md) | Entidades, relacionamentos, diagrama das tabelas, migrations |
| [Contrato da API](docs/contrato-da-api.md) | Todas as rotas, os DTOs, exemplos de JSON e erros |
| [Autoavaliação](docs/autoavaliacao.md) | Os critérios da revisão do código, marcados pelo grupo |
