package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class SimplePostRepository implements PostRepository {
    private final CrudRepository crudRepository;

    public List<Post> findAllForTheLastDay() {
        return crudRepository.query(
                "from Post where created = :fDate", Post.class,
                Map.of(":fDate", LocalDateTime.now().minusDays(1))
        );
    }

    public List<Post> findAllWithPhotos() {
        return crudRepository.query(
                "from Post where car.photos.size > 0", Post.class
        );
    }

    public List<Post> findByCarName(String name) {
        return crudRepository.query(
                "from Post where car.name = :fName", Post.class,
                Map.of("fName", name)
        );
    }
}
