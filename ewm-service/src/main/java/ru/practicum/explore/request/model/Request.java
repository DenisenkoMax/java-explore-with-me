package ru.practicum.explore.request.model;

import lombok.*;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.user.model.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "requests")
public class Request implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @ToString.Exclude
    private Event event;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    @ToString.Exclude
    private Event requester;

    @Column(name = "created")
    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}

