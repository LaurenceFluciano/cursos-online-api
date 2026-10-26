package api.service;

public class TurmaNaoEncontradaException extends RecursoNaoEncontradoException {

    public TurmaNaoEncontradaException(Long id) {
        super("Turma não encontrada: " + id);
    }
}
