package ru.job4j.cars.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auto_posts")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String description;
    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "auto_user_id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "auto_post_id")
    private List<PriceHistory> priceHistoryList = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "participates",
            joinColumns = { @JoinColumn(name = "auto_post_id") },
            inverseJoinColumns = { @JoinColumn(name = "auto_user_id") }
    )
    private List<User> participates = new ArrayList<>();
}
