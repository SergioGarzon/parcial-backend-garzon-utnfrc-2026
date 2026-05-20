package edu.backend.parcial.repositories;


import java.util.List;

import edu.backend.parcial.repositories.context.DbContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public abstract class Repository<T, K> {

    protected EntityManager getManager() {
        return DbContext.getInstance().createManager();
    }

    public void add(T entity) {

        EntityManager manager = getManager();
        EntityTransaction transaction = manager.getTransaction();

        try {

            transaction.begin();

            manager.persist(entity);

            transaction.commit();

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw e;

        } finally {
            manager.close();
        }
    }

    public void update(T entity) {

        EntityManager manager = getManager();
        EntityTransaction transaction = manager.getTransaction();

        try {

            transaction.begin();

            manager.merge(entity);

            transaction.commit();

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw e;

        } finally {
            manager.close();
        }
    }

    public T delete(K id) {

        EntityManager manager = getManager();
        EntityTransaction transaction = manager.getTransaction();

        try {

            transaction.begin();

            T entity = getById(id);

            if (entity != null) {

                T managedEntity = manager.merge(entity);

                manager.remove(managedEntity);
            }

            transaction.commit();

            return entity;

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw e;

        } finally {
            manager.close();
        }
    }

    public abstract T getById(K id);

    public abstract List<T> getAll();
}