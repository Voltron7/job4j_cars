package ru.job4j.cars.service;

import ru.job4j.cars.model.Post;
import java.util.List;
import java.util.Optional;

public interface PostService {

    Post save(Post post);

    boolean deleteById(int id);

    boolean update(Post post);

    Optional<Post> findById(int id);

    List<Post> findAll();

    List<Post> findAllForTheLastDay();

    List<Post> findAllWithPhotos();

    List<Post> findByCarModel(String model);

    boolean setStatus(Post post);
}
