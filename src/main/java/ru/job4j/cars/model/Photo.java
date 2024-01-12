package ru.job4j.cars.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "photos")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String path;
}
