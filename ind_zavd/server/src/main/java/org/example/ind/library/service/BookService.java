package org.example.ind.library.service;

import java.util.List;

import org.example.ind.library.dto.BookRequest;
import org.example.ind.library.dto.BookResponse;
import org.example.ind.library.entity.Book;
import org.example.ind.library.entity.Category;
import org.example.ind.library.exception.NotFoundException;
import org.example.ind.library.repository.BookRepository;
import org.example.ind.library.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<BookResponse> findAll(String query) {
        List<Book> books;
        if (query == null || query.trim().isEmpty()) {
            books = bookRepository.findAllByOrderByTitleAsc();
        } else {
            String normalized = query.trim();
            books = bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrderByTitleAsc(normalized, normalized);
        }
        return books.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public BookResponse findById(Long id) {
        return toResponse(getEntity(id));
    }

    @Transactional
    public BookResponse create(BookRequest request) {
        String isbn = request.isbn().trim();
        if (bookRepository.findByIsbnIgnoreCase(isbn).isPresent()) {
            throw new IllegalArgumentException("Book with this ISBN already exists");
        }
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NotFoundException("Category not found: " + request.categoryId()));
        Book book = new Book();
        book.setTitle(request.title().trim());
        book.setAuthor(request.author().trim());
        book.setIsbn(isbn);
        book.setCategory(category);
        return toResponse(bookRepository.save(book));
    }

    @Transactional
    public BookResponse update(Long id, BookRequest request) {
        Book book = getEntity(id);
        String isbn = request.isbn().trim();
        bookRepository.findByIsbnIgnoreCase(isbn)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Book with this ISBN already exists");
                });
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NotFoundException("Category not found: " + request.categoryId()));
        book.setTitle(request.title().trim());
        book.setAuthor(request.author().trim());
        book.setIsbn(isbn);
        book.setCategory(category);
        return toResponse(bookRepository.save(book));
    }

    @Transactional
    public void delete(Long id) {
        bookRepository.delete(getEntity(id));
    }

    public Book getEntity(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found: " + id));
    }

    private BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getCategory().getId(),
                book.getCategory().getName()
        );
    }
}
