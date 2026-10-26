package api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import api.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    @EntityGraph(attributePaths = {"pessoa"})
    Page<Aluno> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"pessoa"})
    Optional<Aluno> findById(Long id);

    @Query("""
        SELECT COUNT(a) > 0 FROM Aluno a
        WHERE a.pessoa.id = :idPessoa
        """)
    boolean existsByPessoaId(@Param("idPessoa") Long idPessoa);
}
