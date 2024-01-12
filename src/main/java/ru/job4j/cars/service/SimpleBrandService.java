package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.repository.BrandRepository;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleBrandService implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public Optional<Brand> findById(int id) {
        return brandRepository.findById(id);
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }
}
