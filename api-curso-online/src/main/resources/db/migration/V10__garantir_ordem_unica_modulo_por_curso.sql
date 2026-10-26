alter table modulo
    add constraint uq_modulo_curso_ordem unique (id_curso, ordem);
