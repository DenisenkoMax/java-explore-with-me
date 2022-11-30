package ru.practicum.explore.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.comment.model.Comment;

@Repository
public interface CommentRepositoryJpa extends JpaRepository<Comment, Long>, CommentRepositoryJpaCustom {
}
