package org.example.ind.library.config;

import org.example.ind.library.entity.Book;
import org.example.ind.library.entity.Category;
import org.example.ind.library.repository.BookRepository;
import org.example.ind.library.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class SeedDataConfig {
    @Bean
    CommandLineRunner seed(CategoryRepository categoryRepository, BookRepository bookRepository) {
        return args -> {
            if (categoryRepository.count() > 0 || bookRepository.count() > 0) {
                return;
            }

            Category fiction = new Category();
            fiction.setName("Fiction");

            Category programming = new Category();
            programming.setName("Programming");

            Category history = new Category();
            history.setName("History");

            categoryRepository.save(fiction);
            categoryRepository.save(programming);
            categoryRepository.save(history);

            bookRepository.save(new Book("The Hobbit", "J. R. R. Tolkien", "9780547928227", fiction));
            bookRepository.save(new Book("Clean Code", "Robert C. Martin", "9780132350884", programming));
            bookRepository.save(new Book("A Brief History of Time", "Stephen Hawking", "9780553380163", history));
        };
    }
}

