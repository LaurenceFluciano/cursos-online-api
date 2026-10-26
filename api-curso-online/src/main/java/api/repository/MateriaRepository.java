package api.repository;

import api.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {

    List<Materia> findAllByModulo_Id(Long moduloId);

    boolean existsByModulo_Id(Long moduloId);

    boolean existsByModulo_IdAndNome(Long moduloId, String nome);
}
