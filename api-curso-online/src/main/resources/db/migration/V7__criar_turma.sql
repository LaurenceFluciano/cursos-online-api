create table turma (
    id bigint generated always as identity,
    id_modulo bigint not null,
    id_professor bigint not null,
    periodo_letivo varchar(12) not null,
    tipo_periodo varchar(16) not null,
    turno varchar(16) not null,
    vagas int not null,
    data_inicio date,
    data_fim date,
     
    constraint pk_turma primary key (id),
    constraint fk_turma_modulo foreign key (id_modulo) references modulo(id),
    constraint fk_turma_professor foreign key (id_professor) references professor(id),
    constraint chk_vagas check (vagas > 0)
);