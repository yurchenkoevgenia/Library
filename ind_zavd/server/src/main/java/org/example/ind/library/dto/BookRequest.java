package org.example.ind.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookRequest(
        @NotBlank
        @Size(max = 200)
        String title,
        @NotBlank
        @Size(max = 160)
        String author,
        @NotBlank
        @Size(max = 20)
        String isbn,
        @NotNull
        Long categoryId
) {
}

