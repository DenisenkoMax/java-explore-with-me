package ru.practicum.explore.event.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.practicum.explore.category.model.Category;
import ru.practicum.explore.user.model.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annotation")
    private String annotation;

    @Column(name = "title")
    private String title;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "publishet_on")
    private LocalDateTime publishetOn;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Column(name = "created")
    private LocalDateTime createdOn;

    @Column(name = "description")
    private String description;

    @Column(name = "participant_limit")
    private int participantLimit;

    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    private State state;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "locationLon", column = @Column(name = "location_lon")),
            @AttributeOverride(name = "locationLat", column = @Column(name = "location_lat"))})
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id")
    @ToString.Exclude
    private User initiator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;

}
