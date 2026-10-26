# Contrato da API

> O contrato é o que o cliente da API enxerga: rotas, JSON e status. Desenhe as rotas de **todas** as entidades. Nesta etapa precisam funcionar as três da API simples e as que as regras de negócio usam. As linhas marcadas com *(exemplo)* mostram o nível de detalhe esperado: apague e escreva as do grupo.

## Convenções

| Item | Valor |
|---|---|
| Endereço base | `http://localhost:8080` |
| Formato | JSON, na requisição e na resposta |
| Datas | `yyyy-MM-dd` |
| Erros | Sempre no formato `ErroDTO` (ver [Erros](#erros)) |
| Paginação | As listas de `alunos`, `professores`, `turmas` e `matriculas` são paginadas e saem como `PageResponse<T>` (ver [DTOs de apoio](#dtos-de-apoio)). Parâmetros de query: `page` (padrão `0`), `size` (padrão `20`) e `sort` (padrão `id`). A lista de `cursos` **não** é paginada. |
| Documentação viva | `http://localhost:8080/swagger-ui.html` |

## Rotas

Coluna **Funciona**: `sim` para as rotas que já respondem nesta etapa: as da API simples e as que as regras de negócio usam.

### Cursos

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `GET` | `/cursos` | Lista os cursos | Nenhuma | lista de `CursoResponseDTO` | `200` | Nenhum | sim |
| `GET` | `/cursos/{identificador}` | Detalha por id ou slug, com módulos e matérias | Nenhuma | `CursoCompletoResponseDTO` | `200` | `404` | sim |
| `POST` | `/cursos` | Cadastra um curso como rascunho | `CursoRequestDTO` | `CursoResponseDTO` | `201` | `400` | sim |
| `PUT` | `/cursos/{id}` | Atualiza dados ou muda o status do curso | `AtualizarCursoRequestDTO` | `CursoResponseDTO` | `200` | `400`, `404` | sim |
| `DELETE` | `/cursos/{id}` | Remove um curso sem módulos | Nenhuma | Vazio | `204` | `400`, `404` | sim |

Regras de status: o curso deve ser criado como `RASCUNHO`. No `PUT`, só pode passar para `PUBLICADO` quando tiver pelo menos um módulo e uma matéria. Só pode passar para `ARQUIVADO` se o status atual for `PUBLICADO`. Violações retornam `400` com `ErroDTO`.

### Modulos

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `POST` | `/modulos` | Adiciona um módulo a um curso | `ModuloRequestDTO` | `ModuloResponse` | `201` | `400`, `404` | sim |
| `PUT` | `/modulos/{id}` | Atualiza os dados de um módulo | `AtualizarModuloDTO` | `ModuloResponse` | `200` | `400`, `404` | sim |
| `DELETE` | `/modulos/{id}` | Remove um módulo sem matérias ou turmas | Nenhuma | Vazio | `204` | `400`, `404` | sim |

`404` no `POST`: o `idCurso` informado não existe. `400`: a `ordem` já está ocupada por outro módulo do mesmo curso.

### Materias

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `POST` | `/materias` | Adiciona uma matéria a um módulo | `MateriaRequestDTO` | `MateriaResponse` | `201` | `400`, `404` | sim |
| `DELETE` | `/materias/{id}` | Remove uma matéria (não remove a última matéria de um curso publicado) | Nenhuma | Vazio | `204` | `400`, `404` | sim |

`404` no `POST`: o `idModulo` informado não existe. `400`: já existe matéria com esse nome no módulo.

### Turmas

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `POST` | `/turmas` | Cria uma turma | `TurmaRequestDTO` | `TurmaResponseDTO` | `201` | `400`, `404` | sim |
| `GET` | `/turmas` | Lista as turmas (paginado) | Nenhuma (query `page`, `size`, `sort`) | `PageResponse<TurmaResponseDTO>` | `200` | Nenhum | sim |
| `GET` | `/turmas/{id}` | Busca uma turma por id | Nenhuma | `TurmaResponseDTO` | `200` | `404` | sim |
| `PATCH` | `/turmas/{id}` | Atualiza professor, vagas e datas de uma turma | `AtualizarTurmaRequestDTO` | `TurmaResponseDTO` | `200` | `400`, `404` | sim |
| `DELETE` | `/turmas/{id}` | Remove uma turma | Nenhuma | Nenhuma (sem corpo) | `204` | `404` | sim |

`404` no `POST`/`PATCH`: o módulo ou o professor informado não existe. `400`: vagas, data de início ou data de fim inválidas (ver validações em [Turmas](#turmas-1)).

### Matriculas

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `POST` | `/matriculas` | Matricula um aluno em um curso; a API escolhe a turma aberta | `MatricularAlunoRequestDTO` | `MatricularAlunoResponseDTO` | `201` | `400`, `404` | sim |
| `GET` | `/matriculas` | Lista as matrículas (paginado) | Nenhuma (query `page`, `size`, `sort`) | `PageResponse<MatriculaAlunoTurmaResponseDTO>` | `200` | Nenhum | sim |
| `GET` | `/matriculas/{id}` | Busca uma matrícula por id | Nenhuma | `MatriculaAlunoTurmaResponseDTO` | `200` | `404` | sim |
| `PATCH` | `/matriculas/{id}/status` | Altera o status de uma matrícula | `AtualizarStatusMatriculaDTO` | `MatriculaAlunoTurmaResponseDTO` | `200` | `400`, `404` | sim |
| `DELETE` | `/matriculas/{id}` | Remove uma matrícula | Nenhuma | Vazio | `204` | `404` | sim |

`404` no `POST`: aluno ou curso inexistente. `400`: alguma regra de matrícula foi violada (ver [Rotas que não são CRUD](#rotas-que-não-são-crud)).

### Alunos

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `POST` | `/alunos` | Cadastra um aluno (cria a pessoa se ainda não existir) e gera a matrícula global (RA) | `AlunoRequestDTO` | `AlunoResponseDTO` | `200` | `400` | sim |
| `GET` | `/alunos/{id}` | Busca um aluno por id | Nenhuma | `AlunoResponseDTO` | `200` | `404` | sim |
| `GET` | `/alunos` | Lista os alunos (paginado) | Nenhuma (query `page`, `size`, `sort`) | `PageResponse<AlunoResponseDTO>` | `200` | Nenhum | sim |

`400` no `POST`: CPF ou e-mail já usado por outra pessoa, ou a pessoa já está cadastrada como aluno.

### Professores

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `POST` | `/professores` | Cadastra um professor (cria a pessoa se ainda não existir) | `ProfessorRequestDTO` | `ProfessorResponseDTO` | `200` | `400` | sim |
| `GET` | `/professores/{id}` | Busca um professor por id | Nenhuma | `ProfessorResponseDTO` | `200` | `404` | sim |
| `GET` | `/professores` | Lista os professores (paginado) | Nenhuma (query `page`, `size`, `sort`) | `PageResponse<ProfessorResponseDTO>` | `200` | Nenhum | sim |

`400` no `POST`: CPF ou e-mail já usado por outra pessoa, ou a pessoa já está cadastrada como professor.

### Rotas que não são CRUD

As que nascem das regras de negócio do README. Estas precisam funcionar nesta etapa.

| Verbo | Caminho | O que faz | Regra | Sucesso | Erros |
|---|---|---|---|---|---|
| `POST` | `/matriculas` | Matricula um aluno em uma turma de um curso automaticamente | R1, R2, R3, R4, R5 e R6 | `201` | `400`, `404` |
| `PATCH` | `/matriculas/{id}/status` | Altera o status da matrícula; matrícula `CONCLUIDO` não muda mais, e voltar a ocupar vaga exige vaga disponível | R7 | `200` | `400`, `404` |
| `PUT` | `/cursos/{id}` | Muda o status do curso | C1 e C2 | `200` | `400`, `404` |
| `DELETE` | `/materias/{id}` | Remove matéria, exceto a última de um curso publicado | C3 | `204` | `400`, `404` |
| `DELETE` | `/modulos/{id}` | Remove módulo, exceto se tiver matérias ou turmas | C4 | `204` | `400`, `404` |
| `DELETE` | `/cursos/{id}` | Remove curso, exceto se tiver módulos | C4 | `204` | `400`, `404` |

## DTOs

O que entra não é igual ao que sai: o cliente não envia `id` nem campos que a API decide.

### Cursos

| DTO | Direção | Campos | Validações |
|---|---|---|---|
| `CursoRequestDTO` | Entrada (`POST`) | `titulo` (String), `subtitulo` (String), `resumo` (String), `slug` (String), `status` (EnumStatusCurso) | `titulo`: obrigatório, de 2 a 128 caracteres.<br>`subtitulo`: opcional, de 2 a 255 caracteres.<br>`resumo`: opcional, de 16 a 1024 caracteres.<br>`slug`: obrigatório, único, de 2 a 128 caracteres, apenas alfanuméricos e '-'.<br>`status`: obrigatório e deve ser `RASCUNHO` no cadastro. |
| `AtualizarCursoRequestDTO` | Entrada (`PUT`) | `titulo` (String), `subtitulo` (String), `resumo` (String), `slug` (String), `status` (EnumStatusCurso) | Todos os campos são opcionais; os enviados seguem as mesmas restrições de tamanho e formato do `CursoRequestDTO`. O `slug` continua único e o `status` segue as regras C1 e C2. |
| `CursoResponseDTO` | Saída | `id` (Long), `titulo` (String), `subtitulo` (String), `resumo` (String), `slug` (String), `status` (String) | Retorna os dados cadastrais básicos do curso. |
| `CursoCompletoResponseDTO` | Saída | `id` (Long), `titulo` (String), `subtitulo` (String), `resumo` (String), `slug` (String), `status` (String), `modulos` (Lista de `ModuloComMateriasResponseDTO`) | Retorna a árvore pedagógica completa do curso (Módulos e suas respectivas Matérias aninhados). |

---

### Módulos

| DTO | Direção | Campos | Validações |
|---|---|---|---|
| `ModuloRequestDTO` | Entrada (`POST`) | `idCurso` (Long), `nome` (String), `descricao` (String), `cargaHoraria` (Integer), `ordem` (Integer) | `idCurso`: obrigatório, maior que zero, deve ser um curso existente.<br>`nome`: obrigatório, de 2 a 128 caracteres.<br>`descricao`: opcional, de 16 a 1024 caracteres.<br>`cargaHoraria`: obrigatório, deve ser maior que zero.<br>`ordem`: obrigatório, deve ser maior que zero e único por curso. |
| `AtualizarModuloDTO` | Entrada (`PUT`) | `nome` (String), `descricao` (String), `cargaHoraria` (Integer), `ordem` (Integer) | Todos os campos são opcionais; os enviados seguem as mesmas restrições do cadastro. A `ordem` continua única por curso. |
| `ModuloResponse` | Saída | `id` (Long), `idCurso` (Long), `nome` (String), `descricao` (String), `cargaHoraria` (Integer), `ordem` (Integer) | Retorna os dados básicos do módulo criado ou alterado. |
| `ModuloComMateriasResponseDTO` | Saída (dentro de `CursoCompletoResponseDTO`) | `id` (Long), `idCurso` (Long), `nome` (String), `descricao` (String), `cargaHoraria` (Integer), `ordem` (Integer), `materias` (Lista de `MateriaResponse`) | Retorna o módulo com as suas matérias aninhadas. |

---

### Matérias

| DTO | Direção | Campos | Validações |
|---|---|---|---|
| `MateriaRequestDTO` | Entrada (`POST`) | `idModulo` (Long), `nome` (String) | `idModulo`: obrigatório, maior que zero, deve ser um módulo existente.<br>`nome`: obrigatório, de 2 a 128 caracteres, único dentro do mesmo módulo. |
| `MateriaResponse` | Saída | `id` (Long), `idModulo` (Long), `nome` (String) | Retorna os dados básicos da matéria cadastrada. |

---

### Turmas

| DTO | Direção | Campos | Validações |
|---|---|---|---|
| `TurmaRequestDTO` | Entrada (`POST`) | `idModulo` (Long), `idProfessor` (Long), `tipoPeriodo` (EnumTipoPeriodoTurma), `turno` (EnumTurnoTurma), `vagas` (Integer), `dataInicio` (LocalDate), `dataFim` (LocalDate) | `idModulo`, `idProfessor`: obrigatórios, maiores que zero e existentes.<br>`tipoPeriodo`: obrigatório (`SEMESTRE`, `BIMESTRE`, `TRIMESTRE`, `QUADRIMESTRE`).<br>`turno`: obrigatório (`MATUTINO`, `VESPERTINO`, `NOTURNO`, `INTEGRAL`).<br>`vagas`: obrigatório, deve ser maior que zero.<br>`dataInicio`: opcional, deve ser maior ou igual à data de hoje.<br>`dataFim`: opcional; se enviada exige `dataInicio`, deve ser posterior a ela e não pode ultrapassar a duração do `tipoPeriodo` (2, 3, 4 ou 6 meses a partir do início). |
| `AtualizarTurmaRequestDTO` | Entrada (`PATCH`) | `idProfessor` (Long), `vagas` (Integer), `dataInicio` (LocalDate), `dataFim` (LocalDate) | Todos os campos são opcionais.<br>`idProfessor`: maior que zero e existente.<br>`vagas`: maior que zero.<br>`dataInicio`: se enviada, maior ou igual a hoje.<br>`dataFim`: validada contra a `dataInicio` (enviada ou já gravada) e o `tipoPeriodo` da turma.<br>Módulo, `tipoPeriodo` e `turno` não podem ser alterados. O `periodoLetivo` é recalculado. |
| `TurmaResponseDTO` | Saída | `id` (Long), `idModulo` (Long), `idProfessor` (Long), `periodoLetivo` (String), `tipoPeriodo` (String), `turno` (String), `vagas` (Integer), `dataInicio` (LocalDate), `dataFim` (LocalDate) | `periodoLetivo` é gerado automaticamente pelo back-end no formato `YYYY/p`, onde `p` é a posição do período no ano (ex.: semestre que começa em novembro → `2026/2`). |

---

### Matrículas

| DTO | Direção | Campos | Validações |
|---|---|---|---|
| `MatricularAlunoRequestDTO` | Entrada (`POST`) | `idAluno` (Long), `idCurso` (Long) | `idAluno`, `idCurso`: obrigatórios e maiores que zero. A turma não é informada: a API escolhe a primeira turma aberta do curso e valida duplicidade, vagas e as demais regras antes de confirmar. |
| `AtualizarStatusMatriculaDTO` | Entrada (`PATCH`) | `status` (EnumStatusMatricula) | `status`: obrigatório (`ATIVO`, `MATRICULADO`, `AFASTADO`, `TRANCADO`, `CONCLUIDO`). |
| `MatricularAlunoResponseDTO` | Saída (`POST`) | `idMatricula` (Long), `idAluno` (Long), `idCurso` (Long), `idTurma` (Long), `dataMatricula` (LocalDate), `status` (EnumStatusMatricula) | Retorna o ID do vínculo criado, a data gerada automaticamente e o status inicial (`MATRICULADO`). |
| `MatriculaAlunoTurmaResponseDTO` | Saída (`GET`, `PATCH`) | `idMatricula` (Long), `idAluno` (Long), `idCurso` (Long), `dataMatricula` (LocalDate), `status` (EnumStatusMatricula), `idTurma` (Long) | Retorna os dados da matrícula. |

---

### Alunos

| DTO | Direção | Campos | Validações |
|---|---|---|---|
| `AlunoRequestDTO` | Entrada (`POST`) | `nome` (String), `cpf` (String), `email` (String) | `nome`: obrigatório, de 2 a 255 caracteres.<br>`cpf`: obrigatório, exatamente 11 caracteres.<br>`email`: obrigatório, formato de e-mail válido, até 255 caracteres. |
| `AlunoResponseDTO` | Saída | `id` (Long),  `nome` (String), `cpf` (String), `email` (String), `matricula` (String) | `matricula` é a Matrícula Global/RA gerada automaticamente pelo sistema com até 64 caracteres. |

---

### Professores

| DTO | Direção | Campos | Validações |
|---|---|---|---|
| `ProfessorRequestDTO` | Entrada (`POST`) | `nome` (String), `cpf` (String), `email` (String) | `nome`: obrigatório, de 2 a 255 caracteres.<br>`cpf`: obrigatório, exatamente 11 caracteres.<br>`email`: obrigatório, formato de e-mail válido, até 255 caracteres. |
| `ProfessorResponseDTO` | Saída | `id` (Long),  `nome` (String), `cpf` (String), `email` (String) | Retorna os dados do professor cadastrado e o vínculo com a tabela Pessoa. |

---

### DTOs de apoio

| DTO | Direção | Campos | Validações |
|---|---|---|---|
| `PageResponse<T>` | Saída (listas paginadas) | `content` (Lista de `T`), `totalElements` (long), `totalPages` (long), `size` (long), `page` (long) | `content` traz os itens da página pedida; `page` começa em `0`. |
| `ErroDTO` | Saída (erros) | `status` (int), `erro` (String), `mensagem` (String), `caminho` (String), `timestamp` (LocalDateTime), `campos` (Lista de `CampoErroDTO`) | Ver [Erros](#erros). |
| `CampoErroDTO` | Saída (dentro de `ErroDTO`) | `campo` (String), `mensagem` (String) | Um item por campo inválido. |

## Exemplos

Um par requisição/resposta por entidade.

### `POST /cursos`

**Requisição (Body):**
```json
{
  "titulo": "Desenvolvimento Backend Avançado",
  "subtitulo": "Formação completa em arquitetura de microsserviços e nuvem",
  "resumo": "Aprenda a construir APIs resilientes, escaláveis e prontas para produção utilizando Java, Spring Boot, Docker e os principais provedores de nuvem do mercado.",
  "slug": "desenvolvimento-backend-avancado",
  "status": "RASCUNHO"
}
```

**Resposta (Sucesso - 201 Created):**
```json
{
  "id": 1,
  "titulo": "Desenvolvimento Backend Avançado",
  "subtitulo": "Formação completa em arquitetura de microsserviços e nuvem",
  "resumo": "Aprenda a construir APIs resilientes, escaláveis e prontas para produção utilizando Java, Spring Boot, Docker e os principais provedores de nuvem do mercado.",
  "slug": "desenvolvimento-backend-avancado",
  "status": "RASCUNHO"
}
```

---

### `POST /modulos`

**Requisição (Body):**
```json
{
  "idCurso": 1,
  "nome": "Arquitetura Web, Protocolo HTTP e APIs",
  "descricao": "Neste módulo você compreenderá os fundamentos da internet, o ciclo de request/response e as boas práticas de design RESTful.",
  "cargaHoraria": 40,
  "ordem": 1
}
```

**Resposta (Sucesso - 201 Created):**
```json
{
  "id": 12,
  "idCurso": 1,
  "nome": "Arquitetura Web, Protocolo HTTP e APIs",
  "descricao": "Neste módulo você compreenderá os fundamentos da internet, o ciclo de request/response e as boas práticas de design RESTful.",
  "cargaHoraria": 40,
  "ordem": 1
}
```

---

### `POST /materias`

**Requisição (Body):**
```json
{
  "idModulo": 12,
  "nome": "Estilos Arquiteturais e Richardson Maturity Model"
}
```

**Resposta (Sucesso - 201 Created):**
```json
{
  "id": 85,
  "idModulo": 12,
  "nome": "Estilos Arquiteturais e Richardson Maturity Model"
}
```

---

### `POST /turmas`

**Requisição (Body):**
```json
{
  "idModulo": 12,
  "idProfessor": 3,
  "tipoPeriodo": "SEMESTRE",
  "turno": "NOTURNO",
  "vagas": 30,
  "dataInicio": "2026-11-01",
  "dataFim": "2027-04-30"
}
```

**Resposta (Sucesso - 201 Created):**
```json
{
  "id": 7,
  "idModulo": 12,
  "idProfessor": 3,
  "periodoLetivo": "2026/2",
  "tipoPeriodo": "SEMESTRE",
  "turno": "NOTURNO",
  "vagas": 30,
  "dataInicio": "2026-11-01",
  "dataFim": "2027-04-30"
}
```

---

### `POST /matriculas`

**Requisição (Body):**
```json
{
  "idAluno": 5,
  "idCurso": 1
}
```

**Resposta (Sucesso - 201 Created):**
```json
{
  "idMatricula": 21,
  "idAluno": 5,
  "idCurso": 1,
  "idTurma": 7,
  "dataMatricula": "2026-10-26",
  "status": "MATRICULADO"
}
```

---

### `POST /alunos`

**Requisição (Body):**
```json
{
  "nome": "Maria da Silva",
  "cpf": "12345678901",
  "email": "maria.silva@email.com"
}
```

**Resposta (Sucesso - 200 OK):**
```json
{
  "id": 5,
  "nome": "Maria da Silva",
  "cpf": "12345678901",
  "email": "maria.silva@email.com",
  "matricula": "2026170482"
}
```

---

### `POST /professores`

**Requisição (Body):**
```json
{
  "nome": "João Pereira",
  "cpf": "98765432100",
  "email": "joao.pereira@email.com"
}
```

**Resposta (Sucesso - 200 OK):**
```json
{
  "id": 3,
  "nome": "João Pereira",
  "cpf": "98765432100",
  "email": "joao.pereira@email.com"
}
```

## Erros

Todo erro sai no mesmo formato. Este é o `404` de `GET /cursos/999`:

```json
{
  "status": 404,
  "erro": "Not Found",
  "mensagem": "Curso não encontrado: 999",
  "caminho": "/cursos/999",
  "timestamp": "2026-10-06T19:30:00",
  "campos": []
}
```

No `400` de validação, a lista `campos` traz um item por campo inválido, ordenada por campo: `{"campo": "nome", "mensagem": "Nome é obrigatório"}`.

| Status | Quando acontece neste projeto |
|---|---|
| `400` | JSON ilegível (a mensagem pede para conferir o JSON e o formato das datas `yyyy-MM-dd`) ou campo inválido no DTO de entrada |
| `404` | Id que não existe, inclusive o id de uma entidade relacionada enviado no corpo (curso, módulo, professor, aluno) |
| `400` | Regra de negócio violada: a `mensagem` diz qual regra barrou o pedido |
