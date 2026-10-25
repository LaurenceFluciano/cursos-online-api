create table curso(
    id bigint generated always as identity,
    titulo varchar(128) not null,
    subtitulo varchar(255),
    resumo varchar(1024),
    slug varchar(128) not null,
    status varchar(64) not null default 'RASCUNHO',
    constraint pk_curso primary key (id)
);