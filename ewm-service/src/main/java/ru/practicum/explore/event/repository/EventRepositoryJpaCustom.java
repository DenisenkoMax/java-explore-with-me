package ru.practicum.explore.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore.category.model.Category;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.event.model.State;
import ru.practicum.explore.request.model.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepositoryJpaCustom extends JpaRepository<Event, Long> {
    @Query(
            "SELECT e FROM Event e WHERE (LOWER(e.annotation) LIKE LOWER(CONCAT('%', ?1, '%')) " +
                    "OR LOWER(e.description) LIKE LOWER(CONCAT('%', ?1, '%'))) AND e.paid IN ?2 " +
                    "AND e.eventDate > ?3 AND e.eventDate < ?4 AND e.category.id IN ?5" +
                    " AND e.participantLimit < " +
                    "(SELECT COUNT (r) FROM Request r WHERE (r.event.id = e.id) AND (r.status =?6)) AND e.state = ?7"
    )
    List<Event> getEventsAvailableInCat(String text, List<Boolean> listPaid, LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd, List<Integer> categories, Status status, State state);

    @Query(
            "SELECT e FROM Event e WHERE (LOWER(e.annotation) LIKE LOWER(CONCAT('%', ?1, '%')) " +
                    "OR LOWER(e.description) LIKE LOWER(CONCAT('%', ?1, '%'))) AND e.paid IN ?2 " +
                    "AND e.eventDate > ?3 AND e.eventDate < ?4 AND e.category.id IN ?5 AND e.state = ?6"
    )
    List<Event> getEventsInCat(String text, List<Boolean> listPaid, LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd, List<Integer> categories, State state);

    @Query(
            "SELECT e FROM Event e WHERE (LOWER(e.annotation) LIKE LOWER(CONCAT('%', ?1, '%')) " +
                    "OR LOWER(e.description) LIKE LOWER(CONCAT('%', ?1, '%'))) AND e.paid IN ?2 " +
                    "AND e.eventDate > ?3 AND e.eventDate < ?4 " +
                    " AND e.participantLimit < " +
                    "(SELECT COUNT (r) FROM Request r WHERE (r.event.id = e.id) AND (r.status =?5)) AND e.state = ?6"
    )
    List<Event> getEventsAvailable(String text, List<Boolean> listPaid, LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd, Status status, State state);

    @Query(
            "SELECT e FROM Event e WHERE (LOWER(e.annotation) LIKE LOWER(CONCAT('%', ?1, '%')) " +
                    "OR LOWER(e.description) LIKE LOWER(CONCAT('%', ?1, '%'))) AND e.paid IN ?2 " +
                    "AND e.eventDate > ?3 AND e.eventDate < ?4 AND e.state = ?5"
    )
    List<Event> getEvents(String text, List<Boolean> listPaid, LocalDateTime rangeStart,
                                   LocalDateTime rangeEnd, State state);


}