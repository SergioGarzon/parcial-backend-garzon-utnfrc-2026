package edu.backend.parcial.repositories;

import edu.backend.parcial.models.Consumo;
import jakarta.persistence.EntityManager;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ConsumosRepository {

    private EntityManager manager;

    public ConsumosRepository(EntityManager em) {
        this.manager = em;
    }

    public List<Consumo> getAll() {
        return manager.createQuery("FROM Consumos",Consumo.class).getResultList();
    }

    public Consumo getById(Long id) {
        return manager.find(Consumo.class, id);
    }

    public boolean AddConsumo(Consumo consumo) {
        boolean control = false;
        try {
            manager.getTransaction().begin();
            manager.persist(consumo);
            manager.getTransaction().commit();
            control = true;

        } catch (Exception e) {
            if (manager.getTransaction().isActive())
                manager.getTransaction().rollback();
            throw e;
        }
        return control;
    }

    public boolean deleteConsumo(Long id) {
        boolean control = false;

        try {
            manager.getTransaction().begin();
            Consumo entity = getById(id);
            if (entity != null) {
                Consumo managedEntity = manager.merge(entity);
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

    public List<Consumo> getConsumoTarjeta(Integer anio, Integer mes) {

        try {
            return manager.createQuery("""
                    FROM Consumo c 
                    WHERE c.anio = :anio 
                    AND c.mes = :mes
                    """, Consumo.class)
                    .setParameter("anio", anio)
                    .setParameter("mes", mes)
                    .getResultList();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }

    public List<Consumo> getConsumosByTarjetaAnioMes(String numeroTarjeta, Integer anio, Integer mes) {
        try {
            Long cantidadTarjetas = manager.createQuery(
                            "SELECT COUNT(t) FROM Tarjeta t WHERE t.numero = :numero", Long.class)
                    .setParameter("numero", numeroTarjeta)
                    .getSingleResult();

            if (cantidadTarjetas == 0) {
                throw new edu.backend.parcial.excepciones.TarjetaInexistenteException(numeroTarjeta);
            }

            return manager.createQuery("""
                    FROM Consumo c 
                    WHERE c.tarjeta.numero = :numeroTarjeta 
                    AND c.anio = :anio 
                    AND c.mes = :mes
                    """, Consumo.class)
                    .setParameter("numeroTarjeta", numeroTarjeta)
                    .setParameter("anio", anio)
                    .setParameter("mes", mes)
                    .getResultList();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }




}
