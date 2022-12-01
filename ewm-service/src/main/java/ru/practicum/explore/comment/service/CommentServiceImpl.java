package ru.practicum.explore.comment.service;

import ru.practicum.explore.comment.dto.*;
import ru.practicum.explore.comment.model.Comment;
import ru.practicum.explore.comment.repository.CommentRepositoryJpa;
import ru.practicum.explore.event.model.State;
import ru.practicum.explore.event.repository.EventRepositoryJpa;
import ru.practicum.explore.request.repository.RequestRepositoryJpa;
import ru.practicum.explore.user.repository.UserRepositoryJpa;
import ru.practicum.explore.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final RequestRepositoryJpa requestRepositoryJpa;
    private final EventRepositoryJpa eventRepositoryJpa;
    private final UserRepositoryJpa userRepositoryJpa;
    private final CommentRepositoryJpa commentRepositoryJpa;
    private final CommentMapper commentMapper;
    private final Validation validation;

    public CommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto) {
        validation.validateUser(userId);
        validation.validateEvent(eventId);
        if (eventRepositoryJpa.findById(eventId).get().getCommentAvailable() == false)
            throw new IllegalArgumentException("Комментирование события с id " + eventId + "запрещено");
        Comment comment = commentMapper.toComment(userId, eventId, newCommentDto);
        if (eventRepositoryJpa.findById(eventId).get().getCommentModeration() == true) {
            comment.setPublished(false);
        } else {
            comment.setPublished(true);
        }
        commentRepositoryJpa.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    public CommentDto updateComment(Long userId, UpdateCommentDto updateCommentDto) {
        validation.validateUser(userId);
        validation.validateComment(updateCommentDto.getId());
        validation.validateCommentOwner(userId, updateCommentDto.getId());
        Comment comment = commentRepositoryJpa.findById(updateCommentDto.getId()).get();
        if (updateCommentDto.getCommentText() != null) comment.setCommentText(updateCommentDto.getCommentText());
        if (eventRepositoryJpa.findById(comment.getEvent().getId()).get().getCommentModeration() == true) {
            comment.setPublished(false);
        }
        commentRepositoryJpa.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    public CommentDto updateCommentAdmin(UpdateCommentDto updateCommentDto) {
        validation.validateComment(updateCommentDto.getId());
        Comment comment = commentRepositoryJpa.findById(updateCommentDto.getId()).get();
        if (updateCommentDto.getCommentText() != null) comment.setCommentText(updateCommentDto.getCommentText());
        if (eventRepositoryJpa.findById(comment.getEvent().getId()).get().getCommentModeration() == true) {
            comment.setPublished(false);
        }
        commentRepositoryJpa.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    public List<CommentDto> getAllCommentsPrivate(Long ownerId, Boolean onlyPublished, int from, int size) {
        validation.validateUser(ownerId);
        List<Boolean> listPublished = onlyPublished ? List.of(true) : List.of(true, false);
        Pageable pageable = PageRequest.of(from / size, size);
        return commentRepositoryJpa.getAllCommentsPrivate(ownerId, listPublished, pageable).stream()
                .map(p -> commentMapper.toCommentDto(p))
                .collect(Collectors.toList());
    }

    public CommentDto getCommentByIdPrivate(Long ownerId, Long commentId) {
        validation.validateUser(ownerId);
        validation.validateCommentOwner(ownerId, commentId);
        return commentMapper.toCommentDto(commentRepositoryJpa.findById(commentId).get());
    }

    public CommentDto publishCommentUserPrivate(Long ownerId, Long commentId) {
        validation.validateUser(ownerId);
        validation.validateComment(commentId);
        validation.validateCommentOwner(ownerId, commentId);
        Comment comment = commentRepositoryJpa.findById(commentId).get();
        if (eventRepositoryJpa.findById(comment.getEvent().getId()).get().getCommentModeration())
            throw new IllegalArgumentException("Модерация комментариев к данному событию не требуется");
        if (comment.getPublished())
            throw new IllegalArgumentException("Событие " + commentId + " уже прошло модерацию и опубликовано");
        comment.setPublished(true);
        commentRepositoryJpa.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    public void deleteCommentByIdPrivate(Long ownerId, Long commentId) {
        validation.validateUser(ownerId);
        validation.validateCommentOwner(ownerId, commentId);
        commentRepositoryJpa.deleteById(commentId);
    }

    public List<ShortCommentDto> getAllCommentsByEventPub(Long eventId, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        validation.validateEvent(eventId);
        return commentRepositoryJpa.getAllCommentsPublic(eventId, State.PUBLISHED, pageable).stream()
                .map(p -> commentMapper.toShortCommentDto(p))
                .collect(Collectors.toList());
    }

    public ShortCommentDto getCommentByIdPublic(Long commentId) {
        validation.validateComment(commentId);
        return commentMapper.toShortCommentDto(commentRepositoryJpa.findById(commentId, State.PUBLISHED));
    }

    public List<CommentDto> getAllCommentsAdmin(Long ownerId, Long commenterId, Boolean onlyPublished,
                                                int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<Comment> comments = null;
        if (ownerId != null && commenterId != null) comments = commentRepositoryJpa
                .getAllByOwnerAndCommenterOnlyPubAdmin(ownerId, commenterId, onlyPublished, pageable);
        if (ownerId != null && commenterId == null) comments = commentRepositoryJpa
                .getAllByOwnerOnlyPubAdmin(ownerId, onlyPublished, pageable);
        if (ownerId == null && commenterId != null) comments = commentRepositoryJpa
                .getAllByCommenterOnlyPubAdmin(commenterId, onlyPublished, pageable);
        if (ownerId == null && commenterId == null) comments = commentRepositoryJpa
                .getAllByOnlyPubAdmin(onlyPublished, pageable);
        return comments.stream()
                .map(p -> commentMapper.toCommentDto(p))
                .collect(Collectors.toList());
    }
}
