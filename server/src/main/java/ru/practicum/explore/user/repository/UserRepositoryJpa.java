package ru.practicum.explore.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.user.model.User;

@Repository
public interface UserRepositoryJpa extends JpaRepository<User, Long> {

}
