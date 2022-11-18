package ru.practicum.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.stats.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepositoryJpaCustom extends JpaRepository<EndpointHit, Long> {
    @Query("SELECT  DISTINCT COUNT (distinct e.ip), e.uri, e.app FROM EndpointHit e " +
            "GROUP BY e.uri, e.app, e.timestamp HAVING e.timestamp >= ?1 AND e.timestamp <= ?2 AND e.uri IN ?3")
    List<List<Object>> findAllByUrisAndUnique(LocalDateTime start, LocalDateTime end, String[] uris);

    @Query("SELECT DISTINCT COUNT (e.ip), e.uri, e.app FROM EndpointHit e " +
            "GROUP BY e.uri, e.app, e.timestamp HAVING e.timestamp >= ?1 AND e.timestamp <= ?2 AND e.uri IN ?3")
    List<List<Object>> findAllByUris(LocalDateTime start, LocalDateTime end, String[] uris);

    @Query("SELECT  DISTINCT COUNT (distinct e.ip), e.uri, e.app FROM EndpointHit e " +
            "GROUP BY e.uri, e.app, e.timestamp HAVING e.timestamp >= ?1 AND e.timestamp <= ?2")
    List<List<Object>> findAllByUnique(LocalDateTime start, LocalDateTime end);

    @Query("SELECT DISTINCT count (e.ip), e.uri, e.app FROM EndpointHit e " +
            "GROUP BY e.uri, e.app, e.timestamp HAVING e.timestamp >= ?1 AND e.timestamp <= ?2")
    List<List<Object>> findAllByDate(LocalDateTime start, LocalDateTime end);
}