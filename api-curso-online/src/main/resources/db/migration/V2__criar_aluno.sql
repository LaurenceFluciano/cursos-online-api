create table aluno (
    id bigint generated always as identity,
    id_pessoa bigint not null,
    matricula varchar(64) not null,

    constraint pk_aluno primary key (id),
    constraint fk_aluno_pessoa foreign key (id_pessoa) references pessoa(id)
);