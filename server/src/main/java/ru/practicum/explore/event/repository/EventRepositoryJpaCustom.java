package ru.practicum.explore.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore.event.dto.EventDtoPublicAnswer;
import ru.practicum.explore.event.model.Event;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface EventRepositoryJpaCustom extends JpaRepository<Event, Long> {


    @Query(
            "SELECT e FROM Event e "
                    + "WHERE UPPER(e.annotation) LIKE UPPER(CONCAT('%', ?1, '%')) "
                    + "OR UPPER(e.description) LIKE UPPER(CONCAT('%', ?1, '%'))"
                    + "AND e.category IN ?2"
                    + "AND e.eventDate > ?3"
                    + "AND e.eventDate < ?4"
    )

    Page<List<EventDtoPublicAnswer>>  getAllPublicEventsWithText(String text, List categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                                 Boolean onlyAvailable, Pageable pageable);

}
