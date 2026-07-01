package edu.backend.parcial.repositories;

import edu.backend.parcial.models.Tarjeta;
import jakarta.persistence.EntityManager;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor // No se usa pero por las dudas
public class TarjetasRepository {

    private EntityManager manager;

    public TarjetasRepository(EntityManager em) {
        this.manager = em;
    }

    public Tarjeta getById(Long id) {
        return manager.find(Tarjeta.class, id);
    }

    public Tarjeta getByNumero(String numero) {
        try {
            return manager.createQuery("FROM Tarjeta t WHERE t.numero = :numero", Tarjeta.class)
                    .setParameter("numero", numero)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }

    public List<Tarjeta> getAll() {
        return manager.createQuery("FROM Tarjeta",Tarjeta.class).getResultList();
    }

    public boolean AddTarjeta(Tarjeta tarjeta) {
        boolean control = false;
        try {
            manager.getTransaction().begin();
            manager.persist(tarjeta);
            manager.getTransaction().commit();
            control = true;

        } catch (Exception e) {
            if (manager.getTransaction().isActive())
                manager.getTransaction().rollback();
            throw e;
        }
        return control;
    }

    public boolean deleteTarjeta(Long id) {
        boolean control = false;

        try {
            manager.getTransaction().begin();
            Tarjeta entity = getById(id);
            if (entity != null) {
                Tarjeta managedEntity = manager.merge(entity);
                manager.remove(managedEntity);
            }
            manager.getTransaction().commit();
            control = true;
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            throw e;
        }
        return control;
    }

    public boolean deleteTarjetaPorNumero(String numero) {
        boolean control = false;

        try {
            manager.getTransaction().begin();
            Tarjeta entity = getByNumero(numero);
            if (entity != null) {
                Tarjeta managedEntity = manager.merge(entity);
                manager.remove(managedEntity);
            }
            manager.getTransaction().commit();
            control = true;
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            throw e;
        }
        return control;
    }

}