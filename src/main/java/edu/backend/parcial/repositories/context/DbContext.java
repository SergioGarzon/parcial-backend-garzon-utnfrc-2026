package edu.backend.parcial.repositories.context;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DbContext {

    private static DbContext instance;

    private final EntityManagerFactory emf;

    private DbContext() {
        this.emf = Persistence.createEntityManagerFactory("parcial-garzon");
    }

    public static DbContext getInstance() {

        if (instance == null) {
            instance = new DbContext();
        }

        return instance;
    }

    public EntityManager createManager() {
        return emf.createEntityManager();
    }

    public void close() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}