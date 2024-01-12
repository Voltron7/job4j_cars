package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.EngineRepository;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleEngineService implements EngineService {
    private final EngineRepository engineRepository;

    @Override
    public Engine save(Engine engine) {
        return engineRepository.save(engine);
    }

    @Override
    public boolean deleteById(int id) {
        return engineRepository.deleteById(id);
    }

    @Override
    public boolean update(Engine engine) {
        return engineRepository.update(engine);
    }

    @Override
    public Optional<Engine> findById(int id) {
        return engineRepository.findById(id);
    }

    @Override
    public List<Engine> findAll() {
        return engineRepository.findAll();
    }
}
