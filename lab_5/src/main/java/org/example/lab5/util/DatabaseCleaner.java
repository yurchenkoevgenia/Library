package org.example.lab5.util;

import org.hibernate.SessionFactory;

public final class DatabaseCleaner {
    private DatabaseCleaner() {
    }

    public static void clean(SessionFactory sessionFactory) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            session.createNativeQuery("delete from reader_book_reservations").executeUpdate();
            session.createNativeQuery("delete from book_copies").executeUpdate();
            session.createNativeQuery("delete from books").executeUpdate();
            session.createNativeQuery("delete from readers").executeUpdate();
            session.createNativeQuery("delete from library_cards").executeUpdate();
            session.createNativeQuery("delete from categories").executeUpdate();
            transaction.commit();
        }
    }
}
