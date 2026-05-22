package org.example.lab5.repository;

import java.util.List;
import java.util.Optional;

import org.example.lab5.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbnIgnoreCase(String isbn);

    List<Book> findAllByOrderByTitleAsc();

    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrderByTitleAsc(String title, String author);

    List<Book> findByCategoryIdOrderByTitleAsc(Long categoryId);
}
