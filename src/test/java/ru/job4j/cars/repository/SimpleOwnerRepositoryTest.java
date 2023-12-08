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

class SimpleOwnerRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SimpleOwnerRepository simpleOwnerRepository;
    private static UserRepository userRepository;

    @BeforeAll
    public static void init() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        simpleOwnerRepository = new SimpleOwnerRepository(new CrudRepository(sessionFactory));
        userRepository = new UserRepository(new CrudRepository(sessionFactory));
    }

    @AfterAll
    public static void closeConnection() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @AfterEach
    public void delete() {
        List<Owner> owners = simpleOwnerRepository.findAll();
        for (Owner owner : owners) {
            simpleOwnerRepository.deleteById(owner.getId());
        }
        List<User> users = userRepository.findAllOrderById();
        for (User user : users) {
            userRepository.delete(user.getId());
        }
    }

    @Test
    public void whenAddNewOwnerThenInOwnerRepositoryTheSameOwner() {
        var owner = getOwner();
        simpleOwnerRepository.save(owner);
        var result = simpleOwnerRepository.findById(owner.getId()).get();
        assertThat(result).isEqualTo(owner);
    }

    @Test
    public void whenDeleteNewOwnerThenEmptyOptional() {
        var owner = getOwner();
        simpleOwnerRepository.save(owner);
        int id = owner.getId();
        simpleOwnerRepository.deleteById(id);
        var result = simpleOwnerRepository.findById(id);
        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void whenAddNewOwnerThenUpdateItAndFindItsName() {
        var owner = getOwner();
        simpleOwnerRepository.save(owner);
        owner.setName("newOwner");
        simpleOwnerRepository.update(owner);
        var updatedOwner = simpleOwnerRepository.findById(owner.getId()).get();
        assertThat(updatedOwner.getName()).isEqualTo("newOwner");
    }

    private Owner getOwner() {
        Owner owner = new Owner();
        owner.setName("Owner");
        owner.setUser(getUser());
        return owner;
    }

    private User getUser() {
        User user = new User();
        user.setLogin("User");
        user.setPassword("password");
        userRepository.create(user);
        return user;
    }
}