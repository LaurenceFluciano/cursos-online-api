alter table materia
    add constraint uq_materia_modulo_nome unique (id_modulo, nome);
