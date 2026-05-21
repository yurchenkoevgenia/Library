package org.example.lab5.repository;

import java.util.Optional;

import org.example.lab5.entity.LibraryCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryCardRepository extends JpaRepository<LibraryCard, Long> {
    Optional<LibraryCard> findByCardNumberIgnoreCase(String cardNumber);
}
