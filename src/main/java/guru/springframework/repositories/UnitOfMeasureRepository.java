package guru.springframework.repositories;

import guru.springframework.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    // Custom query methods can be defined here if needed
    // For example:
    // List<UnitOfMeasure> findByDescription(String description);
}
