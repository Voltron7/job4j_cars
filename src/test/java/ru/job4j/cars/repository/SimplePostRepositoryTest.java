package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;
import ru.job4j.cars.model.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;

class SimplePostRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SimplePostRepository simplePostRepository;
    private static SimplePhotoRepository simplePhotoRepository;
    private static SimpleCarRepository simpleCarRepository;
    private static SimpleEngineRepository simpleEngineRepository;
    private static UserRepository userRepository;

    @BeforeAll
    public static void init() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        simplePostRepository = new SimplePostRepository(new CrudRepository(sessionFactory));
        simplePhotoRepository = new SimplePhotoRepository(new CrudRepository(sessionFactory));
        simpleCarRepository = new SimpleCarRepository(new CrudRepository(sessionFactory));
        simpleEngineRepository = new SimpleEngineRepository(new CrudRepository(sessionFactory));
        userRepository = new UserRepository(new CrudRepository(sessionFactory));
    }

    @AfterAll
    public static void closeConnection() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @AfterEach
    public void delete() {
        List<Post> posts = simplePostRepository.findAll();
        for (Post post : posts) {
            simplePostRepository.deleteById(post.getId());
        }
        List<User> users = userRepository.findAllOrderById();
        for (User user : users) {
            userRepository.delete(user.getId());
        }
    }

    @Test
    public void whenAddNewPostThenInPostRepositoryTheSamePost() {
        var post1 = getPost1();
        simplePostRepository.save(post1);
        var result = simplePostRepository.findById(post1.getId()).get();
        assertThat(result).isEqualTo(post1);
    }

    @Test
    public void whenDeleteNewPostThenEmptyOptional() {
        var post1 = getPost1();
        simplePostRepository.save(post1);
        int id = post1.getId();
        simplePostRepository.deleteById(id);
        var result = simplePostRepository.findById(id);
        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void whenAddNewPostThenUpdateItAndFindItsDescription() {
        var post1 = getPost1();
        simplePostRepository.save(post1);
        post1.setDescription("newDescription1");
        simplePostRepository.update(post1);
        var updatedPost = simplePostRepository.findById(post1.getId()).get();
        assertThat(updatedPost.getDescription()).isEqualTo("newDescription1");
    }

    @Test
    void whenFindPostsForTheLastDayThenGetList() {
        Post post1 = getPost1();
        Post post2 = getPost2();
        simplePostRepository.save(post1);
        simplePostRepository.save(post2);
        assertThat(simplePostRepository.findAllForTheLastDay()).isEqualTo(List.of(post1));
    }

    @Test
    void whenFindPostsOnlyWithPhotoThenGetList() {
        Post post1 = getPost1();
        post1.setCar(getCarWithPhoto());
        Post post2 = getPost2();
        post2.setCar(getCarWithoutPhoto());
        simplePostRepository.save(post1);
        simplePostRepository.save(post2);
        assertThat(simplePostRepository.findAllWithPhotos()).isEqualTo(List.of(post1));
    }

    @Test
    void whenFindPostsByCarNameThenGetList() {
        Post post1 = getPost1();
        post1.setCar(getCarWithPhoto());
        Post post2 = getPost2();
        post2.setCar(getCarWithoutPhoto());
        simplePostRepository.save(post1);
        simplePostRepository.save(post2);
        assertThat(simplePostRepository.findByCarName("Acura")).isEqualTo(List.of(post1));
    }

    private Post getPost1() {
        Post post = new Post();
        post.setDescription("Description1");
        post.setCreated(LocalDateTime.now());
        post.setUser(getUser1());
        return post;
    }

    private Post getPost2() {
        Post post = new Post();
        post.setDescription("Description2");
        post.setCreated(LocalDateTime.now().minusDays(7));
        post.setUser(getUser2());
        return post;
    }

    private User getUser1() {
        User user = new User();
        user.setLogin("User1");
        user.setPassword("password");
        userRepository.create(user);
        return user;
    }

    private User getUser2() {
        User user = new User();
        user.setLogin("User2");
        user.setPassword("password");
        userRepository.create(user);
        return user;
    }

    private Car getCarWithPhoto() {
        Car car = new Car();
        car.setPhotos(List.of(getPhoto()));
        car.setName("Acura");
        car.setEngine(getEngine());
        simpleCarRepository.save(car);
        return car;
    }

    private Car getCarWithoutPhoto() {
        Car car = new Car();
        car.setName("Honda");
        car.setEngine(getEngine());
        simpleCarRepository.save(car);
        return car;
    }

    private Engine getEngine() {
        Engine engine = new Engine();
        engine.setName("Engine");
        simpleEngineRepository.save(engine);
        return engine;
    }

    private Photo getPhoto() {
        Photo photo = new Photo();
        photo.setPath("/");
        simplePhotoRepository.save(photo);
        return photo;
    }
}