# Modelo de domínio

## Entidades

Uma tabela por entidade. A coluna **Regra** é o que vira `NOT NULL`, `UNIQUE` ou validação no DTO.

### Pessoa

| Atributo    | Tipo Java | Coluna      | Obrigatório | Regra                           |
|-------------|-----------|-------------|-------------|---------------------------------|
| `id`        | `Long`    | `id`        | sim         | Gerado pelo banco               |
| `cpf`       | `String`  | `cpf`       | sim         | CPF único e válido da pessoa    |
| `email`     | `String`  | `email`     | sim         | Único, até 255 caracteres       |
| `nome`      | `String`  | `nome`      | sim         | Até 255 caracteres              |


### Aluno

Um estudante pode se matricular em vários cursos

| Atributo    | Tipo Java | Coluna      | Obrigatório | Regra                                    |
|-------------|-----------|-------------|-------------|------------------------------------------|
| `id`        | `Long`    | `id`        | sim         | Gerado pelo banco                        |
| `pessoa`    | `Pessoa`  | `id_pessoa` | sim         | FK para pessoa                           |
| `matricula` | `String`  | `matricula` | sim         | Gerado no cadastro, máximo 64 caracteres |


### Professor

Professor de uma ou mais matérias

| Atributo    | Tipo Java | Coluna      | Obrigatório | Regra                           |
|-------------|-----------|-------------|-------------|---------------------------------|
| `id`        | `Long`    | `id`        | sim         | Gerado pelo banco               |
| `pessoa`    | `Pessoa`  | `id_pessoa` | sim         | FK para pessoa                  |


### Curso

Um curso específico, pertence a uma instituição. Ele age como um catálogo.

| Atributo           | Tipo Java         | Coluna            | Obrigatório | Regra                                         |
|--------------------|-------------------|-------------------|-------------|-----------------------------------------------|
| `id`               | `Long`            | `id`              | sim         | Gerado pelo banco                             |
| `titulo`           | `String`          | `titulo`          | sim         | Máximo 128 e no minimo 2 caracteres           |
| `subtitulo`        | `String`          | `subtitulo`       | não         | Máximo 255 e no minimo 2 caracteres           |
| `resumo`           | `String`          | `resumo`          | não         | Máximo 1024 e no minimo 16 caracteres         |
| `slug`             | `String`          | `slug`            | sim         | Deve ser caracteres alfanuméricos e '-' como caracter especial apenas, deve ser único e tem no máximo 128 caracteres e no mínimo 2 caracteres      | 
| `status`           | `EnumStatusCurso` | `status`          | sim         | Pode ser RASCUNHO, PUBLICADO e ARQUIVADO                          | 


## Módulo de um curso

Cada curso tem um módulo ou disciplina que será lecionadas no curso.

| Atributo           | Tipo Java         | Coluna            | Obrigatório | Regra                                         |
|--------------------|-------------------|-------------------|-------------|-----------------------------------------------|
| `id`               | `Long`            | `id`              | sim         | Gerado pelo banco                             |
| `curso`            | `Curso`           | `id_curso`        | sim         | FK para `curso`                               |
| `nome`             | `String`          | `nome`            | sim         | Máximo 128 e no minimo 2 caracteres           |
| `descricao`        | `String`          | `descricao`       | não         | Máximo 1024 e no minimo 16 caracteres         |
| `cargaHoraria`     | `Int`             | `carga_horaria`   | sim         | Deve ser maior que zero                       |
| `ordem`            | `Int`             | `ordem`           | sim         | Deve ser maior que zero e é unico com curso   |


## Matéria de um módulo

Cada módulo tem matérias obrigatórios que seram ensinadas.

