package api.repository;

import api.model.EnumStatusMatricula;
import api.model.EnumTurnoTurma;
import api.model.MatriculaAlunoTurma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface MatriculaAlunoTurmaRepository extends JpaRepository<MatriculaAlunoTurma, Long> {

    boolean existsByAlunoIdAndTurmaId(Long idAluno, Long idTurma);

    boolean existsByAlunoIdAndStatusIn(Long idAluno, Collection<EnumStatusMatricula> status);

    boolean existsByAlunoIdAndStatusInAndTurmaTurnoAndTurmaPeriodoLetivo(
            Long idAluno,
            Collection<EnumStatusMatricula> status,
            EnumTurnoTurma turno,
            String periodoLetivo
    );

    long countByTurmaIdAndStatusIn(Long idTurma, Collection<EnumStatusMatricula> status);
}