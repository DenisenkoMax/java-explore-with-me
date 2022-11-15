package ru.practicum.explore.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.category.dto.CategoryDto;
import ru.practicum.explore.category.dto.CategoryDtoAnswer;
import ru.practicum.explore.category.dto.CategoryDtoPatch;
import ru.practicum.explore.category.dto.CategoryMapper;
import ru.practicum.explore.category.model.Category;
import ru.practicum.explore.category.repository.CategoryRepositoryJpa;
import ru.practicum.explore.exception.IllegalArgumentEx;
import ru.practicum.explore.exception.NotFoundEx;
import ru.practicum.explore.validation.Validation;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepositoryJpa repository;
    private final Validation validation;


    @Override
    public Optional<CategoryDtoAnswer> createCategory(CategoryDto categoryDto) {
        return Optional.ofNullable
                (CategoryMapper.toCategoryDtoAnswer(repository.save(CategoryMapper.toCategory(categoryDto))));
    }
    @Override
    public Optional<CategoryDtoAnswer> updateCategory(CategoryDtoPatch categoryDtoPatch) throws NotFoundEx {
        validation.validateCategory(categoryDtoPatch.getId());
        Category category = repository.findById(categoryDtoPatch.getId()).get();
        category.setName(categoryDtoPatch.getName());
        return Optional.ofNullable
                (CategoryMapper.toCategoryDtoAnswer(repository.save(category)));
    }

    @Override
    public void deleteCategoryById(long id) throws NotFoundEx {
        validation.validateCategory(id);
        repository.deleteById(id);
    }

    @Override
    public List<CategoryDtoAnswer> getAllCategories(int from, int size) throws IllegalArgumentEx {
        validation.validatePagination(from, size);
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        return  repository.findAll(pageable).stream().
                map(p -> CategoryMapper.toCategoryDtoAnswer(p)).collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryDtoAnswer> getCategoryById(long id) throws NotFoundEx {
        validation.validateCategory(id);
        return Optional.ofNullable(CategoryMapper.toCategoryDtoAnswer(repository.findById(id).get()));
    }
}
