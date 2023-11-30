package ru.job4j.cars.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "owners")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
