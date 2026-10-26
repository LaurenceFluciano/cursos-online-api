# Autoavaliação

Os 10 critérios que serão avaliados na revisão do código, descritos em `Aula 10-11/AVALIACAO.md`, no repositório da disciplina. Para cada um, a marca do grupo e onde está a evidência.

- ✅ atende
- ◐ atende em parte (diga o que falta)
- ❌ não atende

| Critério | Marca | Evidência |
|---|---|---|
| P1 A API sobe seguindo o README | ✅  | `README.md`, seção "Como executar" |
| P2 Cada classe no pacote da sua camada | ✅  | `src/main/java/api/` (`model`, `repository`, `dto`, `mapper`, `service`, `controller`) |
| P3 Migrations e `@Entity` de todas as tabelas, e a API sobe | ✅  | `src/main/resources/db/migration/` (V1 a V11) e `api/model/` (`Pessoa`, `Aluno`, `Professor`, `Curso`, `Modulo`, `Materia`, `Turma`, `MatriculaAlunoTurma`) |
| P4 DTOs de entrada e de saída de todas as entidades | ✅  | `api/dto/`: `AlunoRequestDTO`/`AlunoResponseDTO`, `ProfessorRequestDTO`/`ProfessorResponseDTO`, `CursoRequestDTO`/`AtualizarCursoRequestDTO`/`CursoResponseDTO`/`CursoCompletoResponseDTO`, `ModuloRequestDTO`/`AtualizarModuloDTO`/`ModuloResponse`/`ModuloComMateriasResponseDTO`, `MateriaRequestDTO`/`MateriaResponse`, `TurmaRequestDTO`/`AtualizarTurmaRequestDTO`/`TurmaResponseDTO`, `MatricularAlunoRequestDTO`/`AtualizarStatusMatriculaDTO`/`MatricularAlunoResponseDTO`/`MatriculaAlunoTurmaResponseDTO` |
| P5 Repository, Mapper, Service e Controller de todas, com o comentário no topo, e o mapa da estrutura | ◐ | Todas as entidades têm as quatro camadas (`api/repository`, `api/mapper`, `api/service`, `api/controller`) e o mapa está em `README.md`, seção "Arquitetura". **Falta:** o comentário no topo das classes, que só existe no `ApiExceptionHandler`, e remover do README a nota "(deve ser completado apenas no final...)". |
| P6 API simples: `POST`, `GET` e `GET /{id}`, com `400` e `404` | ✅ | `AlunoController`, `ProfessorController`, `TurmaController` e `CursoController`. `400` pelo `@Valid` e pelo `ApiExceptionHandler`; `404` pelas exceções `RecursoNaoEncontradoException` (`CursoNaoEncontradoException`, `TurmaNaoEncontradaException`, etc.) |
| P7 No mínimo 3 regras de negócio implementadas, com `400` quando violadas | ✅ | `MatriculaAlunoTurmaService` (R1 a R7, rota `POST /matriculas` e `PATCH /matriculas/{id}/status`), `CursoService` (C1 e C2, rota `PUT /cursos/{id}`), `MateriaService` (C3, `DELETE /materias/{id}`) e `ModuloService`/`CursoService` (C4, `DELETE /modulos/{id}` e `DELETE /cursos/{id}`); todas lançam `RegraDeNegocioException` → `400` |
| P8 As regras atendem ao problema e ao tema | ✅ | `README.md`, seção "O problema": as regras protegem a matrícula (vagas, duplicidade, professor, período da turma) e a integridade do catálogo de cursos a distância |
| P9 Código igual ao desenho | ✅ | `docs/modelo-de-dominio.md` e `docs/contrato-da-api.md`, atualizados conforme o código (rotas, DTOs, enums, nomes de tabela e status HTTP). Conferir as migrations V1 a V11 contra o modelo antes de entregar. |
| P10 README completo e bem escrito | ◐ | `README.md`. **Falta:** trocar `{{usuario}}/{{repositorio}}` no `git clone`, corrigir a tabela "Quem usa o sistema" (sem a linha separadora, não renderiza no GitHub), remover a nota "(deve ser completado...)", revisar erros de português e conferir a renderização no GitHub |

**Pontos que o grupo espera no projeto:** 4,5 (ajustar depois de fechar os itens ◐ de P5 e P10)

A apresentação vale os outros 5,0 pontos: são 3 perguntas, feitas na Aula 11.
