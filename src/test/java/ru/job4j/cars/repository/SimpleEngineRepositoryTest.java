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

class SimpleEngineRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SimpleEngineRepository simpleEngineRepository;

    @BeforeAll
    public static void init() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        simpleEngineRepository = new SimpleEngineRepository(new CrudRepository(sessionFactory));
    }

    @AfterAll
    public static void closeConnection() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    public void whenAddNewEngineThenInEngineRepositoryTheSameEngine() {
        var engine = getEngine();
        simpleEngineRepository.save(engine);
        var result = simpleEngineRepository.findById(engine.getId()).get();
        assertThat(result).isEqualTo(engine);
    }

    @Test
    public void whenDeleteNewEngineThenEmptyOptional() {
        var engine = getEngine();
        simpleEngineRepository.save(engine);
        int id = engine.getId();
        simpleEngineRepository.deleteById(id);
        var result = simpleEngineRepository.findById(id);
        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void whenAddNewEngineThenUpdateItAndFindItsName() {
        var engine = getEngine();
        simpleEngineRepository.save(engine);
        engine.setName("newEngine");
        simpleEngineRepository.update(engine);
        var updatedEngine = simpleEngineRepository.findById(engine.getId()).get();
        assertThat(updatedEngine.getName()).isEqualTo("newEngine");
    }

    private Engine getEngine() {
        Engine engine = new Engine();
        engine.setName("Engine");
        return engine;
    }
}