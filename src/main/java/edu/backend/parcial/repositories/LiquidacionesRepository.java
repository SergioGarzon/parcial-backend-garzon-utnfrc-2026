package edu.backend.parcial.repositories;

import edu.backend.parcial.models.Liquidaciones;
import jakarta.persistence.EntityManager;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LiquidacionesRepository extends Repository<Liquidaciones, Long> {
    
    @Override
    public Liquidaciones getById(Long id) {

        EntityManager manager = getManager();

        try {
            return manager.find(Liquidaciones.class, id);
        } finally {
            manager.close();
        }
    }

    @Override
    public List<Liquidaciones> getAll() {

        EntityManager manager = getManager();

        try {
            return manager.createQuery("FROM Liquidaciones",Liquidaciones.class).getResultList();
        } finally {
            manager.close();
        }
    }

    public Liquidaciones getLiquidacion(String numeroTarjeta, Integer anio, Integer mes) {

        EntityManager manager = getManager();

        try {
            // HQL con alias 'l' y condiciones WHERE obligatorias
            return manager.createQuery("""
                FROM Liquidaciones l 
                WHERE l.tarjeta.numero = :numero 
                  AND l.anio = :anio 
                  AND l.mes = :mes
                """, Liquidaciones.class)
                    .setParameter("numero", numeroTarjeta)
                    .setParameter("anio", anio)
                    .setParameter("mes", mes)
                    .getSingleResult();

        }
        finally {
            manager.close();
        }
    }
}
