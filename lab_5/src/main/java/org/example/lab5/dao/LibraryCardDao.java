package org.example.lab5.dao;

import java.util.Optional;

import org.example.lab5.entity.LibraryCard;
import org.hibernate.SessionFactory;

public class LibraryCardDao extends AbstractDao<LibraryCard> {
    public LibraryCardDao(SessionFactory sessionFactory) {
        super(sessionFactory, LibraryCard.class);
    }

    public Optional<LibraryCard> findByCardNumber(String cardNumber) {
        return executeHql(session -> session.createQuery(
                        "from LibraryCard card where card.cardNumber = :cardNumber",
                        LibraryCard.class)
                .setParameter("cardNumber", cardNumber)
                .uniqueResultOptional());
    }
}
