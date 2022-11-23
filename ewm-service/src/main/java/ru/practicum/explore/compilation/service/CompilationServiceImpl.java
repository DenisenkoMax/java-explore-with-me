package ru.practicum.explore.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.compilation.repository.CompilationRepositoryJpa;
import ru.practicum.explore.validation.Validation;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepositoryJpa repository;
    private final Validation validation;

}
