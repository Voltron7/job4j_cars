package ru.job4j.cars.service;

import ru.job4j.cars.model.Body;
import java.util.List;
import java.util.Optional;

public interface BodyService {

    Optional<Body> findById(int id);

    List<Body> findAll();
}
