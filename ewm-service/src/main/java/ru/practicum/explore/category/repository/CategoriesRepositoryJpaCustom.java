package ru.practicum.explore.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore.category.model.Category;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.event.model.State;
import ru.practicum.explore.request.model.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface CategoriesRepositoryJpaCustom extends JpaRepository<Category, Long> {

    @Query("SELECT c.id FROM Category  c")
    List<Event> findAllId();
}