| Atributo           | Tipo Java         | Coluna            | Obrigatório | Regra                                         |
|--------------------|-------------------|-------------------|-------------|-----------------------------------------------|
| `id`               | `Long`            | `id`              | sim         | Gerado pelo banco                             |
| `modulo`           | `Modulo`          | `id_modulo`       | sim         | FK para `modulo`                              |
| `nome`             | `String`          | `nome`            | sim         | Máximo 128 e no minimo 2 caracteres e unica com modulo |

## Turma

A turma representa efetivamente o acontecimento de um curso. Ela tem alunos, professores e materiais


| Atributo           | Tipo Java         | Coluna            | Obrigatório | Regra                                            |
|--------------------|-------------------|-------------------|-------------|-----------------------------------------------|
| `id`               | `Long`            | `id`              | sim         | Gerado pelo banco                                |
| `modulo`           | `Modulo`          | `id_modulo`       | sim         | FK para `modulo`                                 |
| `professor`        | `Professor`       | `id_professor`    | sim         | FK para `professor`                              |
| `periodoLetivo`    | `String`          | `periodo_letivo`  | sim         | Gerado automaticamente dentro do padrao `YYYY/p` |
| `tipoPeriodo`      | `EnumTipoPeriodoTurma` | `tipo_periodo`    | sim         | Pode ser SEMESTRE, BIMESTRE, TRIMESTRE e QUADRIMESTRE |
| `turno`            | `EnumTurnoTurma`  | `turno`           | sim         | Pode ser MATUTINO, VESPERTINO, NOTURNO e INTEGRAL |
| `vagas`            | `Int`             | `vagas`           | sim         | Deve ser maior que zero                          |
| `dataInicio`       | `Date`            | `dataInicio`      | não         | Tem que ser >= hoje                              |
| `dataFim`          | `Date`            | `dataFim`         | não         | Tem que ser correspondente ao periodo            |


## Matricula Aluno Turma

Tabela de relacionamento de Aluno <-> Turma

| Atributo   | Tipo Java | Coluna     | Obrigatório | Regra              |
|------------|-----------|------------|-------------|--------------------|
| `id`       | `Long`    | `id`       | sim         | Gerado pelo banco  |
| `id_aluno` | `Long`    | `id_aluno` | sim         | FK para alunos     |
| `id_turma` | `Long`    | `id_turma` | sim         | FK para `turma`    |
| `dataMatricula` | `Date` | `data_matricula` | sim         | Gerada automaticamente     |
| `status`    | `String` | `status`       | sim         | Pode ser `Ativo`, `Matriculado`, `Afastado`, `Trancado` e `Concluido` |



## Relacionamentos

| De (Origem) | Para (Destino) | Tipo de Relacionamento | No Banco de Dados (FK / Constraints) | No Código Java (JPA Annotation) |
| :--- | :--- | :--- | :--- | :--- |
| **MatriculaAlunoTurma** | Aluno | Muitos para Um (`M:1`) | `matriculas_alunos_turmas.id_aluno` referência `alunos(id)` | `@ManyToOne` em `MatriculaAlunoTurma` |
| **MatriculaAlunoTurma** | Turma | Muitos para Um (`M:1`) | `matriculas_alunos_turmas.id_turma` referência `turmas(id)` | `@ManyToOne` em `MatriculaAlunoTurma` |
| **Aluno** | Pessoa | Um para Um (`1:1`) | `alunos.id_pessoa` referência `pessoas(id)` (Unique) | `@OneToOne` em `Aluno` |
| **Professor** | Pessoa | Um para Um (`1:1`) | `professores.id_pessoa` referência `pessoas(id)` (Unique) | `@OneToOne` em `Professor` |
| **Modulo** | Curso | Muitos para Um (`M:1`) | `modulos.id_curso` referência `cursos(id)` | `@ManyToOne` em `Modulo` |
| **Materia** | Modulo | Muitos para Um (`M:1`) | `materias.id_modulo` referência `modulos(id)` | `@ManyToOne` em `Materia` |
| **Turma** | Modulo | Muitos para Um (`M:1`) | `turmas.id_modulo` referência `modulos(id)` | `@ManyToOne` em `Turma` |
| **Turma** | Professor | Muitos para Um (`M:1`) | `turmas.id_professor` referência `professores(id)` | `@ManyToOne` em `Turma` |


