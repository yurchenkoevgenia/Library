package org.example.lab5.repository;

import java.util.Optional;

import org.example.lab5.entity.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    Optional<BookCopy> findByCopyCodeIgnoreCase(String copyCode);
}
