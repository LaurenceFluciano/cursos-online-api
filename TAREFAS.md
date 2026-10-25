# Tarefas

## Database

- [ x ] Criar migrations em `src/main/resources/db/migration` seguindo a modelagem definida em [Dominio](./api-curso-online//docs//modelo-de-dominio.md) - Laurence

## Backend

### Implementação da camada Model

- [ x] Criar as entidades Pessoa, Aluno e Professor seguindo a modelagem definida em [Dominio](./api-curso-online//docs//modelo-de-dominio.md) - Laurence

- [ ] Criar as entidades Curso, Módulo e Materia seguindo a modelagem definida em [Dominio](./api-curso-online//docs//modelo-de-dominio.md) - Antonio

- [ ] Criar as entidades Turma e MatriculaAlunoTurma seguindo a modelagem definida em [Dominio](./api-curso-online//docs//modelo-de-dominio.md) - Igor

### Implementação da camada Repository

- [ ] Criar repository base `JpaRepository` das entidades Pessoa, Aluno e Professor. - Laurence
- [ ] Criar repository base `JpaRepository` das entidades Curso, Módulo e Materia. - Igor
- [ ] Criar repository base `JpaRepository` das entidades Turma e MatriculaAlunoTurma. - Antonio

#### Métodos especificos em `AlunoRepository`

- [ ] No repositório de alunos ao listar eles usando o método `findAll` deve ser paginado e retornar a entidade com `Pessoa` junto - Laurence
- [ ] No repositório de alunos ao pegar por id, email, matricula ou nome retornar a entidade com `Pessoa` junto - Laurence


#### Métodos especificos em `ProfesorRepository`

- [ ] No repositório de professores ao listar eles usando o método `findAll` deve ser paginado e retornar a entidade com `Pessoa` junto - Laurence
- [ ] No repositório de professores ao pegar por id, email ou nome retornar a entidade com `Pessoa` junto - Laurence


#### Métodos especificos em `CursoRepository`

- [ ] No repositório de cursos, ao pegar um por id ou slug a camada deve ter um método que lista os módulos existente dele com suas respectivas matérias. - Igor
- [ ] No repositório de cursos ao listar com o método `findAll` deve ser paginado. **Observe: Ele não DEVE listar os módulos existentes.** - Antonio

#### Métodos especificos em `ModuloRepository`

- [ ] No repositório de módulos ao listar com o método `findAll` deve ser paginado. - Antonio
- [ ] No repositório de módulos ao pegar com id deve mostrar o módulo e suas respectivas matérias. - Igor

#### Métodos especificos em `TurmaRepository`

- [ ] No repositório de Turma ao listar com o método `findAll` deve ser paginado juntamente com o módulo respectivo. - Igor
- [ ] No repositório de Turma ao pegar por id deve mostrar o módulo e as respectivas matérias. - Antonio
- [ ] Precisamos de um operação de consulta que mostre os alunos e professores de uma turma ao passar o seu `id` - Igor

### A decidir 

- [ ] Precisamos de uma operação de consulta que mostre histórico turmas (deve ser páginado) que um aluno ao participou `id_pessoa`.
- [ ] Precisamos de uma operação de consulta que mostre histórico turmas (deve ser páginado) de um professor lecionou `id_pessoa`.
- [ ] Precisamos de uma operação que mostre as turmas atuais que um aluno participa
- [ ] Precisamos de uma operação que mostre as turmas atuais que um professor leciona

### Contratos da API e DTOs

- [ x ] Definir DTOs de Entrada (Requests) de Pessoa, Aluno e Professor. - Laurence
- [ x ] Definir DTOs de Saída (Responses) de Pessoa, Aluno e Professor. - Laurence
- [ x ] Definir DTOs de Entrada (Requests) de Cursos, Módulos e Turmas. - Laurence
- [ x ] Definir DTOs de Saída (Responses) de Cursos, Módulos e Turmas. - Laurence
- [ x ] Definir DTOs de Entrada (Requests) de Turma e MatriculaAlunoTurma. - Laurence
- [ x ] Definir DTOs de Saída (Responses) de Turma e MatriculaAlunoTurma. - Laurence
- [  ] Implementar DTOs de Entrada (Requests) de Pessoa, Aluno e Professor. - Laurence
- [  ] Implementar DTOs de Saída (Responses) de Pessoa, Aluno e Professor. - Laurence
- [  ] Implementar DTOs de Entrada (Requests) de Cursos, Módulos e Turmas. - Laurence
- [  ] Implementar DTOs de Saída (Responses) de Cursos, Módulos e Turmas. - Laurence
- [  ] Implementar DTOs de Entrada (Requests) de Turma e MatriculaAlunoTurma. - Laurence
- [  ] Implementar DTOs de Saída (Responses) de Turma e MatriculaAlunoTurma. - Laurence

### Service e Regras de Negócio

...

### End points e Controller

...

### Revisões após implementação BackEnd

- [ ] Verificar se a seção [Relacionamentos](./api-curso-online//docs//modelo-de-dominio.md#relacionamentos) esta correspondente com o código fonte do `model`
- [ ] Verificar se as migrations implementadas em `src/main/resources/db/migration` seguem a estrutura de [Migrations](./api-curso-online//docs//modelo-de-dominio.md#migrations)
- [ ] Verificar e arrumar se a API esta condizente com a documentação principal [README](./api-curso-online/README.md);