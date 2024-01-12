package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repository.SimplePriceHistoryRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePriceHistoryService implements PriceHistoryService {
    private final SimplePriceHistoryRepository simplePriceHistoryRepository;

    @Override
    public PriceHistory save(PriceHistory priceHistory) {
        return simplePriceHistoryRepository.save(priceHistory);
    }

    @Override
    public boolean update(PriceHistory priceHistory) {
        return simplePriceHistoryRepository.update(priceHistory);
    }

    @Override
    public boolean deleteById(int id) {
        return simplePriceHistoryRepository.deleteById(id);
    }

    @Override
    public Optional<PriceHistory> findById(int id) {
        return simplePriceHistoryRepository.findById(id);
    }

    @Override
    public Collection<PriceHistory> findAll() {
        return simplePriceHistoryRepository.findAll();
    }
}
