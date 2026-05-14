package org.example.lab5.dao;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDao<T> {
    private static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();
    private static final Validator VALIDATOR = VALIDATOR_FACTORY.getValidator();

    protected final SessionFactory sessionFactory;
    private final Class<T> entityClass;

    protected AbstractDao(SessionFactory sessionFactory, Class<T> entityClass) {
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }

    public T save(T entity) {
        validate(entity);
        return executeInTransaction(session -> {
            session.persist(entity);
            session.flush();
            return entity;
        });
    }

    public Optional<T> findById(Long id) {
        return executeRead(session -> Optional.ofNullable(session.get(entityClass, id)));
    }

    public List<T> findAll() {
        return executeRead(session -> session.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList());
    }

    public void deleteAll() {
        executeInTransaction(session -> {
            session.createMutationQuery("delete from " + entityClass.getSimpleName()).executeUpdate();
            return null;
        });
    }

    protected <R> R executeInTransaction(Function<Session, R> work) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                R result = work.apply(session);
                transaction.commit();
                return result;
            } catch (RuntimeException exception) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                throw exception;
            }
        }
    }

    protected <R> R executeRead(Function<Session, R> work) {
        try (Session session = sessionFactory.openSession()) {
            return work.apply(session);
        }
    }

    private void validate(T entity) {
        Set<ConstraintViolation<T>> violations = VALIDATOR.validate(entity);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
