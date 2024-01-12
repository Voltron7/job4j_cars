package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleUserRepository implements UserRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<User> create(User user) {
        try {
            crudRepository.run(session -> session.persist(user));
            return Optional.of(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(User user) {
        crudRepository.run(session -> session.merge(user));
        return true;
    }

    @Override
    public boolean delete(int userId) {
        crudRepository.run(
                "delete from User where id = :fId",
                Map.of("fId", userId)
        );
        return true;
    }

    @Override
    public List<User> findAllOrderById() {
        return crudRepository.query("from User order by id asc", User.class);
    }

    @Override
    public Optional<User> findById(int userId) {
        return crudRepository.optional(
                "from User where id = :fId", User.class,
                Map.of("fId", userId)
        );
    }

    @Override
    public List<User> findByLikeLogin(String key) {
        return crudRepository.query(
                "from User where login like :fLogin", User.class,
                Map.of("fLogin", "%" + key + "%")
        );
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(
                "from User where login = :fLogin and password  = :fPassword", User.class,
                Map.of("fLogin", login, "fPassword", password)
        );
    }
}
