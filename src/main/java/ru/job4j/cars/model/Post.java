package ru.job4j.cars.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "auto_posts")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String description;
    @CreationTimestamp
    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "auto_user_id")
    private User user;
    @OneToMany
    @JoinColumn(name = "auto_post_id")
    private List<PriceHistory> priceHistoryList = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "participates",
            joinColumns = {@JoinColumn(name = "auto_post_id")},
            inverseJoinColumns = {@JoinColumn(name = "auto_user_id")}
    )
    private Set<User> participates = new LinkedHashSet<>();
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
    private int price;
    private String phone;
    private boolean status;
}
