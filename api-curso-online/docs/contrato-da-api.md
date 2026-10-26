# Contrato da API

> O contrato é o que o cliente da API enxerga: rotas, JSON e status. Desenhe as rotas de **todas** as entidades. Nesta etapa precisam funcionar as três da API simples e as que as regras de negócio usam. As linhas marcadas com *(exemplo)* mostram o nível de detalhe esperado: apague e escreva as do grupo.

## Convenções

| Item | Valor |
|---|---|
| Endereço base | `http://localhost:8080` |
| Formato | JSON, na requisição e na resposta |
| Datas | `yyyy-MM-dd` |
| Erros | Sempre no formato `ErroDTO` (ver [Erros](#erros)) |
| Documentação viva | `http://localhost:8080/swagger-ui.html` |

## Rotas

Coluna **Funciona**: `sim` para as rotas que já respondem nesta etapa: as da API simples e as que as regras de negócio usam.

### Cursos

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `GET` | `/cursos` | Lista os cursos | Nenhuma | lista de `CursoResponseDTO` | `200` | Nenhum | não |
| `GET` | `/cursos/{slug}` | Detalha um curso específico com seus módulos e matérias | Nenhuma | `CursoCompletoResponseDTO` | `200` | `404` | não |
| `GET` | `/cursos/{id}` | Detalha um curso específico com seus módulos e matérias | Nenhuma | `CursoCompletoResponseDTO` | `200` | `404` | não |
| `POST` | `/cursos` | Cadastra um novo curso | `CursoRequestDTO` | `CursoResponseDTO` | `201` | `400` | não |
| `PUT` | `/cursos/{id}` | Atualiza os dados cadastrais de um curso | `AtualizarCursoRequestDTO` | `CursoResponseDTO` | `200` | `400`, `404` | não |
| `DELETE` | `/cursos/{id}` | Remove um curso do catálogo | Nenhuma | Vazio | `204` | `400`, `404` | não |

### Modulos

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `POST` | `/modulos` | Adiciona um módulo a um curso | `ModuloRequestDTO` | `ModuloResponseDTO` | `201` | `400` | não |
| `PUT` | `/modulos/{id}` | Atualiza os dados de um módulo | `AtualizarModuloDTO` | `ModuloResponseDTO` | `200` | `400`, `404` | não |
| `DELETE` | `/modulos/{id}` | Remove um módulo | Nenhuma | Vazio | `204` | `400`, `404` | não |


### Materias

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `POST` | `/materias` | Adiciona uma matéria a um módulo | `MateriaRequestDTO` | `MateriaResponseDTO` | `201` | `400` | não |
| `DELETE` | `/materias/{id}` | Remove uma matéria | Nenhuma | Vazio | `204` | `404` | não |


### Turmas 

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `POST` | `/turmas` | Cria uma turma | `TurmaRequestDTO` | `TurmaResponseDTO` | `201` | `400` | não |
| `GET` | `/turmas` | Lista os turmas | Nenhuma | lista de lista de `TurmaResponseDTO` | `200` | Nenhum | não |
| `GET` | `/turmas/{id}` | Busca uma turma por id | Nenhuma | `TurmaResponseDTO` | `200` | `404` | não |
 `PATCH` | `/turmas/{id}` | Atualiza uma turma | `AtualizarTurmaRequestDTO` | `CursoResponseDTO` | `200` | `400`, `404` | não |
| `DELETE` | `/turmas/{id}` | Remove uma turma | Nenhuma | Nenhuma (sem corpo) | `204` | `404` | não |


### Matriculas

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|--------|---------------|--------------------------------|-----------------------------|------------------------------|-------|-------|-----|
| `POST` | `/matriculas` | Matricula um aluno em um curso e turma | `MatricularAlunoRequestDTO` | `MatricularAlunoResponseDTO` | `201` | `400` | não |

### Alunos

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `POST` | `/alunos` | Cadastra um aluno caso não existe e gera a matricula | `AlunoRequestDTO` | `AlunoResponseDTO` | `201` | `400` | não |
| `GET` | `/alunos/{id}` | Busca um aluno por id | Nenhuma | `AlunoResponseDTO` | `200` | Nenhum | não |
| `GET` | `/alunos/` | Lista os alunos | Nenhuma | lista de `AlunoResponseDTO` | `200` | Nenhum | não |

###  Professores

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `POST` | `/professores` | Cadastra um professor caso não exista | `ProfessorRequestDTO` | `ProfessorResponseDTO` | `201` | `400` | não |
| `GET` | `/professores/{id}` | Busca um professor por id | Nenhuma | `ProfessorResponseDTO` | `200` | Nenhum | não |
| `GET` | `/professores/` | Lista os professores | Nenhuma | lista de `ProfessorResponseDTO` | `200` | Nenhum | não |

### Rotas que não são CRUD

As que nascem das regras de negócio do README. Estas precisam funcionar nesta etapa.

| Verbo | Caminho | O que faz | Regra | Sucesso | Erros |
|---|---|---|---|---|---|
| `POST` | `/matriculas` | Matricula um aluno em uma turma e curso automaticamente | R1, R2, R3, R4 e R5 | `201` | `400` |
| ... | ... | ... | ... | ... | ... |

## DTOs

O que entra não é igual ao que sai: o cliente não envia `id` nem campos que a API decide.


### Cursos

| DTO | Direção | Campos | Validações |
|---|---|---|---|
| `CursoRequestDTO` | Entrada (`POST`) | `titulo` (String), `subtitulo` (String), `resumo` (String), `slug` (String), `status` (EnumStatusCurso) | `titulo`: obrigatório, de 2 a 128 caracteres.<br>`subtitulo`: opcional, de 2 a 255 caracteres.<br>`resumo`: opcional, de 16 a 1024 caracteres.<br>`slug`: obrigatório, único, de 2 a 128 caracteres, apenas alfanuméricos e '-'.<br>`status`: obrigatório, valores válidos: `RASCUNHO`, `PUBLICADO`, `ARQUIVADO`. |
| `AtualizarCursoRequestDTO` | Entrada (`PUT`) | `titulo` (String), `subtitulo` (String), `resumo` (String), `slug` (String), `status` (EnumStatusCurso) | Mesmas validações do `CursoRequestDTO`, mas aplicado para atualização parcial ou total. |
| `CursoResponseDTO` | Saída | `id` (Long), `titulo` (String), `subtitulo` (String), `resumo` (String), `slug` (String), `status` (String) | Retorna os dados cadastrais básicos do curso. |
| `CursoCompletoResponseDTO` | Saída | `id` (Long), `titulo` (String), `subtitulo` (String), `resumo` (String), `slug` (String), `status` (String), `modulos` (Lista de `ModuloComMateriasResponseDTO`) | Retorna a árvore pedagógica completa do curso (Módulos e suas respectivas Matérias aninhados). |

---

### Módulos

| DTO | Direção | Campos | Validações |
|---|---|---|---|
| `ModuloRequestDTO` | Entrada (`POST`) | `idCurso` (Long), `nome` (String), `descricao` (String), `cargaHoraria` (Integer), `ordem` (Integer) | `idCurso`: obrigatório, deve ser um curso existente.<br>`nome`: obrigatório, de 2 a 128 caracteres.<br>`descricao`: opcional, de 16 a 1024 caracteres.<br>`cargaHoraria`: obrigatório, deve ser maior que zero.<br>`ordem`: obrigatório, deve ser maior que zero e único por curso. |
| `AtualizarModuloDTO` | Entrada (`PUT`) | `nome` (String), `descricao` (String), `cargaHoraria` (Integer), `ordem` (Integer) | `nome`, `cargaHoraria`, `ordem`: obrigatórios se enviados, seguindo as mesmas restrições de validação de valor do cadastro. |
| `ModuloResponseDTO` | Saída | `id` (Long), `idCurso` (Long), `nome` (String), `descricao` (String), `cargaHoraria` (Integer), `ordem` (Integer) | Retorna os dados básicos do módulo criado ou alterado. |

---

### Matérias

| DTO | Direção | Campos | Validações |
|---|---|---|---|
| `MateriaRequestDTO` | Entrada (`POST`) | `idModulo` (Long), `nome` (String) | `idModulo`: obrigatório, deve ser um módulo existente.<br>`nome`: obrigatório, de 2 a 128 caracteres, único dentro do mesmo módulo. |
| `MateriaResponseDTO` | Saída | `id` (Long), `idModulo` (Long), `nome` (String) | Retorna os dados básicos da matéria cadastrada. |

---

###  Turmas

| DTO | Direção | Campos | Validações |
|---|---|---|---|
| `TurmaRequestDTO` | Entrada (`POST`) | `idModulo` (Long), `idProfessor` (Long), `tipoPeriodo` (EnumTipoPeriodoTurma), `turno` (EnumTurnoTurma), `vagas` (Integer), `dataInicio` (Date), `dataFim` (Date) | `idModulo`, `idProfessor`: obrigatórios.<br>`tipoPeriodo`: obrigatório (`SEMESTRE`, `BIMESTRE`, `TRIMESTRE`, `QUADRIMESTRE`).<br>`turno`: obrigatório (`MATUTINO`, `VESPERTINO`, `NOTURNO`, `INTEGRAL`).<br>`vagas`: obrigatório, deve ser maior que zero.<br>`dataInicio`: opcional, deve ser maior ou igual à data de hoje. |
| `AtualizarTurmaRequestDTO` | Entrada (`PATCH`) | `idProfessor` (Long), `vagas` (Integer), `dataInicio` (Date), `dataFim` (Date) | Campos opcionais para ajuste ou remanejamento de professores e vagas. |
| `TurmaResponseDTO` | Saída | `id` (Long), `idModulo` (Long), `idProfessor` (Long), `periodoLetivo` (String), `tipoPeriodo` (String), `turno` (String), `vagas` (Integer), `dataInicio` (Date), `dataFim` (Date) | `periodoLetivo` é gerado automaticamente pelo back-end no formato `YYYY/p`. |

---

### Matrículas

| DTO | Direção | Campos | Validações |
|---|---|---|---|
| `MatricularAlunoRequestDTO` | Entrada (`POST`) | `idAluno` (Long), `idCurso` (Long) | `idAluno`, `idCurso`: obrigatórios. O sistema valida se existem vagas disponíveis na turma antes de confirmar. |
| `MatricularAlunoResponseDTO` | Saída | `idMatricula` (Long), `idAluno` (Long), `idCurso` (Long), `dataMatricula` (Timestamp), `status` (String), `idTurma` (Long) | Retorna o ID do vínculo criado, a data gerada automaticamente e o status inicial (`Matriculado`/`Ativo`). |

---

### Alunos

| DTO | Direção | Campos | Validações |
|---|---|---|---|
| `AlunoRequestDTO` | Entrada (`POST`) | `nome` (String), `cpf` (String), `email` (String) | `nome`: obrigatório, até 255 caracteres.<br>`cpf`: obrigatório, deve ser um CPF válido, contendo apenas 11 dígitos numéricos.<br>`email`: obrigatório, formato de e-mail válido, até 255 caracteres. |
| `AlunoResponseDTO` | Saída | `id` (Long),  `nome` (String), `cpf` (String), `email` (String), `matricula` (String) | `matricula` é a Matrícula Global/RA gerada automaticamente pelo sistema com até 64 caracteres. |

---

### Professores

| DTO | Direção | Campos | Validações |
|---|---|---|---|
| `ProfessorRequestDTO` | Entrada (`POST`) | `nome` (String), `cpf` (String), `email` (String) | `nome`: obrigatório, até 255 caracteres.<br>`cpf`: obrigatório, deve ser um CPF válido.<br>`email`: obrigatório, formato de e-mail válido. |
| `ProfessorResponseDTO` | Saída | `id` (Long),  `nome` (String), `cpf` (String), `email` (String) | Retorna os dados do professor cadastrado e o vínculo com a tabela Pessoa. |



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
  "status": "PUBLICADO"
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
  "status": "PUBLICADO"
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


## Erros

Todo erro sai no mesmo formato. Este é o `404` de `GET /cursos/999`:

```json
{
  "status": 404,
  "erro": "Not Found",
  "mensagem": "Cursos não encontrado: 999",
  "caminho": "/cursos/999",
  "timestamp": "2026-10-06T19:30:00",
  "campos": []
}
```

No `400` de validação, a lista `campos` traz um item por campo inválido: `{"campo": "nome", "mensagem": "Nome é obrigatório"}`.

| Status | Quando acontece neste projeto |
|---|---|
| `400` | JSON ilegível ou campo inválido no DTO de entrada |
| `404` | Id que não existe |
| `400` | Regra de negócio violada: a `mensagem` diz qual regra barrou o pedido |
