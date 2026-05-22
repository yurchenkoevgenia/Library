package org.example.lab5.config;

import java.time.LocalDate;

import org.example.lab5.entity.Book;
import org.example.lab5.entity.BookCopy;
import org.example.lab5.entity.Category;
import org.example.lab5.entity.LibraryCard;
import org.example.lab5.entity.Reader;
import org.example.lab5.repository.BookRepository;
import org.example.lab5.repository.CategoryRepository;
import org.example.lab5.repository.ReaderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

@Configuration
@Profile("dev")
public class StartupDataConfig {
    @Bean
    @Order(1)
    CommandLineRunner seedLibraryData(CategoryRepository categoryRepository,
                                      BookRepository bookRepository,
                                      ReaderRepository readerRepository) {
        return args -> {
            if (categoryRepository.count() > 0 || bookRepository.count() > 0 || readerRepository.count() > 0) {
                return;
            }

            Category fiction = new Category("Fiction");
            Book hobbit = new Book("The Hobbit", "J. R. R. Tolkien", "9780547928227");
            hobbit.addCopy(new BookCopy("HOB-001"));
            fiction.addBook(hobbit);

            Category programming = new Category("Programming");
            Book cleanCode = new Book("Clean Code", "Robert C. Martin", "9780132350884");
            cleanCode.addCopy(new BookCopy("CC-001"));
            programming.addBook(cleanCode);

            Category history = new Category("History");
            Book historyBook = new Book("A Brief History of Time", "Stephen Hawking", "9780553380163");
            historyBook.addCopy(new BookCopy("HST-001"));
            history.addBook(historyBook);

            categoryRepository.save(fiction);
            categoryRepository.save(programming);
            categoryRepository.save(history);

            Reader reader = new Reader("Evgenia Yurchenko", "zhenya.yurchenko.14@gmail.com");
            reader.setPhoneNumber("+380000000000");
            LibraryCard card = new LibraryCard("LIB-1001", LocalDate.now());
            card.setExpireDate(LocalDate.now().plusYears(1));
            reader.assignLibraryCard(card);
            readerRepository.save(reader);

            reader.reserveBook(cleanCode);
            readerRepository.save(reader);
        };
    }
}
