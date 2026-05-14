package org.example.lab5.dao;

import java.util.List;
import java.util.Optional;

import org.example.lab5.entity.Book;
import org.example.lab5.entity.Reader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ReaderDao extends AbstractDao<Reader> {
    public ReaderDao(SessionFactory sessionFactory) {
        super(sessionFactory, Reader.class);
    }

    public Optional<Reader> findByEmailWithDetails(String email) {
        return executeRead(session -> session.createQuery(
                        "select distinct r from Reader r " +
                                "left join fetch r.libraryCard " +
                                "left join fetch r.reservedBooks " +
                                "where r.email = :email",
                        Reader.class)
                .setParameter("email", email)
                .uniqueResultOptional());
    }

    public Reader reserveBooks(Long readerId, List<Long> bookIds) {
        return executeInTransaction(session -> {
            Reader reader = session.get(Reader.class, readerId);
            if (reader == null) {
                throw new IllegalArgumentException("Reader not found: " + readerId);
            }

            for (Long bookId : bookIds) {
                Book book = session.get(Book.class, bookId);
                if (book == null) {
                    throw new IllegalArgumentException("Book not found: " + bookId);
                }
                reader.reserveBook(book);
            }

            session.flush();
            return reader;
        });
    }

    public void clearReservations() {
        executeInTransaction(session -> {
            session.createNativeQuery("delete from reader_book_reservations").executeUpdate();
            return null;
        });
    }
}
