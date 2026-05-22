package org.example.lab5;

import java.util.List;

import org.example.lab5.entity.Book;
import org.example.lab5.entity.BookCopy;
import org.example.lab5.entity.Category;
import org.example.lab5.entity.Reader;
import org.example.lab5.repository.BookRepository;
import org.example.lab5.repository.CategoryRepository;
import org.example.lab5.service.BookService;
import org.example.lab5.service.CategoryService;
import org.example.lab5.service.ReaderService;
import org.example.lab5.service.ReservationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class LibraryApplicationTests {
    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ReaderService readerService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void cleanDatabase() {
        databaseCleaner.clean();
    }

    @Test
    void shouldSaveCategoryBookAndCopy() {
        Category category = categoryService.create("Adventure");
        Book book = bookService.create("The Hobbit", "J. R. R. Tolkien", "9780547928227", category.getId(), "HOB-001");

        Assertions.assertEquals(1, categoryRepository.count());
        Assertions.assertEquals(1, bookRepository.count());
        Assertions.assertEquals(1, book.getCopies().size());
        Assertions.assertEquals("Adventure", book.getCategory().getName());
    }

    @Test
    void shouldUpdateReservationForReader() {
        Category category = categoryService.create("Programming");
        Book book = bookService.create("Clean Code", "Robert C. Martin", "9780132350884", category.getId(), "CC-001");
        Reader reader = readerService.create("Test Reader", "reader@example.com", "+380000000000", "LIB-TEST-1");

        reservationService.reserveBook(reader.getId(), book.getId());

        Reader savedReader = readerService.getById(reader.getId());
        Assertions.assertEquals(1, savedReader.getReservedBooks().size());
        Assertions.assertEquals("Clean Code", savedReader.getReservedBooks().iterator().next().getTitle());
    }

    @Test
    void shouldSearchBooksByKeyword() {
        Category category = categoryService.create("History");
        bookService.create("A Brief History of Time", "Stephen Hawking", "9780553380163", category.getId(), "HST-001");
        bookService.create("History of Art", "Janson", "9780130615466", category.getId(), "ART-001");

        List<Book> found = bookService.findAll("History");
        Assertions.assertEquals(2, found.size());
    }

    @Test
    void shouldCancelReservation() {
        Category category = categoryService.create("Fiction");
        Book book = bookService.create("The Hobbit", "J. R. R. Tolkien", "9780547928227", category.getId(), "HOB-001");
        Reader reader = readerService.create("Test Reader", "reader@example.com", "+380000000000", "LIB-TEST-2");

        reservationService.reserveBook(reader.getId(), book.getId());
        reservationService.cancelReservation(reader.getId(), book.getId());

        Reader savedReader = readerService.getById(reader.getId());
        Assertions.assertTrue(savedReader.getReservedBooks().isEmpty());
    }
}
