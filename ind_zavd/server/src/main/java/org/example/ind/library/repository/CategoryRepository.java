package org.example.ind.library.repository;

import java.util.Optional;

import org.example.ind.library.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameIgnoreCase(String name);

    java.util.List<Category> findAllByOrderByNameAsc();
}

