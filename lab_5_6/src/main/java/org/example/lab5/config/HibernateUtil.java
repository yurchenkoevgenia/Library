package org.example.lab5.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.example.lab5.entity.Book;
import org.example.lab5.entity.BookCopy;
import org.example.lab5.entity.Category;
import org.example.lab5.entity.LibraryCard;
import org.example.lab5.entity.Reader;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public final class HibernateUtil {
    private static SessionFactory sessionFactory;

    private HibernateUtil() {
    }

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Properties properties = loadProperties();
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySettings(properties)
                    .build();

            sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClass(Category.class)
                    .addAnnotatedClass(Book.class)
                    .addAnnotatedClass(BookCopy.class)
                    .addAnnotatedClass(Reader.class)
                    .addAnnotatedClass(LibraryCard.class)
                    .buildMetadata()
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    public static synchronized void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }

    private static Properties loadProperties() {
        try (InputStream inputStream = HibernateUtil.class.getClassLoader().getResourceAsStream("properties.xml")) {
            if (inputStream == null) {
                throw new IllegalStateException("properties.xml is not found");
            }
            Properties properties = new Properties();
            properties.loadFromXML(inputStream);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Cannot load properties.xml", exception);
        }
    }
}