## Diagrama ER

```mermaid
erDiagram
    pessoas {
        BIGINT id PK
        VARCHAR cpf UK
        VARCHAR email UK
        VARCHAR nome
    }

    alunos {
        BIGINT id PK
        BIGINT id_pessoa FK
        VARCHAR matricula UK
    }

    professores {
        BIGINT id PK
        BIGINT id_pessoa FK
    }

    cursos {
        BIGINT id PK
        VARCHAR titulo
        VARCHAR subtitulo
        VARCHAR resumo
        VARCHAR slug UK
        VARCHAR status
    }

    modulos {
        BIGINT id PK
        BIGINT id_curso FK
        VARCHAR nome
        VARCHAR descricao
        INTEGER carga_horaria
        INTEGER ordem
    }

    materias {
        BIGINT id PK
        BIGINT id_modulo FK
        VARCHAR nome
    }

    turmas {
        BIGINT id PK
        BIGINT id_modulo FK
        BIGINT id_professor FK
        VARCHAR periodo_letivo
        VARCHAR tipo_periodo
        VARCHAR turno
        INTEGER vagas
        DATE data_inicio
        DATE data_fim
    }

    matricula_aluno_turma {
        BIGINT id PK
        BIGINT id_aluno FK
        BIGINT id_turma FK
        DATE data_matricula
        VARCHAR status
    }

    pessoas ||--|| alunos : "torna-se"
    pessoas ||--|| professores : "torna-se"
    cursos ||--o{ modulos : "contem"
    modulos ||--o{ materias : "possui"
    modulos ||--o{ turmas : "oferta"
    professores ||--o{ turmas : "rege"
    alunos ||--o{ matricula_aluno_turma : "realiza"
    turmas ||--o{ matricula_aluno_turma : "recebe"

```

Como ler: 

- `||--o{` é "um para muitos". Um curso contem zero ou vários módulos, e cada módulo é de exatamente um curso.
- `||--||` é "um para um". Uma pessoa torna-se um aluno (ou professor), e cada aluno (ou professor) pertence a exatamente uma pessoa.


> Observações Importantes: Aluno e professor tem uma chave PK incremental e não é UK com pessoa, uma vez que em caso de troca de matricula de um aluno é possível manter o histórico de uma turma passada cuja sua matricula era diferente da atual.

Prova-se tal ponto com esses casos:

- **Transferência Interna**
- **Alteração na Grade Curricular**
- **Trancamento ou Suspensão**
- **Cancelamento**


## Migrations

Todas existem em `src/main/resources/db/migration`, uma tabela por arquivo. A ordem importa: a tabela referenciada nasce antes da que referencia.

| Versão | Arquivo                     | O que cria                                                                |
|----|-----------------------------|-------------------------------------------------------------------------------|
| V1 | `V1__criar_pessoa.sql`                  | Tabela `pessoa`                                                   |
| V2 | `V2__criar_aluno.sql`                   | Tabela `aluno`, com uma chave estrangeira                         |
| V3 | `V3__criar_professor.sql`               | Tabela `professor`, com uma chave estrangeiras                    |
| V4 | `V4__criar_curso.sql`                   | Tabela `curso`                                                    |
| V5 | `V5__criar_modulo.sql`                  | Tabela `modulo`, com um chave estrangeira                         |
| V6 | `V6__criar_materia.sql`                 | Tabela `materia`, com as uma chave estrangeira                    |
| V7 | `V7__criar_turma.sql`                   | Tabela `turma`, com as duas chaves estrangeiras                   |
| V8 | `V8__criar_matricula_aluno_turma.sql`   | Tabela `matricula_aluno_turma`, com as duas chaves estrangeiras   |
