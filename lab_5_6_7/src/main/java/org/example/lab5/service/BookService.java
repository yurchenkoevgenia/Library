package org.example.lab5.service;

import java.util.List;

import org.example.lab5.entity.Book;
import org.example.lab5.entity.BookCopy;
import org.example.lab5.entity.Category;
import org.example.lab5.repository.BookCopyRepository;
import org.example.lab5.repository.BookRepository;
import org.example.lab5.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookCopyRepository bookCopyRepository;

    public BookService(BookRepository bookRepository,
                       CategoryRepository categoryRepository,
                       BookCopyRepository bookCopyRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.bookCopyRepository = bookCopyRepository;
    }

    public List<Book> findAll(String query) {
        if (query == null || query.trim().isEmpty()) {
            return bookRepository.findAllByOrderByTitleAsc();
        }
        String normalized = query.trim();
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrderByTitleAsc(normalized, normalized);
    }

    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Книгу не знайдено"));
    }

    @Transactional
    public Book create(String title, String author, String isbn, Long categoryId, String copyCode) {
        Book book = new Book(require(title, "Назва книги"), require(author, "Автор"), require(isbn, "ISBN"));
        if (bookRepository.findByIsbnIgnoreCase(book.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("Книга з таким ISBN вже існує");
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Категорію не знайдено"));
        category.addBook(book);
        if (copyCode != null && !copyCode.trim().isEmpty()) {
            book.addCopy(new BookCopy(copyCode.trim()));
        } else {
            book.addCopy(new BookCopy("COPY-" + System.currentTimeMillis()));
        }
        return bookRepository.save(book);
    }

    @Transactional
    public Book update(Long id, String title, String author, String isbn, Long categoryId) {
        Book book = getById(id);
        String normalizedIsbn = require(isbn, "ISBN");
        if (!book.getIsbn().equalsIgnoreCase(normalizedIsbn) && bookRepository.findByIsbnIgnoreCase(normalizedIsbn).isPresent()) {
            throw new IllegalArgumentException("Книга з таким ISBN вже існує");
        }

        book.setTitle(require(title, "Назва книги"));
        book.setAuthor(require(author, "Автор"));
        book.setIsbn(normalizedIsbn);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Категорію не знайдено"));
        book.setCategory(category);
        return bookRepository.save(book);
    }

    @Transactional
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public Book addCopy(Long bookId, String copyCode) {
        Book book = getById(bookId);
        String code = copyCode == null || copyCode.trim().isEmpty()
                ? "COPY-" + System.currentTimeMillis()
                : copyCode.trim();
        if (bookCopyRepository.findByCopyCodeIgnoreCase(code).isPresent()) {
            throw new IllegalArgumentException("Такий код примірника вже існує");
        }
        book.addCopy(new BookCopy(code));
        return bookRepository.save(book);
    }

    private String require(String value, String label) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(label + " не може бути порожнім");
        }
        return value.trim();
    }
}
