package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.PriceHistory;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimplePriceHistoryRepository implements PriceHistoryRepository {
    private final CrudRepository crudRepository;

    @Override
    public PriceHistory save(PriceHistory priceHistory) {
        crudRepository.run(session -> session.persist(priceHistory));
        return priceHistory;
    }

    @Override
    public boolean deleteById(int id) {
        crudRepository.run(
                "delete from PriceHistory where id = :fId",
                Map.of("fId", id)
        );
        return true;
    }

    @Override
    public boolean update(PriceHistory priceHistory) {
        crudRepository.run(session -> session.merge(priceHistory));
        return true;
    }

    @Override
    public Optional<PriceHistory> findById(int id) {
        return crudRepository.optional(
                "from PriceHistory where id = :fId", PriceHistory.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<PriceHistory> findAll() {
        return crudRepository.query("from PriceHistory order by id asc", PriceHistory.class);
    }
}
