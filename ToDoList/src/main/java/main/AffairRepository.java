package main;

import main.model.Affair;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffairRepository extends CrudRepository<Affair, Integer> {
}
