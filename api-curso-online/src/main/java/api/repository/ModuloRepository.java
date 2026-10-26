package api.repository;

import api.model.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Long> {

    List<Modulo> findAllByCurso_Id(Long cursoId);

    boolean existsByCurso_Id(Long cursoId);

    boolean existsByCurso_IdAndOrdem(Long cursoId, Integer ordem);

    boolean existsByCurso_IdAndOrdemAndIdNot(Long cursoId, Integer ordem, Long moduloId);

    @Query(value = "select exists (select 1 from turma where id_modulo = :moduloId)", nativeQuery = true)
    boolean existeTurmaAssociada(@Param("moduloId") Long moduloId);
}
