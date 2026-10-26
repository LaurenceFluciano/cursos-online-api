package api.repository;

import api.model.Modulo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface ModuloRepository extends CrudRepository<Modulo, Long>{

    }

