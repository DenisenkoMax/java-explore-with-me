package ru.practicum.explore.user.dto;

import lombok.Getter;

@Getter
public class UserDtoAnswer {
    private Long id;
    private String name;
    private String email;

    public UserDtoAnswer(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
