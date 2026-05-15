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
        return executeHql(session -> session.createQuery(
                        "select distinct r from Reader r " +
                                "left join fetch r.libraryCard " +
                                "left join fetch r.reservedBooks " +
                                "where r.email = :email",
                        Reader.class)
                .setParameter("email", email)
                .uniqueResultOptional());
    }

    public List<String> findEmailsNative() {
        return executeNative(session -> session.createNativeQuery(
                        "select r.email from readers r order by r.email",
                        String.class)
                .getResultList());
    }

    public List<String> findEmailsHql() {
        return executeHql(session -> session.createQuery(
                        "select r.email from Reader r order by r.email",
                        String.class)
                .getResultList());
    }

    public List<String> findReservedBookTitlesNative(String email) {
        return executeNative(session -> session.createNativeQuery(
                        "select b.title from readers r " +
                                "join reader_book_reservations rbr on rbr.reader_id = r.id " +
                                "join books b on b.id = rbr.book_id " +
                                "where r.email = :email order by b.title",
                        String.class)
                .setParameter("email", email)
                .getResultList());
    }

    public List<String> findReservedBookTitlesHql(String email) {
        return executeHql(session -> session.createQuery(
                        "select b.title from Reader r join r.reservedBooks b where r.email = :email order by b.title",
                        String.class)
                .setParameter("email", email)
                .getResultList());
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
