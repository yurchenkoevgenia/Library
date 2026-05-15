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
        return executeHql(session -> session.createQuery(
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
        return executeHql(session -> session.createQuery(
                        "select b from Book b join b.category c where c.name = :name",
                        Book.class)
                .setParameter("name", categoryName)
                .getResultList());
    }

    public List<String> findTitlesByCategoryNative(String categoryName) {
        return executeNative(session -> session.createNativeQuery(
                        "select b.title from books b join categories c on c.id = b.category_id where c.name = :name order by b.title",
                        String.class)
                .setParameter("name", categoryName)
                .getResultList());
    }

    public List<String> findTitlesByCategoryHql(String categoryName) {
        return executeHql(session -> session.createQuery(
                        "select b.title from Book b join b.category c where c.name = :name order by b.title",
                        String.class)
                .setParameter("name", categoryName)
                .getResultList());
    }

    public long countBooksInCategoryNative(String categoryName) {
        return executeNative(session -> ((Number) session.createNativeQuery(
                        "select count(*) from books b join categories c on c.id = b.category_id where c.name = :name")
                .setParameter("name", categoryName)
                .getSingleResult()).longValue());
    }

    public long countBooksInCategoryHql(String categoryName) {
        return executeHql(session -> session.createQuery(
                        "select count(b) from Book b join b.category c where c.name = :name",
                        Long.class)
                .setParameter("name", categoryName)
                .getSingleResult());
    }
}
