package org.example.ind.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank
        @Size(max = 120)
        String name
) {
}

