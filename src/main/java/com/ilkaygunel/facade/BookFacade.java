package com.ilkaygunel.facade;

import com.ilkaygunel.entities.Book;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class BookFacade extends AbstractFacade<Book> {

    @PersistenceContext
    private EntityManager entityManager;

    public BookFacade() {
        super(Book.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
