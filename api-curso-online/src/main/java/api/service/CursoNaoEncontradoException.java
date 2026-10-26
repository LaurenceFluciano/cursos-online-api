package api.service;

public class CursoNaoEncontradoException extends RecursoNaoEncontradoException {

    public CursoNaoEncontradoException(Long id) {
        super("Curso não encontrado: " + id);
    }

    public CursoNaoEncontradoException(String slug) {
        super("Curso não encontrado: " + slug);
    }
}
