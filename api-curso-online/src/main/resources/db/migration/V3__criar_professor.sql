create table professor (
    id bigint generated always as identity,
    id_pessoa bigint not null,

    constraint pk_professor primary key (id),
    constraint fk_professor_pessoa foreign key (id_pessoa) references pessoa(id)
);