package ru.practicum.explore.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.request.model.Request;

import java.util.List;

@Repository
public interface RequestRepositoryJpa extends JpaRepository<Request, Long>, RequestRepositoryJpaCustom {
    List<Request> findAllByEventId(Long eventId);

}
