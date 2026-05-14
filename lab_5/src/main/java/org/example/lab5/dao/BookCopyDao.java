package org.example.lab5.dao;

import java.util.List;

import org.example.lab5.entity.BookCopy;
import org.example.lab5.entity.CopyStatus;
import org.hibernate.SessionFactory;

public class BookCopyDao extends AbstractDao<BookCopy> {
    public BookCopyDao(SessionFactory sessionFactory) {
        super(sessionFactory, BookCopy.class);
    }

    public List<BookCopy> findByStatus(CopyStatus status) {
        return executeRead(session -> session.createQuery(
                        "from BookCopy copy where copy.status = :status",
                        BookCopy.class)
                .setParameter("status", status)
                .getResultList());
    }

    public long countAvailableCopiesByBookId(Long bookId) {
        return executeRead(session -> session.createQuery(
                        "select count(copy) from BookCopy copy where copy.book.id = :bookId and copy.status = :status",
                        Long.class)
                .setParameter("bookId", bookId)
                .setParameter("status", CopyStatus.AVAILABLE)
                .getSingleResult());
    }
}
