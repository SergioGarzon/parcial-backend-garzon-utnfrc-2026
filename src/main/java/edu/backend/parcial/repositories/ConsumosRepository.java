package edu.backend.parcial.repositories;

import edu.backend.parcial.models.Consumo;
import edu.backend.parcial.models.Liquidacion;
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

    public Consumo getConsumoTarjeta() {
        return null;
    }

    public List<Consumo> getConsumosByTarjetaAnioMes(Long idTarjeta, Integer anio, Integer mes) {
        try {
            return manager.createQuery("""
                    FROM Consumos c 
                    WHERE c.tarjeta.id = :idTarjeta 
                    AND c.anio = :anio 
                    AND c.mes = :mes
                    """, Consumo.class)
                    .setParameter("idTarjeta", idTarjeta)
                    .setParameter("anio", anio)
                    .setParameter("mes", mes)
                    .getResultList();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }




}
