package es.neil.api.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "La fecha es obligatoria")
    @Future(message = "La fecha debe ser en el futuro") // Ideal para eventos
    @Column(nullable = false)
    private LocalDate date;

    @NotBlank(message = "La ubicación no puede estar vacía")
    @Column(nullable = false)
    private String location;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "event_speaker",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "speaker_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Speaker> speakers = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToMany(mappedBy = "attendedEvents", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<User> attendedUsers = new HashSet<>();

    public void addSpeker(Speaker speaker) {
        this.speakers.add(speaker);
        speaker.getEvents().add(this);
    }

    public void removeSpeaker(Speaker speaker) {
        this.speakers.remove(speaker);
        speaker.getEvents().remove(this);
    }
}
