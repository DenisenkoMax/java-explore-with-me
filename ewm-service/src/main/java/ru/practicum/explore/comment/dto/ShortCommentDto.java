package ru.practicum.explore.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortCommentDto {
    private Long commenter;
    private Long event;
    private String commentText;
}
