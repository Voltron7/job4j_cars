package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;
import ru.job4j.cars.model.*;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;

class UserRepositoryTest {
    private static StandardServiceRegistry registry;
    private static UserRepository userRepository;

    @BeforeAll
    public static void init() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        userRepository = new UserRepository(new CrudRepository(sessionFactory));
    }

    @AfterAll
    public static void closeConnection() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @AfterEach
    public void deleteUsers() {
        List<User> users = userRepository.findAllOrderById();
        for (User user : users) {
            userRepository.delete(user.getId());
        }
    }

    @Test
    public void whenAddNewUserThenInUserRepositoryTheSameUser() {
        var user = getUser();
        userRepository.create(user);
        var result = userRepository.findById(user.getId()).get();
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void whenDeleteNewUserThenEmptyOptional() {
        var user = getUser();
        userRepository.create(user);
        int id = user.getId();
        userRepository.delete(id);
        var result = userRepository.findById(id);
        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void whenAddNewUserThenUpdateItAndFindItsName() {
        var user = getUser();
        userRepository.create(user);
        user.setLogin("newUser");
        user.setPassword("newPassword");
        userRepository.update(user);
        var updatedUser = userRepository.findById(user.getId()).get();
        assertThat(updatedUser.getLogin()).isEqualTo("newUser");
        assertThat(updatedUser.getPassword()).isEqualTo("newPassword");
    }

    private User getUser() {
        User user = new User();
        user.setLogin("User");
        user.setPassword("password");
        return user;
    }
}