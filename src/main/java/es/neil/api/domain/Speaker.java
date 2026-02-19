package es.neil.api.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "speakers")
public class Speaker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;


    @ManyToMany(mappedBy = "speakers")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Event> events = new HashSet<>();

}
