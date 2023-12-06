package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.History;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleHistoryRepository implements HistoryRepository {
    private final CrudRepository crudRepository;

    @Override
    public History save(History history) {
        crudRepository.run(session -> session.persist(history));
        return history;
    }

    @Override
    public boolean deleteById(int id) {
        crudRepository.run(
                "delete from History where id = :fId",
                Map.of("fId", id)
        );
        return true;
    }

    @Override
    public boolean update(History history) {
        crudRepository.run(session -> session.merge(history));
        return true;
    }

    @Override
    public Optional<History> findById(int id) {
        return crudRepository.optional(
                "from History where id = :fId", History.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<History> findAll() {
        return crudRepository.query("from History order by id asc", History.class);
    }
}
