package ru.job4j.cars.repository;

import ru.job4j.cars.model.PriceHistory;
import java.util.Collection;
import java.util.Optional;

public interface PriceHistoryRepository {

    PriceHistory save(PriceHistory priceHistory);

    boolean update(PriceHistory priceHistory);

    boolean deleteById(int id);

    Optional<PriceHistory> findById(int id);

    Collection<PriceHistory> findAll();
}
