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

class SimpleCarRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SimpleCarRepository simpleCarRepository;
    private static SimpleEngineRepository simpleEngineRepository;

    @BeforeAll
    public static void init() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        simpleCarRepository = new SimpleCarRepository(new CrudRepository(sessionFactory));
        simpleEngineRepository = new SimpleEngineRepository(new CrudRepository(sessionFactory));
    }

    @AfterAll
    public static void closeConnection() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    public void whenAddNewCarThenInCarRepositoryTheSameCar() {
        var car = getCar();
        var result = simpleCarRepository.findById(car.getId()).get();
        assertThat(result).isEqualTo(car);
    }

    @Test
    public void whenDeleteNewCarThenEmptyOptional() {
        var car = getCar();
        int id = car.getId();
        simpleCarRepository.deleteById(id);
        var result = simpleCarRepository.findById(id);
        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void whenAddNewCarThenUpdateItAndFindItsName() {
        var car = getCar();
        car.setModel("newModel");
        simpleCarRepository.update(car);
        var updatedCar = simpleCarRepository.findById(car.getId()).get();
        assertThat(updatedCar.getModel()).isEqualTo("newModel");
    }

    private Engine getEngine() {
        Engine engine = new Engine();
        engine.setName("Engine");
        simpleEngineRepository.save(engine);
        return engine;
    }

    private Car getCar() {
        Car car = new Car();
        car.setModel("Model");
        car.setEngine(getEngine());
        simpleCarRepository.save(car);
        return car;
    }
}