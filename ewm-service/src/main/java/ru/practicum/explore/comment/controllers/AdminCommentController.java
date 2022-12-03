package ru.practicum.explore.comment.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.comment.dto.CommentDto;
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
        value = "/admin/comments",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AdminCommentController {
    private final CommentService commentService;

    @GetMapping
    public List<CommentDto> getAllCommentsAdmin(@RequestParam(name = "ownerId", required = false) Long ownerId,
                                                @RequestParam(name = "commenterId", required = false) Long commenterId,
                                                @RequestParam(name = "onlyPublished", defaultValue = "false")
                                                Boolean onlyPublished,
                                                @PositiveOrZero
                                                @RequestParam(value = "from", defaultValue = "0")
                                                Integer from,
                                                @Positive
                                                @RequestParam(value = "size", defaultValue = "10")
                                                Integer size) {
        log.info("Запрос на получение списка комментариев с парамерами: только опубликованные = {}", onlyPublished);
        return commentService.getAllCommentsAdmin(ownerId, commenterId, onlyPublished, from, size);
    }

    @PutMapping
    public CommentDto updateComment(@Valid @RequestBody UpdateCommentDto updateCommentDto) {
        log.info("Обновление комментария  с id {} ", updateCommentDto.getId());
        return commentService.updateCommentAdmin(updateCommentDto);
    }
}
