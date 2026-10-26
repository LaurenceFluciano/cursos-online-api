package api.service;

public class ModuloNaoEncontradoException extends RecursoNaoEncontradoException {

    public ModuloNaoEncontradoException(Long id) {
        super("Módulo não encontrado: " + id);
    }
}
