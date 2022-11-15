package ru.practicum.explore.user.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.exception.IllegalArgumentEx;
import ru.practicum.explore.exception.NotFoundEx;
import ru.practicum.explore.user.service.UserService;
import ru.practicum.explore.user.dto.UserDto;
import ru.practicum.explore.user.dto.UserDtoAnswer;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/admin/users",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AdminUserController {
    private final UserService userService;
    private static final String FIRST_ELEMENT = "0";
    private static final String PAGE_SIZE = "10";

    @GetMapping
    public ResponseEntity<List<UserDtoAnswer>> getAllUsers(
            @RequestParam(value = "ids", required = false) Long[] ids,
            @RequestParam(name = "from", defaultValue = FIRST_ELEMENT) int from,
            @RequestParam(name = "size", defaultValue = PAGE_SIZE) int size) throws IllegalArgumentEx {
        return new ResponseEntity<>(userService.getAllUsers(ids, from, size), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDtoAnswer> createUser(@Valid @RequestBody UserDto userDto) {
        return userService.createUser(userDto).map(newUser -> new ResponseEntity<>(newUser, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable long id) throws NotFoundEx {
        userService.deleteUserById(id);
    }

}
