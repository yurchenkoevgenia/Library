package org.example.lab5.demo;

import java.time.LocalDate;
import java.util.List;

import org.example.lab5.dao.BookCopyDao;
import org.example.lab5.dao.BookDao;
import org.example.lab5.dao.CategoryDao;
import org.example.lab5.dao.LibraryCardDao;
import org.example.lab5.dao.ReaderDao;
import org.example.lab5.entity.Book;
import org.example.lab5.entity.BookCopy;
import org.example.lab5.entity.Category;
import org.example.lab5.entity.LibraryCard;
import org.example.lab5.entity.Reader;
import org.example.lab5.util.DatabaseCleaner;
import org.hibernate.SessionFactory;

public class LibraryDemo {
    private final SessionFactory sessionFactory;
    private final CategoryDao categoryDao;
    private final BookDao bookDao;
    private final BookCopyDao bookCopyDao;
    private final ReaderDao readerDao;
    private final LibraryCardDao libraryCardDao;

    public LibraryDemo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.categoryDao = new CategoryDao(sessionFactory);
        this.bookDao = new BookDao(sessionFactory);
        this.bookCopyDao = new BookCopyDao(sessionFactory);
        this.readerDao = new ReaderDao(sessionFactory);
        this.libraryCardDao = new LibraryCardDao(sessionFactory);
    }

    public void run() {
        DatabaseCleaner.clean(sessionFactory);

        Category fiction = new Category("Fiction");
        Book dune = new Book("Dune", "Frank Herbert", "9780441013593");
        dune.addCopy(new BookCopy("DUNE-001"));
        dune.addCopy(new BookCopy("DUNE-002"));
        fiction.addBook(dune);

        Category computerScience = new Category("Computer Science");
        Book cleanCode = new Book("Clean Code", "Robert C. Martin", "9780132350884");
        cleanCode.addCopy(new BookCopy("CC-001"));
        computerScience.addBook(cleanCode);

        categoryDao.save(fiction);
        categoryDao.save(computerScience);

        Reader reader = new Reader("Evgenia Yurchenko", "zhenya.yurchenko.14@gmail.com");
        reader.setPhoneNumber("+380000000000");
        reader.assignLibraryCard(new LibraryCard("LIB-1001", LocalDate.of(2026, 5, 14)));
        readerDao.save(reader);

        readerDao.reserveBooks(reader.getId(), List.of(dune.getId(), cleanCode.getId()));

        printSummary(reader.getEmail());
    }

    private void printSummary(String email) {
        Reader savedReader = readerDao.findByEmailWithDetails(email).orElseThrow();
        System.out.println("Reader: " + savedReader.getFullName());
        System.out.println("Card: " + savedReader.getLibraryCard().getCardNumber());
        System.out.println("Reserved books: " + savedReader.getReservedBooks().size());
        for (Book book : savedReader.getReservedBooks()) {
            System.out.println("- " + book.getTitle() + " / " + book.getAuthor());
        }

        Book savedBook = bookDao.findByTitleWithDetails("Dune").orElseThrow();
        System.out.println("Copies of Dune: " + savedBook.getCopies().size());
        System.out.println("Available Dune copies: " + bookCopyDao.countAvailableCopiesByBookId(savedBook.getId()));

        System.out.println("Categories in DB: " + categoryDao.findAll().size());
        System.out.println("Cards in DB: " + libraryCardDao.findAll().size());
        System.out.println("Native category names: " + categoryDao.findNamesNative());
        System.out.println("HQL category names: " + categoryDao.findNamesHql());
        System.out.println("Native book titles in Fiction: " + bookDao.findTitlesByCategoryNative("Fiction"));
        System.out.println("HQL book titles in Fiction: " + bookDao.findTitlesByCategoryHql("Fiction"));
        System.out.println("Native reader emails: " + readerDao.findEmailsNative());
        System.out.println("HQL reader emails: " + readerDao.findEmailsHql());
        System.out.println("Native reserved titles: " + readerDao.findReservedBookTitlesNative(email));
        System.out.println("HQL reserved titles: " + readerDao.findReservedBookTitlesHql(email));
    }
}
