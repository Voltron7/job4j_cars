package ru.job4j.cars.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "auto_users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String login;
    private String password;
}
