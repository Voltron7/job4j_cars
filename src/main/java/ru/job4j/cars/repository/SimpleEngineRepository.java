package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleEngineRepository implements EngineRepository {
    private final CrudRepository crudRepository;

    @Override
    public Engine save(Engine engine) {
        crudRepository.run(session -> session.persist(engine));
        return engine;
    }

    @Override
    public boolean deleteById(int id) {
        crudRepository.run(
                "delete from Engine where id = :fId",
                Map.of("fId", id)
        );
        return true;
    }

    @Override
    public boolean update(Engine engine) {
        crudRepository.run(session -> session.merge(engine));
        return true;
    }

    @Override
    public Optional<Engine> findById(int id) {
        return crudRepository.optional(
                "from Engine where id = :fId", Engine.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<Engine> findAll() {
        return crudRepository.query("from Engine order by id asc", Engine.class);
    }
}
