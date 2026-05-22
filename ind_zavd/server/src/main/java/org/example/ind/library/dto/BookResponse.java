package org.example.ind.library.dto;

public record BookResponse(
        Long id,
        String title,
        String author,
        String isbn,
        Long categoryId,
        String categoryName
) {
}

