package edu.backend.parcial.repositories;

import edu.backend.parcial.models.Liquidacion;
import edu.backend.parcial.excepciones.TarjetaInexistenteException;
import edu.backend.parcial.models.Tarjeta;
import jakarta.persistence.EntityManager;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class LiquidacionesRepository {

    private EntityManager manager;

    public LiquidacionesRepository(EntityManager em) {
        this.manager = em;
    }

    public Liquidacion getById(Long id) {
        return manager.find(Liquidacion.class, id);
    }

    public List<Liquidacion> getAll() {
        return manager.createQuery("FROM Liquidaciones",Liquidacion.class).getResultList();
    }

    public boolean AddLiquidacion(Liquidacion liquidacion) {
        boolean control = false;
        try {
            manager.getTransaction().begin();
            manager.persist(liquidacion);
            manager.getTransaction().commit();
            control = true;

        } catch (Exception e) {
            if (manager.getTransaction().isActive())
                manager.getTransaction().rollback();
            throw e;
        }
        return control;
    }


    public boolean deleteLiquidacion(Long id) {
        boolean control = false;

        try {
            manager.getTransaction().begin();
            Liquidacion entity = getById(id);
            if (entity != null) {
                Liquidacion managedEntity = manager.merge(entity);
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

    public Liquidacion getLiquidacionTarjeta(String numeroTarjeta, Integer anio, Integer mes) throws TarjetaInexistenteException {

        try {
            Long cantidadTarjetas = manager.createQuery(
                            "SELECT COUNT(t) FROM Tarjeta t WHERE t.numero = :numero", Long.class)
                    .setParameter("numero", numeroTarjeta)
                    .getSingleResult();

            if (cantidadTarjetas == 0) {
                throw new edu.backend.parcial.excepciones.TarjetaInexistenteException(numeroTarjeta);
            }

            return manager.createQuery("""
                FROM Liquidacion l 
                WHERE l.tarjeta.numero = :numero 
                  AND l.anio = :anio 
                  AND l.mes = :mes
                """, Liquidacion.class)
                    .setParameter("numero", numeroTarjeta)
                    .setParameter("anio", anio)
                    .setParameter("mes", mes)
                    .getSingleResult();

        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }

    public Liquidacion getLiquidacion(Long idTarjeta, Integer anio, Integer mes) throws TarjetaInexistenteException {

        try {
            Long cantidadTarjetas = manager.createQuery(
                            "SELECT COUNT(t) FROM Tarjeta t WHERE t.id = :id", Long.class)
                    .setParameter("id", idTarjeta)
                    .getSingleResult();

            if (cantidadTarjetas == 0) {
                throw new edu.backend.parcial.excepciones.TarjetaInexistenteException(idTarjeta);
            }

            return manager.createQuery("""
                FROM Liquidacion l 
                WHERE l.tarjeta.id = :id 
                  AND l.anio = :anio 
                  AND l.mes = :mes
                """, Liquidacion.class)
                    .setParameter("id", idTarjeta)
                    .setParameter("anio", anio)
                    .setParameter("mes", mes)
                    .getSingleResult();

        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }


}
