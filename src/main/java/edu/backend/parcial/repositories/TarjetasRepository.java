package edu.backend.parcial.repositories;

import edu.backend.parcial.models.Tarjetas;
import jakarta.persistence.EntityManager;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TarjetasRepository extends Repository<Tarjetas, Long> {

    @Override
    public Tarjetas getById(Long id) {

        EntityManager manager = getManager();

        try {
            return manager.find(Tarjetas.class, id);
        } finally {
            manager.close();
        }
    }

    @Override
    public List<Tarjetas> getAll() {

        EntityManager manager = getManager();

        try {
            return manager.createQuery("FROM Tarjetas",Tarjetas.class).getResultList();
        } finally {
            manager.close();
        }
    }

    public List<Tarjetas> getTarjetasSinLiquidacion(Integer anio, Integer mes) {

        EntityManager manager = getManager();

        try {

            return manager.createQuery("""
                    FROM Tarjetas t
                    WHERE t.id NOT IN (
                        SELECT l.tarjeta.id
                        FROM Liquidaciones l
                        WHERE l.anio = :anio
                        AND l.mes = :mes
                    )
                    """, Tarjetas.class)
                    .setParameter("anio", anio)
                    .setParameter("mes", mes)
                    .getResultList();
        } finally {
            manager.close();
        }
    }
}