package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepository;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePostService implements PostService {
    private final PostRepository postRepository;

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public boolean deleteById(int id) {
        return postRepository.deleteById(id);
    }

    @Override
    public boolean update(Post post) {
        return postRepository.update(post);
    }

    @Override
    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> findAllForTheLastDay() {
        return postRepository.findAllForTheLastDay();
    }

    @Override
    public List<Post> findAllWithPhotos() {
        return postRepository.findAllWithPhotos();
    }

    @Override
    public List<Post> findByCarModel(String model) {
        return postRepository.findByCarModel(model);
    }

    @Override
    public boolean setStatus(Post post) {
        return postRepository.setStatus(post);
    }
}
