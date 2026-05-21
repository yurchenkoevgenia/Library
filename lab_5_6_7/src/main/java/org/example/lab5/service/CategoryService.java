package org.example.lab5.service;

import java.util.List;

import org.example.lab5.entity.Category;
import org.example.lab5.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAllByOrderByNameAsc();
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Категорію не знайдено"));
    }

    @Transactional
    public Category create(String name) {
        String value = normalize(name, "Назва категорії");
        if (categoryRepository.findByNameIgnoreCase(value).isPresent()) {
            throw new IllegalArgumentException("Така категорія вже існує");
        }
        return categoryRepository.save(new Category(value));
    }

    @Transactional
    public Category update(Long id, String name) {
        Category category = getById(id);
        String value = normalize(name, "Назва категорії");
        category.setName(value);
        return categoryRepository.save(category);
    }

    @Transactional
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    private String normalize(String value, String label) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(label + " не може бути порожнім");
        }
        return value.trim();
    }
}
