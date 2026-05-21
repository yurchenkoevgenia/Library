package org.example.lab5.repository;

import java.util.List;
import java.util.Optional;

import org.example.lab5.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    Optional<Reader> findByEmailIgnoreCase(String email);

    List<Reader> findAllByOrderByFullNameAsc();
}
