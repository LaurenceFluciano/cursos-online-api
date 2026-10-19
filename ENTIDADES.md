# Entidades

Aluno | Um estudante | Um estudante pode se matricular em vários cursos
Curso | Um curso específico | Pertence a uma instituição, pode ter vários alunos

Matricula | Relacionamento `aluno-curso`

## Aluno

```sql
id    Serial              // PK
cpf     String NOT NULL // pode ser usado como PK em vez do id
nome    String NOT NULL
email    String NOT NULL
matricula String NOT NULL // ou UInt? Refere-se a numero de matricula do aluno na instituição
```

## Curso

```sql
id   Serial // pode ser usado como código
nome  String
periodo String // MATUTINO, VESPERTINO, NOTURNO
materias String // TODO: dividir em uma tabela nova de matérias
```

## Matricula (aluno <-> curso)

```sql
id_aluno   // FK (alunos)
id_curso   // FK (alunos)
ativo   Boolean // a matricula está ativa? o aluno trancou? etc..
```
