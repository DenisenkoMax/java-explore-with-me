package ru.practicum.explore.comment.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explore.comment.model.Comment;
import ru.practicum.explore.event.repository.EventRepositoryJpa;
import ru.practicum.explore.user.repository.UserRepositoryJpa;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final EventRepositoryJpa eventRepositoryJpa;
    private final UserRepositoryJpa userRepositoryJpa;

    public Comment toComment(Long userId, Long eventId, NewCommentDto newCommentDto) {
        if (newCommentDto == null) return null;
        else return new Comment(
                0L,
                LocalDateTime.now(),
                false,
                newCommentDto.getCommentText(),
                eventRepositoryJpa.findById(eventId).isEmpty() ? null : eventRepositoryJpa.findById(eventId).get(),
                userRepositoryJpa.findById(userId).isEmpty() ? null : userRepositoryJpa.findById(userId).get()
        );
    }

    public CommentDto toCommentDto(Comment comment) {
        if (comment == null) return null;
        else return new CommentDto(
                comment.getId(),
                comment.getCreated(),
                comment.getCommenter().getId(),
                comment.getEvent().getId(),
                comment.getCommentText(),
                comment.getPublished()
        );
    }

    public ShortCommentDto toShortCommentDto(Comment comment) {
        if (comment == null) return null;
        else return new ShortCommentDto(
                comment.getCommenter().getId(),
                comment.getEvent().getId(),
                comment.getCommentText()
        );
    }
}
