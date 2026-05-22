package org.example.ind.library.dto;

public record CategoryResponse(
        Long id,
        String name,
        long bookCount
) {
}

