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
    private static SimpleUserRepository simpleUserRepository;

    @BeforeAll
    public static void init() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        simpleOwnerRepository = new SimpleOwnerRepository(new CrudRepository(sessionFactory));
        simpleUserRepository = new SimpleUserRepository(new CrudRepository(sessionFactory));
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
        List<User> users = simpleUserRepository.findAllOrderById();
        for (User user : users) {
            simpleUserRepository.delete(user.getId());
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
        owner.setOwnerName("newOwnerName");
        simpleOwnerRepository.update(owner);
        var updatedOwner = simpleOwnerRepository.findById(owner.getId()).get();
        assertThat(updatedOwner.getOwnerName()).isEqualTo("newOwnerName");
    }

    private Owner getOwner() {
        Owner owner = new Owner();
        owner.setOwnerName("OwnerName");
        owner.setUser(getUser());
        return owner;
    }

    private User getUser() {
        User user = new User();
        user.setLogin("User");
        user.setPassword("password");
        simpleUserRepository.create(user);
        return user;
    }
}