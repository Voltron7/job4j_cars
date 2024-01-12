package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.User;

public class UserUsage {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory()) {
            var simpleUserRepository = new SimpleUserRepository(new CrudRepository(sf));
            var user = new User();
            user.setLogin("admin");
            user.setPassword("admin");
            simpleUserRepository.create(user);
            simpleUserRepository.findAllOrderById()
                    .forEach(System.out::println);
            simpleUserRepository.findByLikeLogin("e")
                    .forEach(System.out::println);
            simpleUserRepository.findById(user.getId())
                    .ifPresent(System.out::println);
            simpleUserRepository.findByLoginAndPassword("admin", "admin")
                    .ifPresent(System.out::println);
            user.setPassword("password");
            simpleUserRepository.update(user);
            simpleUserRepository.findById(user.getId())
                    .ifPresent(System.out::println);
            simpleUserRepository.delete(user.getId());
            simpleUserRepository.findAllOrderById()
                    .forEach(System.out::println);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
