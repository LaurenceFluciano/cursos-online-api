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
| `GET` | `/cursos/{id}` | Busca um cursos | Nenhuma | `CursoResponseDTO` | `200` | `404` | não |
| `POST` | `/cursos` | Cadastra um cursos | `CursoRequestDTO` | `CursoResponseDTO` | `201` | `400` | não |
| `PUT` | `/cursos/{id}` | Atualiza um cursos | `CursoRequestDTO` | `CursoResponseDTO` | `200` | `400`, `404` | não |
| `DELETE` | `/cursos/{id}` | Remove um cursos | Nenhuma | Nenhuma (sem corpo) | `204` | `404` | não |
| `POST` | `/cursos/{cursoId}/alunos/{alunoId}` | Matricula um aluno em um curso | `MatricularAlunoRequestDTO` | `MatricularAlunoResponseDTO` | `201` | `400` | não |
| `POST` | `/cursos/{cursoId}/professores/{professorId}` | Matricula um professor para dar aulas no curso | `MatricularProfessorRequestDTO` | `MatricularProfessorResponseDTO` | `201` | `400` | não |


### Alunos

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `POST` | `/alunos` | Matricula um aluno em um curso | `AlunoRequestDTO` | `AlunoResponseDTO` | `201` | `400` | não |
| `GET` | `/alunos/{id}` | Busca um aluno | Nenhuma | `AlunoResponseDTO` | `200` | Nenhum | não |
| `GET` | `/alunos/` | Lista os alunos | Nenhuma | lista de `AlunoResponseDTO` | `200` | Nenhum | não |

### {{Recurso 3}}

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `POST` | `/professores` | Matricula um aluno em um curso | `ProfessorRequestDTO` | `ProfessorResponseDTO` | `201` | `400` | não |
| `GET` | `/professores/{id}` | Busca um aluno | Nenhuma | `ProfessorResponseDTO` | `200` | Nenhum | não |
| `GET` | `/professores/` | Lista os alunos | Nenhuma | lista de `ProfessorResponseDTO` | `200` | Nenhum | não |

### Rotas que não são CRUD

As que nascem das regras de negócio do README. Estas precisam funcionar nesta etapa.

| Verbo | Caminho | O que faz | Regra | Sucesso | Erros |
|---|---|---|---|---|---|
| `POST` | `/cursos/{cursoId}/alunos/{alunoId}` | Matricula um aluno em um curso | R1, R2 e R3 | `201` | `400` |
| `POST` | `/cursos/{cursoId}/professores/{professorId}` | Matricula um professor para dar aulas no curso | R4 | `201` | `400` | 
| ... | ... | ... | ... | ... | ... |

## DTOs

O que entra não é igual ao que sai: o cliente não envia `id` nem campos que a API decide.

| DTO | Direção | Campos | Validações |
|---|---|---|---|
| `CursoRequestDTO` *(exemplo)* | entrada | `nome`, `periodo`, lista de `materias` | Todos os campos são obrigatórios obrigatórios (`@NotBlank`) |
| `CursoResponseDTO` *(exemplo)* | saída | `id`, `nome`, `periodo`, lista de `materias` | Nenhuma |
| `AlunoRequestDTO` *(exemplo)* | entrada | `cpf`, `nome`, `email`, `matricula` | Todos os campos são obrigatórios (`@NotBlank`) |
| `AlunoResponseDTO` *(exemplo)* | saída | `id`, `cpf`, `nome`, `email`, `matricula` | Nenhuma |
| `ProfessorRequestDTO` *(exemplo)* | entrada | `cpf`, `nome`, `email`, `matricula` | Todos os campos são obrigatórios (`@NotBlank`) |
| `ProfessorResponseDTO` *(exemplo)* | saída | `id`, `cpf`, `nome`, `email`, `matricula` | Nenhuma |
| `MatricularAlunoRequestDTO` *(exemplo)* | entrada | Nenhuma | Nenhuma |
| `MatricularAlunoResponseDTO` *(exemplo)* | saída | `idAluno`, `idCurso`, `ativo` | Nenhuma |
| `MatricularProfessorRequestDTO` *(exemplo)* | entrada | Nenhuma | Nenhuma |
| `MatricularProfessorResponseDTO` *(exemplo)* | saída | `idProfessor`, `idCurso`, `ativo` | Nenhuma |

| ... | ... | ... | ... |

## Exemplos

Um par requisição/resposta por entidade.

### `POST /cursos` *(exemplo)*

Requisição:

```json
{
  "nome": "Curso de backend",
  "periodo": "Noturno",
  "materias": ["Backend"]
}
```

Resposta, `201 Created`:

```json
{
  "id": 1,
  "titulo": "Curso de backend",
  "periodo": "Noturno",
  "materias": ["Backend"]
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
