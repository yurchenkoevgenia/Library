package org.example.lab5.dao;

import java.util.Optional;

import org.example.lab5.entity.Category;
import org.hibernate.SessionFactory;

public class CategoryDao extends AbstractDao<Category> {
    public CategoryDao(SessionFactory sessionFactory) {
        super(sessionFactory, Category.class);
    }

    public Optional<Category> findByNameWithBooks(String name) {
        return executeRead(session -> session.createQuery(
                        "select distinct c from Category c left join fetch c.books where c.name = :name",
                        Category.class)
                .setParameter("name", name)
                .uniqueResultOptional());
    }
}
