package edu.backend.parcial.repositories;

import edu.backend.parcial.models.Cotizacion;
import edu.backend.parcial.models.Tarjeta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
public class CotizacionesRepository {

    private EntityManager manager;

    public CotizacionesRepository(EntityManager em) {
        this.manager = em;
    }

    public Cotizacion getByMoneda(String moneda) {
        try {
            return manager.createQuery("FROM Cotizaciones t WHERE t.moneda = :moneda", Cotizacion.class)
                    .setParameter("moneda", moneda)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Cotizacion getByTasaCambio(BigDecimal tasaCambio) {
        try {
            return manager.createQuery("FROM Cotizaciones t WHERE t.tasa_cambio = :tasaCambio", Cotizacion.class)
                    .setParameter("tasaCambio", tasaCambio)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Cotizacion> getAll() {
        return manager.createQuery("FROM Cotizaciones",Cotizacion.class).getResultList();
    }

    public boolean AddCotizacion(Cotizacion cotizacion) {
        boolean control = false;
        try {
            manager.getTransaction().begin();
            manager.persist(cotizacion);
            manager.getTransaction().commit();
            control = true;

        } catch (Exception e) {
            if (manager.getTransaction().isActive())
                manager.getTransaction().rollback();
            throw e;
        }
        return control;
    }

}
