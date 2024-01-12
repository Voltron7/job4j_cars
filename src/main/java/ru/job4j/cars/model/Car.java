package ru.job4j.cars.model;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cars")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private String model;

    @ManyToOne
    @JoinColumn(name = "body_id")
    private Body body;

    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "history_owners",
            joinColumns = {@JoinColumn(name = "car_id")},
            inverseJoinColumns = {@JoinColumn(name = "owner_id")}
    )
    private List<Owner> owners = new ArrayList<>();

    @OneToMany(mappedBy = "car")
    private Set<History> history = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "car_id")
    private List<Photo> photos = new ArrayList<>();
}
