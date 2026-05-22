package org.example.ind.library.service;

import java.util.List;

import org.example.ind.library.dto.CategoryRequest;
import org.example.ind.library.dto.CategoryResponse;
import org.example.ind.library.entity.Category;
import org.example.ind.library.exception.NotFoundException;
import org.example.ind.library.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAllByOrderByNameAsc().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id) {
        return toResponse(getEntity(id));
    }

    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        String name = request.name().trim();
        if (categoryRepository.findByNameIgnoreCase(name).isPresent()) {
            throw new IllegalArgumentException("Category with this name already exists");
        }
        Category category = new Category();
        category.setName(name);
        return toResponse(categoryRepository.save(category));
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = getEntity(id);
        String name = request.name().trim();
        categoryRepository.findByNameIgnoreCase(name)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Category with this name already exists");
                });
        category.setName(name);
        return toResponse(categoryRepository.save(category));
    }

    @Transactional
    public void delete(Long id) {
        Category category = getEntity(id);
        categoryRepository.delete(category);
    }

    public Category getEntity(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found: " + id));
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.getBooks().size());
    }
}
