package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleCarRepository implements CarRepository {
    private final CrudRepository crudRepository;

    @Override
    public Car save(Car car) {
        crudRepository.run(session -> session.persist(car));
        return car;
    }

    @Override
    public boolean deleteById(int id) {
        crudRepository.run(
                "delete from Car where id = :fId",
                Map.of("fId", id)
        );
        return true;
    }

    @Override
    public boolean update(Car car) {
        crudRepository.run(session -> session.merge(car));
        return true;
    }

    @Override
    public Optional<Car> findById(int id) {
        return crudRepository.optional(
                "from Car c LEFT JOIN FETCH c.owners LEFT JOIN FETCH c.photos where c.id = :fId", Car.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<Car> findAll() {
        return crudRepository.query("from Car order by id asc", Car.class);
    }

    @Override
    public Car getCarWithPhoto(int id) {
        return crudRepository.optional("SELECT c FROM Car c LEFT JOIN FETCH c.photos WHERE c.id = :fId", Car.class,
                Map.of("fId", id)
        ).get();
    }
}
