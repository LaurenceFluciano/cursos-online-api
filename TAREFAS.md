# Tarefas

## Database

- [ ] Criar migrations em `src/main/resources/db/migration` seguindo a modelagem definida em [Dominio](./api-curso-online//docs//modelo-de-dominio.md)

## Backend

### Implementação da camada Model

- [ ] Criar as entidades Pessoa, Aluno e Professor seguindo a modelagem definida em [Dominio](./api-curso-online//docs//modelo-de-dominio.md)
- [ ] Criar as entidades Curso, Módulo e Materia seguindo a modelagem definida em [Dominio](./api-curso-online//docs//modelo-de-dominio.md)
- [ ] Criar as entidades Turma e MatriculaAlunoTurma seguindo a modelagem definida em [Dominio](./api-curso-online//docs//modelo-de-dominio.md)

### Implementação da camada Repository

- [ ] Criar repository base `JpaRepository` das entidades Pessoa, Aluno e Professor.
- [ ] Criar repository base `JpaRepository` das entidades Curso, Módulo e Materia.
- [ ] Criar repository base `JpaRepository` das entidades Turma e MatriculaAlunoTurma.

#### Métodos especificos em `AlunoRepository`

- [ ] No repositório de alunos ao listar eles usando o método `findAll` deve ser paginado e retornar a entidade com `Pessoa` junto
- [ ] No repositório de alunos ao pegar por id, email, matricula ou nome retornar a entidade com `Pessoa` junto


#### Métodos especificos em `ProfesorRepository`

- [ ] No repositório de professores ao listar eles usando o método `findAll` deve ser paginado e retornar a entidade com `Pessoa` junto
- [ ] No repositório de professores ao pegar por id, email ou nome retornar a entidade com `Pessoa` junto


#### Métodos especificos em `CursoRepository`

- [ ] No repositório de cursos, ao pegar um por id ou slug a camada deve ter um método que lista os módulos existente dele com suas respectivas matérias.
- [ ] No repositório de cursos ao listar com o método `findAll` deve ser paginado. **Observe: Ele não DEVE listar os módulos existentes.**

#### Métodos especificos em `ModuloRepository`

- [ ] No repositório de módulos ao listar com o método `findAll` deve ser paginado.
- [ ] No repositório de módulos ao pegar com id deve mostrar o módulo e suas respectivas matérias.

#### Métodos especificos em `TurmaRepository`

- [ ] No repositório de Turma ao listar com o método `findAll` deve ser paginado juntamente com o módulo respectivo.
- [ ] No repositório de Turma ao pegar por id deve mostrar o módulo e as respectivas matérias.
- [ ] Precisamos de um operação de consulta que mostre os alunos e professores de uma turma ao passar o seu `id`

### A decidir 


- [ ] Precisamos de uma operação de consulta que mostre histórico turmas (deve ser páginado) que um aluno ao participou `id_pessoa`. **Observe: consideramos até aquelas que ele já fez**
- [ ] Precisamos de uma operação de consulta que mostre histórico turmas (deve ser páginado) de um professor lecionou `id_pessoa`. **Observe: consideramos até aquelas que ele já lecionou**
- [ ] Precisamos de uma operação que mostre as turmas atuais que um aluno participa
- [ ] Precisamos de uma operação que mostre as turmas atuais que um professor leciona

### Revisões após implementação BackEnd

- [ ] Verificar se a seção [Relacionamentos](./api-curso-online//docs//modelo-de-dominio.md#relacionamentos) esta correspondente com o código fonte do `model`
- [ ] Verificar se as migrations implementadas em `src/main/resources/db/migration` seguem a estrutura de [Migrations](./api-curso-online//docs//modelo-de-dominio.md#migrations)
- [ ] Verificar e arrumar se a API esta condizente com a documentação principal [README](./api-curso-online/README.md);