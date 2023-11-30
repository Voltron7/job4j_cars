package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleOwnerRepository implements OwnerRepository {
    private final CrudRepository crudRepository;

    @Override
    public Owner save(Owner owner) {
        crudRepository.run(session -> session.persist(owner));
        return owner;
    }

    @Override
    public boolean deleteById(int id) {
        crudRepository.run(
                "delete from Owner where id = :fId",
                Map.of("fId", id)
        );
        return true;
    }

    @Override
    public boolean update(Owner owner) {
        crudRepository.run(session -> session.merge(owner));
        return true;
    }

    @Override
    public Optional<Owner> findById(int id) {
        return crudRepository.optional(
                "from Owner where id = :fId", Owner.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<Owner> findAll() {
        return crudRepository.query("from Owner order by id asc", Owner.class);
    }
}
