# Tarefas

## Database

- [ x ] Criar migrations em `src/main/resources/db/migration` seguindo a modelagem definida em [Dominio](./api-curso-online//docs//modelo-de-dominio.md) - Laurence

## Backend

### Implementação da camada Model

- [ x] Criar as entidades Pessoa, Aluno e Professor seguindo a modelagem definida em [Dominio](./api-curso-online//docs//modelo-de-dominio.md) - Laurence

- [ ] Criar as entidades Curso, Módulo e Materia seguindo a modelagem definida em [Dominio](./api-curso-online//docs//modelo-de-dominio.md) - Antonio

- [ ] Criar as entidades Turma e MatriculaAlunoTurma seguindo a modelagem definida em [Dominio](./api-curso-online//docs//modelo-de-dominio.md) - Igor

### Implementação da camada Repository

- [ ] Criar PessoaRepository. - Laurence
- [ ] Criar AlunoRepository. - Laurence
- [ ] Criar ProfessorRepository. - Laurence
- [ ] Criar CursoRepository. - Antonio
- [ ] Criar ModuloRepository. - Antonio
- [ ] Criar MateriaRepository. - Antonio
- [ ] Criar TurmaRepository. - Igor
- [ ] Criar MatriculaAlunoTurmaRepository. - Igor

### Implementação na camada de Contratos da API e DTOs

- [ x ] Definir DTOs de Entrada (Requests) de Pessoa, Aluno e Professor. - Laurence
- [ x ] Definir DTOs de Saída (Responses) de Pessoa, Aluno e Professor. - Laurence
- [ x ] Definir DTOs de Entrada (Requests) de Cursos, Módulos e Turmas. - Laurence
- [ x ] Definir DTOs de Saída (Responses) de Cursos, Módulos e Turmas. - Laurence
- [ x ] Definir DTOs de Entrada (Requests) de Turma e MatriculaAlunoTurma. - Laurence
- [ x ] Definir DTOs de Saída (Responses) de Turma e MatriculaAlunoTurma. - Laurence
- [  ] Implementar DTOs de Entrada (Requests) de Pessoa, Aluno e Professor. - Laurence
- [  ] Implementar DTOs de Saída (Responses) de Pessoa, Aluno e Professor. - Laurence
- [  ] Implementar DTOs de Entrada (Requests) de Cursos, Módulos e Turmas. - Antonio
- [  ] Implementar DTOs de Saída (Responses) de Cursos, Módulos e Turmas. - Antonio
- [  ] Implementar DTOs de Entrada (Requests) de Turma e MatriculaAlunoTurma. - Igor
- [  ] Implementar DTOs de Saída (Responses) de Turma e MatriculaAlunoTurma. - Igor

### Implementação na camada de Services

- [  ] Implementar AlunoService - Laurence
- [  ] Implementar ProfessorService - Laurence
- [  ] Implementar MatriculaService - Igor
- [  ] Implementar TurmaService - Igor
- [  ] Implementar CursoService - Antonio
- [  ] Implementar ModuloService - Antonio
- [  ] Implementar MateriaService - Antonio

### Implementação na camada de Controller

- [  ] Implementar AlunoController - Laurence
- [  ] Implementar ProfessorController - Laurence
- [  ] Implementar MatriculaController - Igor
- [  ] Implementar TurmaController - Igor
- [  ] Implementar CursoController - Antonio
- [  ] Implementar ModuloController - Antonio
- [  ] Implementar MateriaController - Antonio

### Revisões após implementação BackEnd

- [ ] Verificar se as regras de negócio do Backend estão na camada de service.
- [ ] Revisar se existem acoplamentos entre as camadas.
- [ ] Verificar se a seção [Relacionamentos](./api-curso-online//docs//modelo-de-dominio.md#relacionamentos) esta correspondente com o código fonte do `model`
- [ ] Verificar se as migrations implementadas em `src/main/resources/db/migration` seguem a estrutura de [Migrations](./api-curso-online//docs//modelo-de-dominio.md#migrations)
- [ ] Verificar e arrumar se a API esta condizente com a documentação principal [README](./api-curso-online/README.md);