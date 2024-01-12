package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Photo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimplePhotoRepository implements PhotoRepository {
    private final CrudRepository crudRepository;

    @Override
    public Photo save(Photo photo) {
        crudRepository.run(session -> session.persist(photo));
        return photo;
    }

    @Override
    public boolean deleteById(int id) {
        crudRepository.run(
                "delete from Photo where id = :fId",
                Map.of("fId", id)
        );
        return true;
    }

    @Override
    public boolean update(Photo photo) {
        crudRepository.run(session -> session.merge(photo));
        return true;
    }

    @Override
    public Optional<Photo> findById(int id) {
        return crudRepository.optional(
                "from Photo where id = :fId", Photo.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<Photo> findAll() {
        return crudRepository.query("from Photo order by id asc", Photo.class);
    }
}
