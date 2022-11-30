package ru.practicum.explore.comment.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.comment.dto.CommentDto;
import ru.practicum.explore.comment.dto.NewCommentDto;
import ru.practicum.explore.comment.dto.UpdateCommentDto;
import ru.practicum.explore.comment.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(
        value = "/users/{userId}/comments/",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class PrivateCommentController {
    private final CommentService commentService;

    @PostMapping("/{eventId}")
    public CommentDto addComment(@PathVariable Long userId,
                                 @PathVariable Long eventId,
                                 @Valid @RequestBody NewCommentDto newCommentDto) {
        log.info("Добавление комментария от текущего пользователя {} к событию  {}", userId, eventId);
        return commentService.addCommentPrivate(userId, eventId, newCommentDto);
    }

    @PutMapping
    public CommentDto updateComment(@PathVariable Long userId,
                                    @Valid @RequestBody UpdateCommentDto updateCommentDto) {
        log.info("Обновление комментария  с id {} ", updateCommentDto.getId());
        return commentService.updateCommentPrivate(userId, updateCommentDto);
    }

    @GetMapping
    public List<CommentDto> getAllCommentsByUser(@PathVariable Long userId,
                                                 @RequestParam(name = "onlyPublished", defaultValue = "false")
                                                 Boolean onlyPublished,
                                                 @PositiveOrZero @RequestParam(value = "from", defaultValue = "0")
                                                 Integer from,
                                                 @Positive @RequestParam(value = "size", defaultValue = "10")
                                                 Integer size) {
        log.info("Запрос на получение комментариев на все события пользователя {}", userId);
        return commentService.getAllCommentsPrivate(userId, onlyPublished, from, size);
    }

    @GetMapping("/{commentId}")
    public CommentDto getCommentById(@PathVariable Long userId,
                                     @PathVariable Long commentId) {
        log.info("Запрос на получение комментария {} на событие пользователя {}", userId);
        return commentService.getCommentByIdPrivate(userId, commentId);
    }

    @PatchMapping("/{commentId}/publish")
    public CommentDto publishEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Одобрение на модерацию комментария {} пользователем {}",eventId, userId);
        return commentService.publishCommentUserPrivate(userId, eventId);
    }

    @DeleteMapping("/{id}")
    public void deleteCommentById(@PathVariable Long userId,
                                  @PathVariable Long commentId) {
        log.info("Запрос на удаление комментария {} на событие пользователя {}",commentId, userId);
        commentService.deleteCommentByIdPrivate(userId, commentId);
    }
}
