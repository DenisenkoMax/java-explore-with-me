package ru.practicum.explore.comment.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.comment.dto.ShortCommentDto;
import ru.practicum.explore.comment.service.CommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(
        value = "/comments",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class PublicCommentController {
    private final CommentService commentService;

    //Получение любым пользователем коротких комментарием (только опубликованным) к нужному событию (только опубликованному)
    @GetMapping("/event/{eventId}")
    public List<ShortCommentDto> getAllCommentsByEvent(@PathVariable Long eventId,
                                                       @PositiveOrZero
                                                       @RequestParam(value = "from", defaultValue = "0")
                                                       Integer from,
                                                       @Positive
                                                       @RequestParam(value = "size", defaultValue = "10")
                                                       Integer size) {
        log.info("Запрос на получение опубликованных комментариев на событие {}", eventId);
        return commentService.getAllCommentsByEventPub(eventId, from, size);
    }

    //Получение любым пользователем комментарием с необходимым номером (только опубликованным)
    // к любым только к опубликованным событиям(только опубликованному)
    @GetMapping("/{commentId}")
    public ShortCommentDto getCommentById(@PathVariable Long commentId) {
        log.info("Запрос на получение комментария {}", commentId);
        return commentService.getCommentByIdPublic(commentId);
    }
}
