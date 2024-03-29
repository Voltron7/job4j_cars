package ru.job4j.cars.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "engines")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String name;
}
