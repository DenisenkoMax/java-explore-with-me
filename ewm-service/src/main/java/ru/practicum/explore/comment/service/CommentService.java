package ru.practicum.explore.comment.service;

import ru.practicum.explore.comment.dto.CommentDto;
import ru.practicum.explore.comment.dto.NewCommentDto;
import ru.practicum.explore.comment.dto.ShortCommentDto;
import ru.practicum.explore.comment.dto.UpdateCommentDto;

import java.util.List;

public interface CommentService {
    CommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto);

    CommentDto updateComment(Long userId, UpdateCommentDto updateCommentDto);
    CommentDto updateCommentAdmin(UpdateCommentDto updateCommentDto);

    List<CommentDto> getAllCommentsPrivate(Long userId, Boolean onlyPublished, int from, int size);

    CommentDto getCommentByIdPrivate(Long userId, Long commentId);
    CommentDto publishCommentUserPrivate(Long userId, Long commentId);
    void deleteCommentByIdPrivate(Long userId, Long commentId);
    List<ShortCommentDto> getAllCommentsByEventPub(Long eventId, int from, int size);
    ShortCommentDto getCommentByIdPublic(Long commentId);
    List<CommentDto>  getAllCommentsAdmin(Long ownerId, Long commenterId, Boolean onlyPublished, int from, int size);

}
