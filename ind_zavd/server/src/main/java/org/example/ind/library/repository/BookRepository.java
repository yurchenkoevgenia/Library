package org.example.ind.library.repository;

import java.util.List;
import java.util.Optional;

import org.example.ind.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbnIgnoreCase(String isbn);

    List<Book> findAllByOrderByTitleAsc();

    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrderByTitleAsc(String title, String author);
}

