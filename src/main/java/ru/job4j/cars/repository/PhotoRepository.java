package ru.job4j.cars.repository;

import ru.job4j.cars.model.Photo;
import java.util.List;
import java.util.Optional;

public interface PhotoRepository {

    Photo save(Photo photo);

    boolean deleteById(int id);

    boolean update(Photo photo);

    Optional<Photo> findById(int id);

    List<Photo> findAll();
}
