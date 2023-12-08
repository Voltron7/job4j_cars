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

class SimplePhotoRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SimplePhotoRepository simplePhotoRepository;

    @BeforeAll
    public static void init() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        simplePhotoRepository = new SimplePhotoRepository(new CrudRepository(sessionFactory));
    }

    @AfterAll
    public static void closeConnection() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @AfterEach
    public void deletePhotos() {
        List<Photo> photos = simplePhotoRepository.findAll();
        for (Photo photo : photos) {
            simplePhotoRepository.deleteById(photo.getId());
        }
    }

    @Test
    public void whenAddNewPhotoThenInPhotoRepositoryTheSamePhoto() {
        var photo = getPhoto();
        simplePhotoRepository.save(photo);
        var result = simplePhotoRepository.findById(photo.getId()).get();
        assertThat(result).isEqualTo(photo);
    }

    @Test
    public void whenDeleteNewPhotoThenEmptyOptional() {
        var photo = getPhoto();
        simplePhotoRepository.save(photo);
        int id = photo.getId();
        simplePhotoRepository.deleteById(id);
        var result = simplePhotoRepository.findById(id);
        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void whenAddNewPhotoThenUpdateItAndFindItsPath() {
        var photo = getPhoto();
        simplePhotoRepository.save(photo);
        photo.setPath("newPath");
        simplePhotoRepository.update(photo);
        var updatedPhoto = simplePhotoRepository.findById(photo.getId()).get();
        assertThat(updatedPhoto.getPath()).isEqualTo("newPath");
    }

    private Photo getPhoto() {
        Photo photo = new Photo();
        photo.setPath("path");
        return photo;
    }
}