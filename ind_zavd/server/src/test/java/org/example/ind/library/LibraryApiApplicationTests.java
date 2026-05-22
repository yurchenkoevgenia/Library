package org.example.ind.library;

import org.example.ind.library.dto.BookRequest;
import org.example.ind.library.dto.CategoryRequest;
import org.example.ind.library.repository.BookRepository;
import org.example.ind.library.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LibraryApiApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void cleanDatabase() {
        bookRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void shouldCreateCategoryAndBook() throws Exception {
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Fantasy"}
                                """))
                .andExpect(status().isCreated());

        Long categoryId = categoryRepository.findByNameIgnoreCase("Fantasy").orElseThrow().getId();

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"The Hobbit","author":"J. R. R. Tolkien","isbn":"9780547928227","categoryId":%d}
                                """.formatted(categoryId)))
                .andExpect(status().isCreated());

        Assertions.assertEquals(1, bookRepository.count());
    }

    @Test
    void shouldListCategories() throws Exception {
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"History"}
                                """))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Science"}
                                """))
                .andExpect(status().isCreated());

        Long id = categoryRepository.findByNameIgnoreCase("Science").orElseThrow().getId();
        mockMvc.perform(delete("/api/categories/" + id))
                .andExpect(status().isNoContent());
    }
}
