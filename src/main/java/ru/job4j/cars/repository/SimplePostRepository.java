package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimplePostRepository implements PostRepository {
    private final CrudRepository crudRepository;

    @Override
    public Post save(Post post) {
        crudRepository.run(session -> session.persist(post));
        return post;
    }

    @Override
    public boolean deleteById(int id) {
        crudRepository.run(
                "delete from Post where id = :fId",
                Map.of("fId", id)
        );
        return true;
    }

    @Override
    public boolean update(Post post) {
        crudRepository.run(session -> session.merge(post));
        return true;
    }

    @Override
    public Optional<Post> findById(int id) {
        return crudRepository.optional(
                "from Post where id = :fId", Post.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<Post> findAll() {
        return crudRepository.query("from Post order by id asc", Post.class);
    }

    public List<Post> findAllForTheLastDay() {
        return crudRepository.query(
                "from Post where created >= :fDate", Post.class,
                Map.of("fDate", LocalDateTime.now().minusDays(1))
        );
    }

    public List<Post> findAllWithPhotos() {
        return crudRepository.query(
                "from Post where car.photos.size > 0", Post.class
        );
    }

    public List<Post> findByCarModel(String model) {
        return crudRepository.query(
                "from Post where car.model = :fModel", Post.class,
                Map.of("fModel", model)
        );
    }

    @Override
    public boolean setStatus(Post post) {
        crudRepository.run("update Post set status = :fStatus where id = :fId",
                Map.of("fStatus", true, "fId", post.getId())
        );
        return true;
    }
}
