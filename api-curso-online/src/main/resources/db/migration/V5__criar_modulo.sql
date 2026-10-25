create table modulo(
    id bigint generated always as identity,
    id_curso bigint not null,
    nome varchar(128) not null,
    descricao varchar(1024),
    carga_horaria int not null,
    ordem int not null,
    constraint pk_modulo primary key (id),
    constraint fk_modulo_curso foreign key (id_curso) references curso(id),
    constraint chk_ordem_positiva check (ordem > 0)
);