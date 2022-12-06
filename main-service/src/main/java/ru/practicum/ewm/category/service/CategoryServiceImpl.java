package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return categoryRepository.findAll(pageable).stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Введено некорректное значение id категории"));
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = CategoryMapper.toCreateCategory(categoryDto);
        try {
            category = categoryRepository.save(category);
            return CategoryMapper.toCategoryDto(category);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Категория с таким именем уже существует");
        }
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        if (categoryDto.getId() == null || categoryDto.getId() <= 0) {
            throw new IllegalArgumentException("Введено некорректное значение id категории для обновления");
        }
        Category category = CategoryMapper.toCategory(categoryDto);
        List<String> names = getAllCategory()
                .stream()
                .map(Category::getName)
                .collect(Collectors.toList());
        if (names.contains(category.getName())) {
            throw new ConflictException("Категория с таким именем уже существует");
        }
        category = categoryRepository.save(category);
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public void deleteCategoryById(Long catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Введено некорректное значение id категории"));
        categoryRepository.delete(category);
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
