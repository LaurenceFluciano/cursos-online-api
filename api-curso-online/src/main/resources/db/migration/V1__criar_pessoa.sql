create table pessoa (
    id bigint generated always as identity,
    cpf varchar(11) not null unique,
    email varchar(255) not null unique,
    nome varchar(255) not null,
    constraint pk_pessoa primary key (id)
);