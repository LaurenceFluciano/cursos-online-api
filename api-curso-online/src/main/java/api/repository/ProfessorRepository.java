package api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import api.model.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    @EntityGraph(attributePaths = {"pessoa"})
    Page<Professor> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"pessoa"})
    Optional<Professor> findById(Long id);
}
