package ru.practicum.explore.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.event.model.State;
import ru.practicum.explore.request.model.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepositoryJpaCustom extends JpaRepository<Event, Long> {
    @Query(
            "SELECT e FROM Event e "
                    + "WHERE (LOWER(e.annotation) LIKE LOWER(CONCAT('%', ?1, '%')) "
                    + "OR LOWER(e.description) LIKE LOWER(CONCAT('%', ?1, '%')))"
                    + "AND e.category IN ?2"
                    + "AND e.paid = ?3"
                    + "AND e.eventDate > ?4"
                    + "AND e.eventDate < ?5"
                    + "AND e.participantLimit <  "
                    + "(SELECT COUNT (r) FROM Request r WHERE (r.event.id = e.id) AND (r.status =(?6)))"
                    + "AND e.state = ?7"
    )
    Page<Event> getEvents(String text, List categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                          Status status, State state, Pageable pageable);


}