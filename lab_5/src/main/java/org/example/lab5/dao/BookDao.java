package org.example.lab5.dao;

import java.util.List;
import java.util.Optional;

import org.example.lab5.entity.Book;
import org.hibernate.SessionFactory;

public class BookDao extends AbstractDao<Book> {
    public BookDao(SessionFactory sessionFactory) {
        super(sessionFactory, Book.class);
    }

    public Optional<Book> findByTitleWithDetails(String title) {
        return executeRead(session -> session.createQuery(
                        "select distinct b from Book b " +
                                "left join fetch b.category " +
                                "left join fetch b.copies " +
                                "left join fetch b.reservedByReaders " +
                                "where b.title = :title",
                        Book.class)
                .setParameter("title", title)
                .uniqueResultOptional());
    }

    public List<Book> findByCategoryName(String categoryName) {
        return executeRead(session -> session.createQuery(
                        "select b from Book b join b.category c where c.name = :name",
                        Book.class)
                .setParameter("name", categoryName)
                .getResultList());
    }
}
