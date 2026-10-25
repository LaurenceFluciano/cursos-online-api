create table matricula_aluno_turma(
    id bigint generated always as identity,
    id_turma bigint not null,
    id_aluno bigint not null,
    data_matricula date not null,
    status varchar(32) not null,
    
    constraint pk_matricula_aluno_turma primary key (id),
    constraint fk_matricula_aluno_turma_turma foreign key (id_turma) references turma(id),
    constraint fk_matricula_aluno_turma_aluno foreign key (id_aluno) references aluno(id)
);