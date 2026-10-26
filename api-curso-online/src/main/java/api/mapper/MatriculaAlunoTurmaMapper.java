package api.mapper;

import org.springframework.stereotype.Component;

import api.dto.MatriculaAlunoTurmaResponseDTO;
import api.model.MatriculaAlunoTurma;

@Component
public class MatriculaAlunoTurmaMapper {

    public MatriculaAlunoTurmaResponseDTO toResponse(MatriculaAlunoTurma matricula) {
        return new MatriculaAlunoTurmaResponseDTO(
                matricula.getId(),
                matricula.getAluno().getId(),
                matricula.getTurma().getModulo().getCurso().getId(),
                matricula.getDataMatricula(),
                matricula.getStatus(),
                matricula.getTurma().getId()
        );
    }
}
