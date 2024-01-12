package ru.job4j.cars.service;

import ru.job4j.cars.model.Brand;
import java.util.List;
import java.util.Optional;

public interface BrandService {

    Optional<Brand> findById(int id);

    List<Brand> findAll();
}
