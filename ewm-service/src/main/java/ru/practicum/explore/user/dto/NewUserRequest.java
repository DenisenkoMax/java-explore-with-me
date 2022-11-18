package ru.practicum.explore.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserRequest {
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    @Email
    private String email;
}
