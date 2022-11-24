package ru.practicum.explore.compilation.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explore.event.model.Event;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@Entity
@Table(name = "compilations")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Compilation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "events_compilations", joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> eventsCompilations;


    @Column(name = "pinned")
    private boolean pinned;

    @Column(name = "title")
    private String title;
}
