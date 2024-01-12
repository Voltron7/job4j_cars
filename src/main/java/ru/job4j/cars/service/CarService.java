package ru.job4j.cars.service;

import ru.job4j.cars.model.Car;
import java.util.List;
import java.util.Optional;

public interface CarService {

    Car save(Car car);

    boolean deleteById(int id);

    boolean update(Car car);

    Optional<Car> findById(int id);

    List<Car> findAll();

    Car getCarWithPhoto(int id);
}
