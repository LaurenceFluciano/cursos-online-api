package api.service;

public class MateriaNaoEncontradaException extends RecursoNaoEncontradoException {

    public MateriaNaoEncontradaException(Long id) {
        super("Matéria não encontrada: " + id);
    }
}
