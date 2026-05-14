package org.example.lab5;

import java.time.LocalDate;
import java.util.List;

import org.example.lab5.config.HibernateUtil;
import org.example.lab5.dao.BookDao;
import org.example.lab5.dao.CategoryDao;
import org.example.lab5.dao.ReaderDao;
import org.example.lab5.entity.Book;
import org.example.lab5.entity.BookCopy;
import org.example.lab5.entity.Category;
import org.example.lab5.entity.LibraryCard;
import org.example.lab5.entity.Reader;
import org.example.lab5.util.DatabaseCleaner;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LibraryHibernateTest {
    private static final SessionFactory SESSION_FACTORY = HibernateUtil.getSessionFactory();

    private final CategoryDao categoryDao = new CategoryDao(SESSION_FACTORY);
    private final BookDao bookDao = new BookDao(SESSION_FACTORY);
    private final ReaderDao readerDao = new ReaderDao(SESSION_FACTORY);

    @BeforeEach
    void setUp() {
        DatabaseCleaner.clean(SESSION_FACTORY);
    }

    @AfterAll
    static void tearDown() {
        HibernateUtil.shutdown();
    }

    @Test
    void shouldPersistBookHierarchy() {
        Category category = new Category("Adventure");
        Book book = new Book("The Hobbit", "J. R. R. Tolkien", "9780547928227");
        book.addCopy(new BookCopy("HOB-001"));
        book.addCopy(new BookCopy("HOB-002"));
        category.addBook(book);

        categoryDao.save(category);

        Book savedBook = bookDao.findByTitleWithDetails("The Hobbit").orElseThrow();
        Assertions.assertEquals("Adventure", savedBook.getCategory().getName());
        Assertions.assertEquals(2, savedBook.getCopies().size());
    }

    @Test
    void shouldCreateReaderCardAndReservation() {
        Category category = new Category("Programming");
        Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884");
        book.addCopy(new BookCopy("CC-TEST-001"));
        category.addBook(book);
        categoryDao.save(category);

        Reader reader = new Reader("Test Reader", "reader@example.com");
        reader.assignLibraryCard(new LibraryCard("LIB-TEST-1", LocalDate.of(2026, 5, 14)));
        readerDao.save(reader);

        readerDao.reserveBooks(reader.getId(), List.of(book.getId()));

        Reader savedReader = readerDao.findByEmailWithDetails("reader@example.com").orElseThrow();
        Assertions.assertEquals("LIB-TEST-1", savedReader.getLibraryCard().getCardNumber());
        Assertions.assertEquals(1, savedReader.getReservedBooks().size());
        Assertions.assertEquals("Clean Code", savedReader.getReservedBooks().iterator().next().getTitle());
    }
}
