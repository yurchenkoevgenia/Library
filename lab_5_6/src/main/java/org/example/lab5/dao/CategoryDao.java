package org.example.lab5.dao;

import java.util.Optional;
import java.util.List;

import org.example.lab5.entity.Category;
import org.hibernate.SessionFactory;

public class CategoryDao extends AbstractDao<Category> {
    public CategoryDao(SessionFactory sessionFactory) {
        super(sessionFactory, Category.class);
    }

    public Optional<Category> findByNameWithBooks(String name) {
        return executeHql(session -> session.createQuery(
                        "select distinct c from Category c left join fetch c.books where c.name = :name",
                        Category.class)
                .setParameter("name", name)
                .uniqueResultOptional());
    }

    public List<String> findNamesNative() {
        return executeNative(session -> session.createNativeQuery(
                        "select c.name from categories c order by c.name",
                        String.class)
                .getResultList());
    }

    public List<String> findNamesHql() {
        return executeHql(session -> session.createQuery(
                        "select c.name from Category c order by c.name",
                        String.class)
                .getResultList());
    }
}
