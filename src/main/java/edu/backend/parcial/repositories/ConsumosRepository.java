package edu.backend.parcial.repositories;

import edu.backend.parcial.models.Consumos;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ConsumosRepository extends Repository<Consumos, Long> {
    @Override
    public Consumos getById(Long id) {

        EntityManager manager = getManager();

        try {
            return manager.find(Consumos.class, id);
        } finally {
            manager.close();
        }
    }

    @Override
    public List<Consumos> getAll() {

        EntityManager manager = getManager();

        try {
            return manager.createQuery("FROM Consumos",Consumos.class).getResultList();
        } finally {
            manager.close();
        }
    }

    public List<Consumos> getByTarjetaAndPeriodo(Long idTarjeta, Integer anio, Integer mes) {

        EntityManager manager = getManager();

        try {

            return manager.createQuery("""
                    FROM Consumos c
                    WHERE c.tarjeta.id = :idTarjeta
                    AND c.anio = :anio
                    AND c.mes = :mes
                    """, Consumos.class)
                    .setParameter("idTarjeta", idTarjeta)
                    .setParameter("anio", anio)
                    .setParameter("mes", mes)
                    .getResultList();

        } finally {
            manager.close();
        }
    }
}
