package api.mapper;

import api.dto.TurmaResponseDTO;
import api.model.Turma;
import org.springframework.stereotype.Component;

@Component
public class TurmaMapper {

    public TurmaResponseDTO toResponse(Turma turma) {
        return new TurmaResponseDTO(
                turma.getId(),
                turma.getModulo().getId(),
                turma.getProfessor().getId(),
                turma.getPeriodoLetivo(),
                turma.getTipoPeriodo(),
                turma.getTurno(),
                turma.getVagas(),
                turma.getDataInicio(),
                turma.getDataFim()
        );
    }
}