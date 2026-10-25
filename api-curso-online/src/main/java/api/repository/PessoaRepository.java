package api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
 
    Optional<Pessoa> findByCpf(String cpf);
}
