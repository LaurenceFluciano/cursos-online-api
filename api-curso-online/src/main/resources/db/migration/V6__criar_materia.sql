create table materia (
    id bigint generated always as identity,
    id_modulo bigint not null,
    nome varchar(128) not null,
    constraint pk_materia primary key (id),
    constraint fk_materia_modulo foreign key (id_modulo) references modulo(id)
);