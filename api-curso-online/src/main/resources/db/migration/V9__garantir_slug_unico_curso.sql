alter table curso
    add constraint uq_curso_slug unique (slug);
