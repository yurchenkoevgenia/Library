package org.example.lab5.service;

import org.example.lab5.entity.Book;
import org.example.lab5.entity.Reader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {
    private final ReaderService readerService;
    private final BookService bookService;

    public ReservationService(ReaderService readerService, BookService bookService) {
        this.readerService = readerService;
        this.bookService = bookService;
    }

    @Transactional
    public void reserveBook(Long readerId, Long bookId) {
        Reader reader = readerService.getById(readerId);
        Book book = bookService.getById(bookId);
        reader.reserveBook(book);
        readerService.save(reader);
    }

    @Transactional
    public void cancelReservation(Long readerId, Long bookId) {
        Reader reader = readerService.getById(readerId);
        Book book = bookService.getById(bookId);
        reader.cancelReservation(book);
        readerService.save(reader);
    }
}
