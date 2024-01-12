package ru.job4j.cars.service;

import ru.job4j.cars.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> create(User user);

    boolean update(User user);

    boolean delete(int userId);

    List<User> findAllOrderById();

    Optional<User> findById(int userId);

    List<User> findByLikeLogin(String key);

    Optional<User> findByLoginAndPassword(String login, String password);
}
